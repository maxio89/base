package pl.itcrowd.base.security;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.web.BundleKeys;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@SuppressWarnings("UnusedDeclaration")
public class UserAuthenticator extends BaseAuthenticator implements Authenticator {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Authenticator ---------------------

    @Inject
    private Credentials credentials;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private EntityManager entityManager;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger log;

    @Inject
    private Messages messages;

    @Inject
    private PasswordDigester passwordDigester;

    @Inject
    private Identity identity;

    @Override
    public void authenticate()
    {
        final PasswordCredential credential = (PasswordCredential) credentials.getCredential();
        final String password = credential == null ? "" : credential.getValue();
        String username = credentials.getUsername();
        if (username == null) {
            username = "";
        }
        try {
            final User user = (User) entityManager.createQuery(
                "select u from User u where u.email=:email and u.passwordDigest=:passwordDigest")
                .setParameter("email", username)
                .setParameter("passwordDigest", password)
                .getSingleResult();
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(new SimpleUser(user.getEmail()));
            //set role
            identity.addRole(user.getRole().toString(), "USERS", "GROUP");
        } catch (NoResultException e) {
            messages.error(BundleKeys.AUTHORIZATION_EXCEPTION);
            log.info("Authentication error for user:" + username + " with password:" + password);
            setStatus(AuthenticationStatus.FAILURE);
        }
    }
}
