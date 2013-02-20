package com.project.security;

import com.project.domain.User;
import com.project.security.annotations.Authenticated;
import com.project.user.CurrentUser;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Authorizer {
// ------------------------------ FIELDS ------------------------------

    @Inject
    private Identity identity;

    @Inject
    @CurrentUser
    private User user;

// -------------------------- OTHER METHODS --------------------------

    /**
     * Authrozier method for @Authenticated security binding.
     *
     * @return true if user has logged in
     */
    @Secures
    @Authenticated
    public boolean isAuthenticated()
    {
        return identity.isLoggedIn();
    }
}
