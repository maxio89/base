package pl.itcrowd.base.user.view;

import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.security.PasswordDigester;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;
import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.security.PasswordDigester;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named
@ViewScoped
public class RegisterView implements Serializable {

    private PasswordBean passwordBean = new PasswordBean();

    private UserHome userHome;

    private Messages messages;

    private boolean registerCompleted = false;

    public RegisterView()
    {
    }

    @Inject
    public RegisterView(UserHome userHome, Messages messages)
    {
        this.userHome = userHome;
        this.messages = messages;
    }

    public PasswordBean getPasswordBean()
    {
        return passwordBean;
    }

    public void setPasswordBean(PasswordBean passwordBean)
    {
        this.passwordBean = passwordBean;
    }

    public User getUser()
    {
        return userHome.getInstance();
    }

    public boolean isRegisterCompleted()
    {
        return registerCompleted;
    }

    public void register()
    {
        if (passwordBean == null || (passwordBean.getPassword() == null || passwordBean.getPasswordConfirmation() == null) || !passwordBean.isPasswordMatch()) {
            messages.error(BundleKeys.WRONG_PASSWORD_DATA);
            return;
        }

        final User user = getUser();
        user.setRegistrationDate(new Date());
        user.setPasswordDigest(new PasswordDigester().getDigest(passwordBean.getPassword()));

        registerCompleted = userHome.persist();

    }
}
