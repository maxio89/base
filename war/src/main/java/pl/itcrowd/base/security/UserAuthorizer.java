package pl.itcrowd.base.security;

import pl.itcrowd.base.domain.RoleEnum;
import pl.itcrowd.base.security.annotations.Admin;
import pl.itcrowd.base.security.annotations.Authenticated;
import pl.itcrowd.base.user.CurrentUser;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;
import pl.itcrowd.base.domain.RoleEnum;
import pl.itcrowd.base.security.annotations.Admin;
import pl.itcrowd.base.security.annotations.Authenticated;

import javax.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public class UserAuthorizer {

    /**
     * Example secures method, checks if user has valid permission to manage users.
     *
     * @param identity identity
     *
     * @return true if authorized
     */

    @Inject
    @CurrentUser
    private pl.itcrowd.base.domain.User currentUser;

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
