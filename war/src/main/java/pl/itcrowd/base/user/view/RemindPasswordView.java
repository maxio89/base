package pl.itcrowd.base.user.view;

import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Backing bean for remindPassword page
 */
@Named
@ViewScoped
public class RemindPasswordView implements Serializable {

    private UserHome userHome;

    private Messages messages;

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
        if (!userHome.loadByEmail(email)) {
            messages.error(BundleKeys.EMAIL_NOT_FOUND);
            return;
        }

        boolean passwordResetInitiationSuccessful = userHome.initiatePasswordReset();

        if (passwordResetInitiationSuccessful) {
            messages.info(BundleKeys.RESET_PASSWORD_EMAIL_SENT);
        } else {
            messages.error(BundleKeys.RESET_PASSWORD_TOKEN_EXISTS);
        }
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
