package com.project.user.view;

import com.project.user.business.UserHome;
import com.project.web.BundleKeys;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.seam.international.status.Messages;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: Rafal Gielczowski
 * Date: 2/25/13 Time: 4:43 PM
 * <p/>
 * Backing bean for remindPassword page
 */
@Named
@ViewScoped
public class RemindPasswordView implements Serializable {

    @Inject
    private UserHome userHome;

    @Inject
    private Messages messages;

    @Inject
    private FacesContext facesContext;

    private String email;

    /**
     * Remind button action
     */
    public void remindPassword()
    {
        try {
            userHome.getByEmail(email);
        } catch (NoResultException nre) {
            messages.error(BundleKeys.EMAIL_NOT_FOUND);
            return;
        }

        boolean result = userHome.saveResetPasswordRequest("/view/forgottenPassword.jsf?email=@email@&token=@token@");

        if (result) {
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
