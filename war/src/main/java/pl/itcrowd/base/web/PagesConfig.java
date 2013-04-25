package pl.itcrowd.base.web;

import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import pl.itcrowd.base.security.annotations.AccessDenied;
import pl.itcrowd.base.security.annotations.Authenticated;
import pl.itcrowd.base.security.annotations.permissions.AdminRole;

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
        @Authenticated @ViewPattern("/view/private/*") @LoginView("/view/login.xhtml") @AccessDeniedView("/view/401.xhtml") @FacesRedirect @RestrictAtPhase(
            {PhaseIdType.RESTORE_VIEW, PhaseIdType.INVOKE_APPLICATION, PhaseIdType.PROCESS_VALIDATIONS, PhaseIdType.UPDATE_MODEL_VALUES, PhaseIdType.APPLY_REQUEST_VALUES,
                PhaseIdType.RENDER_RESPONSE})
        USER,
        @Authenticated @AdminRole @ViewPattern("/view/private/admin/*") @LoginView("/view/login.xhtml") @AccessDeniedView(
            "/view/401.xhtml") @FacesRedirect @RestrictAtPhase(
            {PhaseIdType.RESTORE_VIEW, PhaseIdType.INVOKE_APPLICATION, PhaseIdType.PROCESS_VALIDATIONS, PhaseIdType.UPDATE_MODEL_VALUES, PhaseIdType.APPLY_REQUEST_VALUES,
                PhaseIdType.RENDER_RESPONSE})
        ADMIN
    }
}
