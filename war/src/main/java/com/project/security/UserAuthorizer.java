package com.project.security;

import com.project.domain.RoleEnum;
import com.project.domain.User;
import com.project.security.annotations.Admin;
import com.project.security.annotations.Authenticated;
import com.project.user.CurrentUser;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import javax.inject.Inject;

/**
 * User: Rafal Gielczowski
 * Date: 2/26/13 Time: 1:31 PM
 */
@SuppressWarnings("UnusedDeclaration")
public class UserAuthorizer {

    @Inject
    @CurrentUser
    private User currentUser;

    @Secures
    @Admin
    public boolean isAdmin(Identity identity)
    {
        return identity.isLoggedIn() && currentUser.getRole().equals(RoleEnum.ADMIN);
    }

    @Secures
    @Authenticated
    public boolean isAuthenticated(Identity identity)
    {
        return identity.isLoggedIn();
    }
}
