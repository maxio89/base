package pl.itcrowd.base.user;

import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.business.UserHome;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.PostLoggedOutEvent;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.seam3.persistence.EntityRemoved;
import pl.itcrowd.seam3.persistence.EntityUpdated;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import java.io.Serializable;

/**
 * Manages current user. It is not invoked directly from code but is used by CDI to produce current user.
 */
@SuppressWarnings("UnusedDeclaration")
@SessionScoped
public class CurrentUserManager implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private User currentUser;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Instance<EntityManager> entityManagerInstance;

    @Inject
    private Instance<Identity> identityInstance;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger log;

    @SuppressWarnings("CdiUnproxyableBeanTypesInspection")
    @Inject
    private Instance<ServletContext> servletContextInstance;

    @Inject
    private UserHome userHome;

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Produces user that is currently logged in. It is also aviable by EL.
     *
     * @return current user or null if not logged in
     */
    @Produces
    @Named
    @CurrentUser
    public User getCurrentUser()
    {
        final Identity identity = identityInstance.get();
        if (!identity.isLoggedIn()) {
            currentUser = null;
            return currentUser; //user not logged
        }

        String userEmail = identity.getUser().getId();
        if (currentUser != null) {
            if (!currentUser.getEmail().equals(userEmail)) {
                currentUser = null;
            }
        } else {
            User user;
            try {
                user = userHome.loadByEmail(userEmail);
                currentUser = user;
            } catch (NoResultException nre) { //user not found? If this will be invoked, authentication it's not working properly.
                currentUser = null;
                throw new IllegalStateException("Current user is logged in, but his instance not found in DB.");
            }
        }
        return currentUser;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Listner for user logout event. Refreshes currentUser.
     *
     * @param event logout event
     */
    public void onLogout(@Observes final PostLoggedOutEvent event)
    {
        currentUser = null;
    }

    /**
     * Listener for user removal event. Refreshes currentUser.
     *
     * @param user removed user
     */
    public void onUserRemoved(@Observes @EntityRemoved User user)
    {
        currentUser = null;
    }

    /**
     * Listener for user modification event. Refreshes currentUser.
     *
     * @param user modified user
     */
    public void onUserUpdated(@Observes @EntityUpdated User user)
    {
        currentUser = null;
    }

    /**
     * Loads any user from database. This is of course for development purposes only.
     *
     * @return any user db returns
     */
    private User getDeveloper()
    {
        return (User) entityManagerInstance.get().createQuery("select u from User u").setMaxResults(1).getSingleResult();
    }
}
