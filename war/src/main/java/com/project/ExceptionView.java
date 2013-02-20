package com.project;

import org.jboss.solder.exception.control.ExceptionStack;
import org.jboss.solder.exception.control.ExceptionToCatch;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequestScoped
@Named
public class ExceptionView {
// ------------------------------ FIELDS ------------------------------

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    @Named("caughtException")
    private ExceptionToCatch caughtException;

    private List<Throwable> causes;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    @Named("handledException")
    private ExceptionStack handledException;

// --------------------- GETTER / SETTER METHODS ---------------------

    public List<Throwable> getCauses()
    {
        if (causes == null) {
            causes = new ArrayList<Throwable>();
            //Workaround to NPE in ExceptionStack
            //noinspection ThrowableResultOfMethodCallIgnored
            if (handledException.getCurrent() != null) {
                Collection<Throwable> causeElements = handledException.getCauseElements();
                if (causeElements != null) {
                    causes.addAll(causeElements);
                }
            }
        }
        return causes;
    }
}
