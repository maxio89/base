package pl.itcrowd.base.user.view;

import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Backing bean for remindPassword page
 */
@Named
@ViewScoped
public class RemindPasswordView implements Serializable {

    private UserHome userHome;

    private Messages messages;

    private boolean passwordResetInitiationSuccessful = false;

    @SuppressWarnings("UnusedDeclaration")
    public RemindPasswordView()
    {
    }

    @Inject
    public RemindPasswordView(UserHome userHome, Messages messages)
    {
        this.userHome = userHome;
        this.messages = messages;
    }

    private String email;

    /**
     * Remind button action
     * This method saves new token in DB, and set passwordResetInitiationSuccessful flag.
     */
    public void initiatePasswordReset()
    {
        try {
            userHome.loadByEmail(email);
        } catch (NoResultException nre) {
            messages.error(BundleKeys.EMAIL_NOT_FOUND);
            return;
        }

        passwordResetInitiationSuccessful = userHome.initiatePasswordReset();

        if (passwordResetInitiationSuccessful) {
            messages.info(BundleKeys.RESET_PASSWORD_EMAIL_SENT);
        } else {
            messages.error(BundleKeys.RESET_PASSWORD_TOKEN_EXISTS);
        }
    }

    @NotNull
    @NotEmpty
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
