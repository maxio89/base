package pl.itcrowd.base.user.view;

import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.business.UserHome;
import org.apache.commons.lang.StringUtils;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.business.UserHome;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

@Named
@RequestScoped
public class ActivateAccountView {

    private UserHome userHome;

    private boolean activationSuccess = false;

    private String email;

    private String token;

    @SuppressWarnings("UnusedDeclaration")
    public ActivateAccountView()
    {
    }

    @Inject
    public ActivateAccountView(UserHome userHome)
    {
        this.userHome = userHome;
    }

    public boolean isActivationSuccess()
    {
        return activationSuccess;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public void activate()
    {
        final User user;

        try {
            user = userHome.loadByEmail(this.email);
        } catch (NoResultException nre) {
            return;
        }

        if (user == null || (!user.isActive() && !userHome.activate(email, token))) {
            return;
        }
        activationSuccess = true;
    }

    public boolean isActivationAttempt()
    {
        return StringUtils.isBlank(email) || !StringUtils.isBlank(token);
    }
}
