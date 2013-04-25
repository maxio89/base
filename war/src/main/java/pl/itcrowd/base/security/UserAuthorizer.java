package pl.itcrowd.base.security;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;
import pl.itcrowd.base.domain.RoleEnum;
import pl.itcrowd.base.security.annotations.Authenticated;
import pl.itcrowd.base.security.annotations.permissions.AdminRole;
import pl.itcrowd.base.security.annotations.permissions.UserRole;

@SuppressWarnings({"UnusedDeclaration", "JavaDoc"})
public class UserAuthorizer {

    /**
     * Example secures method, checks if user has valid permission to manage users.
     *
     * @param identity identity
     *
     * @return true if authorized
     */
    @Secures
    @AdminRole
    public boolean canManageUsers(Identity identity)
    {
        return identity.isLoggedIn() && identity.hasRole(RoleEnum.ADMIN.name(), "USERS", "GROUP");
    }

    @Secures
    @Authenticated
    public boolean isAuthenticated(Identity identity)
    {
        return identity.isLoggedIn();
    }

    @Secures
    @UserRole
    public boolean isUser(Identity identity)
    {
        return identity.isLoggedIn() && identity.hasRole(RoleEnum.USER.name(), "USERS", "GROUP");
    }
}
