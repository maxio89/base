package com.project.security;

import com.project.domain.User;
import com.project.web.BundleKeys;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserAuthenticator extends BaseAuthenticator implements Authenticator {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Authenticator ---------------------

    @Inject
    private Messages messages;

    @Inject
    private Credentials credentials;

    @Inject
    private Identity identity;

    @Inject
    private PasswordDigester passwordDigester;

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger log;

    @Override
    public void authenticate()
    {

        final PasswordCredential credential = (PasswordCredential) credentials.getCredential();
        final String password = credential == null ? "" : passwordDigester.getDigest(credential.getValue());
        String username = credentials.getUsername();
        if (username == null) {
            username = "";
        }
        try {
            final User user = (User) entityManager.createQuery("select u from User u where u.email=:email and u.passwordDigest=:passwordDigest")
                .setParameter("email", username)
                .setParameter("passwordDigest", password)
                .getSingleResult();
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(new SimpleUser(user.getEmail()));
            identity.addRole(user.getRole().toString(), "USERS", "GROUP");
        } catch (NoResultException e) {
            messages.error(BundleKeys.AUTHORIZATION_EXCEPTION);
            setStatus(AuthenticationStatus.FAILURE);
        }
    }
}
