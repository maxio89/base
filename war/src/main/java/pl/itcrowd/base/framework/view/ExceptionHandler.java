package pl.itcrowd.base.framework.view;

import pl.itcrowd.base.framework.business.ExceptionMessage;
import pl.itcrowd.base.web.BundleKeys;
import org.jboss.seam.faces.qualifier.Faces;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.jboss.solder.exception.control.Precedence;
import org.jboss.solder.exception.control.TraversalMode;
import org.jboss.solder.logging.Logger;

import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("UnusedDeclaration")
@HandlesExceptions
public class ExceptionHandler {
// ------------------------------ FIELDS ------------------------------

    @SuppressWarnings({"CdiInjectionPointsInspection"})
    @Inject
    private Logger log;

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings({"UnusedDeclaration"})
    public void handleEntityNotFoundException(@Handles @Faces CaughtException<EntityNotFoundException> event, ExceptionMessage message, FacesContext context)
    {
        message.setMessage(BundleKeys.ENTITY_NOT_FOUND);
        redirect(context, "/view/404.jsf");
        event.handled();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void handleException(@Handles(precedence = Precedence.BUILT_IN, during = TraversalMode.DEPTH_FIRST) @Faces CaughtException<Throwable> event,
                                FacesContext context)
    {
        //noinspection ThrowableResultOfMethodCallIgnored
        log.infov("Handling exception {0}", event.getException());
        redirect(context, "/view/error.jsf");
        event.handled();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void handleNonexistentConversationException(@Handles @Faces CaughtException<NonexistentConversationException> event, ExceptionMessage message,
                                                       FacesContext context)
    {
        handleSessionTimeout(context, message);
        event.handled();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void handleViewExpiredException(@Handles @Faces CaughtException<ViewExpiredException> event, ExceptionMessage message, FacesContext context)
    {
        handleSessionTimeout(context, message);
        event.handled();
    }

    private void handleSessionTimeout(FacesContext context, ExceptionMessage message)
    {
        message.setMessage(BundleKeys.SESSION_TIMEOUT);
        redirect(context, "/view/login.jsf");
    }

    private void redirect(FacesContext context, String path)
    {
        try {
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            if (!response.isCommitted()) {
                response.reset();
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + path);
            } else {
                log.errorv("Cannot redirect to {0} because response has already been commited", path);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
