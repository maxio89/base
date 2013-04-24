package pl.itcrowd.base.user.view;

import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.security.PasswordDigester;
import pl.itcrowd.base.user.CurrentUser;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;
import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.security.PasswordDigester;
import pl.itcrowd.base.user.CurrentUser;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ChangePasswordView implements Serializable {

    private boolean changeSuccessful = false;

    private User currentUser;

    private PasswordBean passwordBean = new PasswordBean();

    private UserHome userHome;

    private Messages messages;

    private String currentPassword;

    @SuppressWarnings("UnusedDeclaration")
    public ChangePasswordView()
    {
    }

    @Inject
    public ChangePasswordView(@CurrentUser User currentUser, UserHome userHome, Messages messages)
    {
        this.currentUser = currentUser;
        this.userHome = userHome;
        this.messages = messages;
    }

    public void changePassword()
    {
        if (passwordBean == null || (passwordBean.getPassword() == null || passwordBean.getPasswordConfirmation() == null) || (!passwordBean.isPasswordMatch()
            || !isCurrentPasswordMatch())) {
            messages.error(BundleKeys.WRONG_PASSWORD_DATA);
            return;
        }
        this.userHome.setId(currentUser.getId());
        userHome.getInstance().setPasswordDigest(new PasswordDigester().getDigest(passwordBean.getPassword()));
        changeSuccessful = userHome.update();
    }

    private boolean isCurrentPasswordMatch()
    {
        return currentUser != null && (currentUser.getPasswordDigest().equals(new PasswordDigester().getDigest(currentPassword)));
    }

    public PasswordBean getPasswordBean()
    {
        return passwordBean;
    }

    public boolean isChangeSuccessful()
    {
        return changeSuccessful;
    }

    public String getCurrentPassword()
    {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword)
    {
        this.currentPassword = currentPassword;
    }
}
