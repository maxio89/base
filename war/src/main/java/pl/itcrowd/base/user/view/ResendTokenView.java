package pl.itcrowd.base.user.view;

import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;
import org.hibernate.validator.constraints.Email;
import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Named
@RequestScoped
public class ResendTokenView implements Serializable {

    private Messages messages;

    @Email
    @Size(min = 3, max = 255)
    private String userEmail = "";

    private UserHome userHome;

    @SuppressWarnings("UnusedDeclaration")
    public ResendTokenView()
    {
    }

    @Inject
    public ResendTokenView(UserHome userHome, Messages messages)
    {
        this.messages = messages;
        this.userHome = userHome;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public void resendToken()
    {
        if (userEmail != null && !userEmail.isEmpty()) {
            if (userHome.resendActivationMail(userEmail)) {
                messages.info(BundleKeys.ACTIVATION_MAIL_RESENT);
            } else {
                messages.error(BundleKeys.ACTIVATION_MAIL_RESENT_ERROR);
            }
        } else {
            messages.error(BundleKeys.EMAIL_NOT_WELL_FORMED);
        }
    }
}

