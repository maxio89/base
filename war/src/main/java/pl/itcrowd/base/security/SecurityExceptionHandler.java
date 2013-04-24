package pl.itcrowd.base.security;

import pl.itcrowd.base.web.BundleKeys;
import org.jboss.seam.faces.event.PreLoginEvent;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfigStore;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.seam.security.Identity;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.jboss.solder.logging.Logger;

import javax.enterprise.event.Event;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
@HandlesExceptions
public class SecurityExceptionHandler {
// ------------------------------ FIELDS ------------------------------

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger log;

    @Inject
    private Event<PreLoginEvent> preLoginEvent;

    @Inject
    private ViewConfigStore viewConfigStore;

// -------------------------- OTHER METHODS --------------------------

    public void handleAuthorizationException(@Handles CaughtException<AuthorizationException> evt, Messages messages, Identity identity, FacesContext context)
    {
        if (!identity.isLoggedIn()) {
            redirectToLoginPage(context);
        } else {
            redirectToAccessDeniedView(context);
            messages.error(BundleKeys.AUTHORIZATION_EXCEPTION);
        }
        evt.handled();
    }

    private void redirectToAccessDeniedView(FacesContext context)
    {
        UIViewRoot viewRoot = context.getViewRoot();
        // If a user has already done a redirect and rendered the response (possibly in an observer) we cannot do this output
        final PhaseId currentPhase = context.getCurrentPhaseId();
        if (!context.getResponseComplete() && !PhaseId.RENDER_RESPONSE.equals(currentPhase)) {
            AccessDeniedView accessDeniedView = viewConfigStore.getAnnotationData(viewRoot.getViewId(), AccessDeniedView.class);
            if (accessDeniedView == null || accessDeniedView.value() == null || accessDeniedView.value().isEmpty()) {
                log.warn("No AccessDeniedView is configured, returning 401 response (access denied). Please configure an AccessDeniedView in the ViewConfig.");
                context.getExternalContext().setResponseStatus(HttpServletResponse.SC_UNAUTHORIZED);
                context.responseComplete();
                return;
            }
            String accessDeniedViewId = accessDeniedView.value();
            log.debugf("Redirecting to configured AccessDenied %s", accessDeniedViewId);
            NavigationHandler navHandler = context.getApplication().getNavigationHandler();
            navHandler.handleNavigation(context, "", accessDeniedViewId);
            context.renderResponse();
        }
    }

    private void redirectToLoginPage(FacesContext context)
    {
        UIViewRoot viewRoot = context.getViewRoot();
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        preLoginEvent.fire(new PreLoginEvent(context, sessionMap));
        LoginView loginView = viewConfigStore.getAnnotationData(viewRoot.getViewId(), LoginView.class);
        if (loginView == null || loginView.value() == null || loginView.value().isEmpty()) {
            log.debug("Returning 401 response (login required)");
            context.getExternalContext().setResponseStatus(HttpServletResponse.SC_UNAUTHORIZED);
            context.responseComplete();
            return;
        }
        String loginViewId = loginView.value();
        log.debugf("Redirecting to configured LoginView %s", loginViewId);
        NavigationHandler navHandler = context.getApplication().getNavigationHandler();
        navHandler.handleNavigation(context, "", loginViewId);
        context.renderResponse();
    }
}
