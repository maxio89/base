package pl.itcrowd.base.user.business;

import pl.itcrowd.base.domain.RoleEnum;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.domain.UserActivationToken;
import pl.itcrowd.base.domain.UserRemindPasswordToken;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.mail.ProjectMailman;
import pl.itcrowd.base.security.PasswordDigester;
import pl.itcrowd.base.setting.business.ProjectConfig;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.domain.RoleEnum;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.mail.ProjectMailman;
import pl.itcrowd.base.security.PasswordDigester;
import pl.itcrowd.seam3.persistence.EntityRemoved;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
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

    public User loadByEmail(String email)
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

    @Transactional
    public boolean persist()
    {
        final User user = getInstance();
        user.setRole(RoleEnum.CLIENT); //default role
        user.setClientLanguage(Locale.ENGLISH.getLanguage());

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
        String activationURL = projectConfig.getActiveAccountUrl();

        try {
            activationURL = activationURL.replaceAll("@email@", URLEncoder.encode(user.getEmail(), "UTF-8")).replaceAll("@token@", URLEncoder.encode(token.getToken(), "UTF-8"));
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

    public boolean resendActivationMail(String email)
    {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email was null or empty");
        }
        boolean userExists;
        try {
            loadByEmail(email);
            userExists = true;
        } catch (NoResultException nre) {
            userExists = false;
        }

        if (userExists) {

            if (getInstance().isActive()) {
                return false;
            }
            UserActivationToken token;
            try {
                String query = "select t from UserActivationToken t where t.user.email=:email";
                token = (UserActivationToken) getEntityManager().createQuery(query).setParameter("email", getInstance().getEmail()).getSingleResult();
            } catch (NoResultException nre) { //token not found for this user, so new token is needed
                token = new UserActivationToken();
                token.setUser(getInstance());
                token.setToken(UUID.randomUUID().toString());
                getEntityManager().persist(token);
                getEntityManager().flush();
            }

            String activationURL = projectConfig.getActiveAccountUrl();

            try {
                activationURL = activationURL.replaceAll("@email@", URLEncoder.encode(getInstance().getEmail(), "UTF-8"))
                    .replaceAll("@token@", URLEncoder.encode(token.getToken(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            mailman.get().sendRegistrationMail(getInstance(), activationURL);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method will persist new UserRemindPasswordToken
     * and send mail to user with password change link
     */
    @Transactional
    public boolean initiatePasswordReset()
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
            String resetURL = projectConfig.getPasswordResetLink();
            try {
                resetURL = resetURL.replaceAll("@email@", URLEncoder.encode(user.getEmail(), "UTF-8")).replaceAll("@token@", URLEncoder.encode(passwordToken.getToken(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.errorv(e, "Unsupported encoding ex in saveResetPassword. Maybe wrong reset link?: {0}", resetURL);
            }

            mailman.get().sendPasswordChangeMail(this.getInstance(), resetURL);
            return true;
        }
    }

    /**
     * Method change password if token exist for provided email
     *
     * @param email       email
     * @param newPassword new password
     * @param token       token
     *
     * @return true if change was successful, otherwise false
     */
    public boolean changePassword(String email, String newPassword, String token)
    {
        if (email == null || newPassword == null || token == null) {
            throw new IllegalArgumentException("email, password or token was null");
        }

        UserRemindPasswordToken tokenEntity;
        try {
            final String query = "select t from UserRemindPasswordToken t where t.user.email=:email and t.token=:token";
            tokenEntity = (UserRemindPasswordToken) getEntityManager().createQuery(query).setParameter("email", email).setParameter("token", token).getSingleResult();
        } catch (NoResultException nre) {
            return false;
        }

        final String passwordDigest = new PasswordDigester().getDigest(newPassword);
        final String updatePassQuery = "update User u set u.passwordDigest=:pass where u.email=:email";
        final int result = getEntityManager().createQuery(updatePassQuery).setParameter("pass", passwordDigest).setParameter("email", email).executeUpdate();
        if (result == 0) {
            return false;
        } else {
            getEntityManager().remove(tokenEntity);
            getEntityManager().flush();
            return true;
        }
    }
}
