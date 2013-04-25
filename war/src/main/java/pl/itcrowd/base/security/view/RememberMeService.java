package pl.itcrowd.base.security.view;

import org.apache.commons.codec.digest.DigestUtils;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.PreLoggedOutEvent;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;
import org.jboss.solder.servlet.event.Initialized;
import org.picketlink.idm.impl.api.PasswordCredential;
import pl.itcrowd.base.domain.RememberMeToken;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.CurrentUser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Instance;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SessionScoped
public class RememberMeService implements Serializable {

    private static final int COOKIE_MAX_AGE = 360;

    private static final String COOKIE_NAME = "rememberMe";

    @Inject
    private Credentials credentials;

    @Inject
    @CurrentUser
    private Instance<User> currentUser;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Instance<EntityManager> entityManagerInstance;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private FacesContext facesContext;

    @Inject
    private Identity identity;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger logger;

    private String remoteAddr;

    private String token;

    private String userAgentHash;

    private boolean userAutologged;

    private boolean sessionStarted;

    @Transactional
    public void createRememberMeToken()
    {
        collectUserDetails();
        EntityManager entityManager = entityManagerInstance.get();
        User user = currentUser.get();
        if (user != null && user.getId() != null) {
            final User managedUser = entityManager.find(User.class, user.getId());
            if (managedUser != null) {
                //persist token
                RememberMeToken token = new RememberMeToken();
                Date now = new Date();
                token.setCreated(now);
                token.setLastUse(now);
                token.setToken(getUniqueToken());
                token.setUser(managedUser);

                if (remoteAddr != null && remoteAddr.length() > 0) {
                    token.setRemoteAddr(remoteAddr);
                }

                if (userAgentHash != null) {
                    token.setUserAgentHash(userAgentHash);
                }

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

    @SuppressWarnings("UnusedDeclaration")
    @Transactional
    public void observeSessionInitialized(@Observes(notifyObserver = Reception.ALWAYS) @Initialized HttpSession session)
    {
        if (!sessionStarted) {
            sessionStarted = true;
            collectUserDetails();
            if (token == null) {
                return;
            }

            RememberMeToken rMtoken = findToken(token);
            if (rMtoken == null) {
                return;
            }

            boolean tokenValid = validateToken(rMtoken);

            if (!tokenValid) {
                removeRememberMeToken(rMtoken.getToken());
                return;
            }

            User user = rMtoken.getUser();
            credentials.setUsername(user.getEmail());
            credentials.setCredential(new PasswordCredential(user.getPasswordDigest()));
            identity.login();
            userAutologged = true;
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @Transactional
    public void onLogout(@Observes PreLoggedOutEvent event)
    {
        if (token != null) {
            removeRememberMeToken(token);
        }
    }

    @Transactional
    public void removeRememberMeToken(@Nonnull String token)
    {
        final String query = "delete from RememberMeToken t where t.token=:token";
        entityManagerInstance.get().createQuery(query).setParameter("token", token).executeUpdate();
    }

    public void collectUserDetails()
    {
        if (remoteAddr == null || userAgentHash == null) {
            final ExternalContext externalContext = facesContext.getExternalContext();
            remoteAddr = ((HttpServletRequest) externalContext.getRequest()).getRemoteAddr();
            String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
            if (userAgent != null) {
                userAgentHash = DigestUtils.md5Hex(userAgent);
            }

            final Map<String, Object> requestCookieMap = externalContext.getRequestCookieMap();
            if (requestCookieMap != null && requestCookieMap.size() > 0 && requestCookieMap.containsKey(COOKIE_NAME)) {
                Cookie cookie = (Cookie) requestCookieMap.get(COOKIE_NAME);
                token = cookie.getValue();
            }
        }
    }

    @Transactional
    private RememberMeToken findToken(@Nonnull String token)
    {
        String query = "select t from RememberMeToken t where t.token=:token";
        EntityManager em = entityManagerInstance.get();
        try {
            return (RememberMeToken) em.createQuery(query).setParameter("token", token).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Nonnull
    private String getUniqueToken()
    {
        return UUID.randomUUID().toString();
    }

    private boolean validateToken(@Nullable RememberMeToken rMToken)
    {
        return token != null && rMToken != null && rMToken.getToken().equals(this.token) && rMToken.getRemoteAddr().equals(remoteAddr) && rMToken.getUserAgentHash()
            .equals(userAgentHash);
    }
}
