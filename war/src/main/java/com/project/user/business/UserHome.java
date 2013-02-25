package com.project.user.business;

import com.project.ProjectConfig;
import com.project.domain.User;
import com.project.domain.UserActivationToken;
import com.project.framework.business.EntityHome;
import com.project.mail.ProjectMailman;
import org.jboss.seam.transaction.Transactional;
import pl.com.it_crowd.seam.framework.EntityRemoved;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserHome extends EntityHome<User> {

    @Inject
    Instance<ProjectMailman> mailman;

    @Inject
    private ProjectConfig projectConfig;

    // -------------------------- OTHER METHODS --------------------------

    public boolean activate(String email, String token)
    {
        try {
            final String qlString = "select t from UserActivationToken t where t.user.email=:email and t.token=:token";
            final UserActivationToken activationToken = (UserActivationToken) getEntityManager().createQuery(qlString)
                .setParameter("email", email)
                .setParameter("token", token)
                .getSingleResult();
            getEntityManager().remove(activationToken);
            activationToken.getUser().setActive(true);
            getEntityManager().flush();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public User getByEmail(String email)
    {
        final User user = (User) getEntityManager().createQuery("select u from User u where u.email=:email").setParameter("email", email).getSingleResult();
        setInstance(user);
        return user;
    }

    public User getById(Long id)
    {
        final User user = (User) getEntityManager().createQuery("select u from User u where u.id=:id").setParameter("id", id).getSingleResult();
        setInstance(user);
        return user;
    }

    @Override
    public boolean persist()
    {
        return super.persist();
    }

    @Transactional
    public boolean persist(String activationURL)
    {
        final User user = getInstance();

        //if development, all persisted user are active by default
        if(!projectConfig.isProduction()){
            user.setActive(true);
        }
        final boolean persistResult = super.persist();
        final UserActivationToken token = new UserActivationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        getEntityManager().persist(token);
        getEntityManager().flush();
        try {
            activationURL = activationURL.replaceAll("@email@", URLEncoder.encode(user.getEmail(), "UTF-8"))
                .replaceAll("@token@", URLEncoder.encode(token.getToken(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        mailman.get().sendRegistrationMail(user, activationURL);
        return persistResult;
    }

    @Override
    public int remove(Collection<User> elements)
    {
        if (elements.isEmpty()) {
            return 0;
        }
        final Set<Long> ids = new HashSet<Long>();
        for (User user : elements) {
            ids.add(user.getId());
        }
        ids.remove(null);
        final int count = getEntityManager().createQuery("delete User where id in (:ids)").setParameter("ids", ids).executeUpdate();
        for (User element : elements) {
            beanManager.fireEvent(element, new AnnotationLiteral<EntityRemoved>() {
            });
        }
        return count;
    }
}
