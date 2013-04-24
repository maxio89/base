package pl.itcrowd.base.web;

import pl.itcrowd.base.security.annotations.AccessDenied;
import pl.itcrowd.base.security.annotations.Admin;
import pl.itcrowd.base.security.annotations.Authenticated;
import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import pl.itcrowd.base.security.annotations.AccessDenied;
import pl.itcrowd.base.security.annotations.Admin;
import pl.itcrowd.base.security.annotations.Authenticated;

@SuppressWarnings("UnusedDeclaration")
@ViewConfig
public interface PagesConfig {
// -------------------------- ENUMERATIONS --------------------------

    @SuppressWarnings("UnusedDeclaration")
    static enum Pages {
        @AccessDenied @RestrictAtPhase(PhaseIdType.RESTORE_VIEW) @ViewPattern("/resources/components/*")
        COMPONENTS,
        @AccessDenied @RestrictAtPhase(PhaseIdType.RESTORE_VIEW) @ViewPattern("/layout/*")
        LAYOUTS,
        @Authenticated @RestrictAtPhase(PhaseIdType.RESTORE_VIEW) @ViewPattern("/view/user/*") @LoginView("/view/login.xhtml") @AccessDeniedView(
            "/view/401.xhtml")
        PRIVATE,
        @Authenticated @Admin @RestrictAtPhase(
            {PhaseIdType.RESTORE_VIEW, PhaseIdType.INVOKE_APPLICATION, PhaseIdType.PROCESS_VALIDATIONS, PhaseIdType.UPDATE_MODEL_VALUES,
                PhaseIdType.APPLY_REQUEST_VALUES, PhaseIdType.RENDER_RESPONSE}) @ViewPattern("/view/admin/*") @LoginView("/view/login.xhtml") @AccessDeniedView(
            "/view/401.xhtml")
        ADMIN,
        @FacesRedirect @ViewPattern("/view/*") @AccessDeniedView("/view/401.xhtml") @LoginView("/view/login.xhtml")
        ALL
    }
}
