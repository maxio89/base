package com.project.user.view;

import com.project.security.PasswordDigester;
import com.project.user.business.UserHome;
import com.project.web.BundleKeys;
import org.jboss.seam.international.status.Messages;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.io.Serializable;

/**
 * User: Rafal Gielczowski
 * Date: 2/22/13 Time: 4:30 PM
 * <p/>
 * Class validate reset link request, and allows user to add new password.
 */
@Named
@ViewScoped
public class ForgottenPasswordView implements Serializable {

    @Inject
    private UserHome userHome;

    @Inject
    private Messages messages;

    private String emailAddress;

    private String token;

    private boolean tokenValid = false;

    private PasswordBean passwordBean = new PasswordBean();

    /**
     * view action, should check view params, and decide if reset password attempt is valid
     * This method will set tokenValid to true, if all ok
     */
    public void validate()
    {
        if (emailAddress == null || token == null) {
            messages.error(BundleKeys.RESET_PASSWORD_WRONG_ACTIVATION_LINK);
        }

        try {
            userHome.getByEmail(emailAddress);
        } catch (NoResultException nre) {
            messages.error(BundleKeys.RESET_PASSWORD_WRONG_ACTIVATION_LINK);
            return;
        }

        if (!userHome.validatePasswordToken(emailAddress, token)) {
            messages.error(BundleKeys.RESET_PASSWORD_WRONG_ACTIVATION_LINK);
            return;
        }

        tokenValid = true;
    }

    public String changePassword()
    {
        userHome.getByEmail(emailAddress);
        userHome.getInstance().setPasswordDigest(new PasswordDigester().getDigest(passwordBean.getPassword()));
        userHome.persist();

        return "changedPassword";
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

    public boolean isTokenValid()
    {
        return tokenValid;
    }

    public PasswordBean getPasswordBean()
    {
        return passwordBean;
    }
}
