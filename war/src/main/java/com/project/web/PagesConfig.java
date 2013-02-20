package com.project.web;

import com.project.security.annotations.AccessDenied;
import com.project.security.annotations.Authenticated;
import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

@ViewConfig
public interface PagesConfig {
// -------------------------- ENUMERATIONS --------------------------

    static enum Pages {
        @AccessDenied @RestrictAtPhase(PhaseIdType.RESTORE_VIEW) @ViewPattern("/resources/components/*")
        COMPONENTS,
        @AccessDenied @RestrictAtPhase(PhaseIdType.RESTORE_VIEW) @ViewPattern("/layout/*")
        LAYOUTS,
        @Authenticated @RestrictAtPhase(PhaseIdType.RESTORE_VIEW) @ViewPattern("/view/private/*")
        PRIVATE,
        @FacesRedirect @ViewPattern("/view/*") @AccessDeniedView("/view/denied.xhtml") @LoginView("/view/login.xhtml")
        ALL
    }
}
