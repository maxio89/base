package com.project.user.view;

import com.project.domain.User;
import com.project.security.PasswordDigester;
import com.project.user.business.UserHome;
import com.project.web.UrlHelper;

import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named
@ViewScoped
public class RegisterView implements Serializable {

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
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        user.setRegistrationDate(new Date());
        user.setPasswordDigest(new PasswordDigester().getDigest(passwordBean.getPassword()));

        return userHome.persist(UrlHelper.encodeURL(externalContext, "/view/activateAccount.jsf?email=@email@&token=@token@")) ? "success" : "failure";
    }


}
