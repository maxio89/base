package com.project.user.business;

import com.project.ProjectConfig;
import com.project.domain.RoleEnum;
import com.project.domain.User;
import com.project.domain.UserActivationToken;
import com.project.domain.UserRemindPasswordToken;
import com.project.framework.business.EntityHome;
import com.project.mail.ProjectMailman;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;
import pl.com.it_crowd.seam.framework.EntityRemoved;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserHome extends EntityHome<User> {

    Instance<ProjectMailman> mailman;

    private ProjectConfig projectConfig;

    private Logger logger;

    @Inject
    public UserHome(Instance<ProjectMailman> mailman, ProjectConfig projectConfig, Logger logger)
    {
        this.mailman = mailman;
        this.projectConfig = projectConfig;
        this.logger = logger;
    }

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

    /**
     * Method will persist new UserRemindPasswordToken
     * and send mail to user with password change link
     *
     * @param resetLink link with email and token placeholders
     */
    @Transactional
    public boolean saveResetPasswordRequest(String resetLink)
    {
        final User user = getInstance(); //current user
        try {
            String query = "select t from UserRemindPasswordToken t where t.user.email=:email";
            getEntityManager().createQuery(query).setParameter("email", user.getEmail()).getSingleResult();
            return false;
        } catch (NoResultException nre) { //if token wasn't found, its ok

            //create new token
            UserRemindPasswordToken passwordToken = new UserRemindPasswordToken();
            passwordToken.setUser(user);
            passwordToken.setToken(UUID.randomUUID().toString());
            passwordToken.setGenerationDate(new Date());
            getEntityManager().persist(passwordToken);
            getEntityManager().flush();

            //generate reset URL from reset link
            String resetURL = projectConfig.getAppURL().concat(resetLink);
            try {
                resetURL = resetURL.replaceAll("@email@", URLEncoder.encode(user.getEmail(), "UTF-8"))
                    .replaceAll("@token@", URLEncoder.encode(passwordToken.getToken(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("Unsupported encoding ex in saveResetPassword. Maybe wrong reset link?:" + resetLink);
                e.printStackTrace();
            }

            mailman.get().sendPasswordChangeMail(this.getInstance(), resetURL);
            return true;
        }
    }

    /**
     * This method checks if provided email and password is valid for password reminding.
     * One call to this method will remove UserRemindPasswordToken connected instance.
     *
     * @param email user email
     * @param token uuid token
     *
     * @return true if valid, otherwise false
     */
    public boolean validatePasswordToken(String email, String token)
    {
        try {
            final String query = "select t from UserRemindPasswordToken t where t.user.email=:email and t.token=:token";
            UserRemindPasswordToken passwordToken = (UserRemindPasswordToken) getEntityManager().createQuery(query)
                .setParameter("email", email)
                .setParameter("token", token)
                .getSingleResult();
            getEntityManager().remove(passwordToken);
            getEntityManager().flush();
            return true;
        } catch (NoResultException nre) {
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
        user.setRole(RoleEnum.CLIENT); //default role

        //if development, all persisted user are active by default
        if (!projectConfig.isProduction()) {
            user.setActive(true);
        }
        final boolean persistResult = super.persist();
        final UserActivationToken token = new UserActivationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        getEntityManager().persist(token);
        getEntityManager().flush();

        //generate reset URL from reset link
        activationURL = projectConfig.getAppURL().concat(activationURL);

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
