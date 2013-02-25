package com.project.user.view;

import com.project.domain.User;
import com.project.user.business.UserHome;
import com.project.web.BundleKeys;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

/**
 * User: Rafal Gielczowski
 * Date: 2/22/13 Time: 4:30 PM
 */
@Named
@RequestScoped
public class ForgottenPasswordView {

    @Inject
    private UserHome userHome;

    @Inject
    private Messages messages;

    private String emailAddress;

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void remindPassword(){
        //TODO reminding password
        try{
            User user = userHome.getByEmail(emailAddress);
        }catch (NoResultException nre){
            messages.error("No email found! Sorry.");
        }
    }
}
