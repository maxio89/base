package com.project.web;

import org.jboss.seam.security.events.PostLoggedOutEvent;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Invalidates session when user logs out.
 */
@RequestScoped
public class SessionInvalidator {
// ------------------------------ FIELDS ------------------------------

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private HttpServletRequest httpRequest;

// -------------------------- OTHER METHODS --------------------------

    /**
     * Listener for user logs out event. Invalidates http session.
     *
     * @param event logout event
     */
    @SuppressWarnings("UnusedDeclaration")
    public void handlePostLoggedOutEvent(@Observes final PostLoggedOutEvent event)
    {
        httpRequest.getSession().invalidate();
    }
}
