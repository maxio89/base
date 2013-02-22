package com.project.user.view;

import com.project.domain.User;
import com.project.user.business.UserHome;
import org.apache.commons.lang.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

@Named
@RequestScoped
public class ActivateAccountView {

    @Inject
    private UserHome userHome;

    private boolean activationSuccess = false;

    private String email;

    private String token;

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
            user = userHome.getByEmail(this.email);
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
