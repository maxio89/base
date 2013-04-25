package pl.itcrowd.base.user.view;

import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.Constants;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Class validate reset link request, and allows user to add new password.
 */
@Named
@ViewScoped
public class ForgottenPasswordView implements Serializable {

    private UserHome userHome;

    private Messages messages;

    private String emailAddress;

    private String token;

    private boolean tokenAndEmailProvided = false;

    private PasswordBean passwordBean = new PasswordBean();

    @SuppressWarnings("UnusedDeclaration")
    public ForgottenPasswordView()
    {
    }

    @Inject
    public ForgottenPasswordView(UserHome userHome, Messages messages)
    {
        this.userHome = userHome;
        this.messages = messages;
    }

    /**
     * Method save password changes if token was valid.
     *
     * @return outcome
     */
    public String changePassword()
    {
        if (passwordBean.getPassword() == null || token == null || emailAddress == null) {
            throw new IllegalArgumentException("Password/token/email was null");
        }

        if (!passwordBean.isPasswordMatch()) {
            messages.error(BundleKeys.PASSWORDS_DONT_MATCH);
            return Constants.OUTCOME_FAILURE;
        }

        boolean result = userHome.changePassword(emailAddress, passwordBean.getPassword(), token);
        if (result) {
            messages.info(BundleKeys.RESET_PASSWORD_SUCCESSFUL);
            return Constants.OUTCOME_SUCCESS;
        } else {
            messages.info(BundleKeys.RESET_PASSWORD_FAILURE);
            return Constants.OUTCOME_FAILURE;
        }
    }

    /**
     * View action, should check view params, and decide if reset password attempt is valid
     * This method will set tokenAndEmailProvided to true, if all is ok.
     */
    public void validate()
    {
        if (emailAddress == null || token == null) {
            messages.error(BundleKeys.RESET_PASSWORD_WRONG_ACTIVATION_LINK);
            tokenAndEmailProvided = false;
        } else {
            tokenAndEmailProvided = true;
        }
    }

    //---------- GETTERS & SETTERS ------------------

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public boolean isTokenAndEmailProvided()
    {
        return tokenAndEmailProvided;
    }

    public PasswordBean getPasswordBean()
    {
        return passwordBean;
    }
}
