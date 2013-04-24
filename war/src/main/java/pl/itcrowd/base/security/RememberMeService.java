package pl.itcrowd.base.security;

import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.PreLoggedOutEvent;
import org.jboss.solder.logging.Logger;
import org.picketlink.idm.impl.api.PasswordCredential;
import pl.itcrowd.base.domain.RememberMeToken;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.CurrentUser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Named
@SessionScoped
public class RememberMeService implements Serializable {

    private static final String COOKIE_NAME = "rememberMe";

    private static final int COOKIE_MAX_AGE = 360;

    private static final String TOKEN_EM_QUERY = "select t.user from RememberMeToken t where t.token=:token";

    private static final String TOKEN_REMOVE_QUERY = "delete from RememberMeToken t where t.token=:token";

    @Inject
    private Identity identity;

    @Inject
    private Credentials credentials;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private FacesContext facesContext;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Instance<EntityManager> entityManagerInstance;

    @Inject
    @CurrentUser
    private Instance<User> currentUser;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger logger;

    private boolean userAutologged;

    private String token;

    @PostConstruct
    public void onCreate()
    {
        final Map<String, Object> requestCookieMap = facesContext.getExternalContext().getRequestCookieMap();

        if (requestCookieMap != null && requestCookieMap.size() > 0 && requestCookieMap.containsKey(COOKIE_NAME)) {
            Cookie cookie = (Cookie) requestCookieMap.get(COOKIE_NAME);
            String token = cookie.getValue();
            final HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            request.getRemoteAddr();

            if (token != null) {
                User user = findUserFromToken(token);
                if (user != null) {
                    credentials.setUsername(user.getEmail());
                    credentials.setCredential(new PasswordCredential(user.getPasswordDigest()));
                    identity.login();
                    userAutologged = true;
                    this.token = token;
                }
            }
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onLogout(@Observes PreLoggedOutEvent event)
    {
        if (userAutologged && token != null) {
            removeRememberMeToken(token);
        }
    }

    private User findUserFromToken(String token)
    {
        EntityManager em = entityManagerInstance.get();
        try {
            return (User) em.createQuery(TOKEN_EM_QUERY).setParameter("token", token).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void createRememberMeToken()
    {
        EntityManager entityManager = entityManagerInstance.get();
        User user = currentUser.get();
        if (user != null && user.getId() != null) {
            final User managedUser = entityManager.find(User.class, user.getId());
            if (managedUser != null) {
                //persist token
                RememberMeToken token = new RememberMeToken();
                token.setDate(new Date());
                token.setToken(getUniqueToken());
                token.setUser(managedUser);
                entityManager.persist(token);
                entityManager.flush();

                //add cookie to response
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                Cookie cookie = new Cookie(COOKIE_NAME, token.getToken());
                cookie.setMaxAge(COOKIE_MAX_AGE);
                response.addCookie(cookie);
            } else {
                logger.error("Current user is not null, but his instance not found in DB");
            }
        } else {
            logger.error("Cannot create rememberMe token, because currentUser is null or transient");
        }
    }

    public void removeRememberMeToken(String token)
    {
        int ignore = entityManagerInstance.get().createQuery(TOKEN_REMOVE_QUERY).setParameter("token", token).executeUpdate();
    }

    /**
     * If user is logged trough Remember Me functionality, returns true.
     * Useful with sensitive operations, e.g. money transfers
     *
     * @return status
     */
    public boolean isUserAutologged()
    {
        return userAutologged;
    }

    private String getUniqueToken()
    {
        return UUID.randomUUID().toString();
    }
}
