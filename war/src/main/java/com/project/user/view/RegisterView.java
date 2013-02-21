package com.project.user.view;

import com.project.domain.User;
import com.project.security.PasswordDigester;
import com.project.user.business.UserHome;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

@Named
@ViewScoped
public class RegisterView {

    private PasswordBean passwordBean;

    @Inject
    private UserHome userHome;

    public PasswordBean getPasswordBean()
    {
        return passwordBean;
    }

    public void setPasswordBean(PasswordBean passwordBean)
    {
        this.passwordBean = passwordBean;
    }


    public User getUser(){
        return userHome.getInstance();
    }

    public String register(){
        final User user = getUser();
        user.setRegistrationDate(new Date());
        user.setPasswordDigest(new PasswordDigester().getDigest(passwordBean.getPassword()));

        //TODO redirect to activation
        return userHome.persist() ? "success" : "failure";
    }


}
