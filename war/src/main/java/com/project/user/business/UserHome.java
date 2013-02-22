package com.project.user.business;

import com.project.domain.User;
import com.project.domain.UserActivationToken;
import com.project.mail.ProjectMailman;
import org.jboss.seam.transaction.Transactional;
import pl.com.it_crowd.seam.framework.EntityHome;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public class UserHome extends EntityHome<User> {

    @Inject
    Instance<ProjectMailman> mailman;

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

    @Override
    public boolean persist()
    {
        throw new UnsupportedOperationException("User persist(String activationEmail)");
    }

    @Transactional
    public boolean persist(String activationURL)
    {
        final boolean persistResult = super.persist();
        final User user = getInstance();
        final UserActivationToken token = new UserActivationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        getEntityManager().persist(token);
        getEntityManager().flush();
        try {
            activationURL = activationURL.replaceAll(URLEncoder.encode("@email@", "UTF-8"), user.getEmail())
                .replaceAll(URLEncoder.encode("@token@", "UTF-8"), token.getToken());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        mailman.get().sendRegistrationMail(user, activationURL);
        return persistResult;
    }


}
