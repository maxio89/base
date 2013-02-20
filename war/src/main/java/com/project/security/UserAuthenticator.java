package com.project.security;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.picketlink.idm.impl.api.model.SimpleUser;

public class UserAuthenticator extends BaseAuthenticator implements Authenticator {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Authenticator ---------------------

    @Override
    public void authenticate()
    {
//        TODO currently we also need to login to beta and we don't have user management yet
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        if (facesContext != null && ProjectStage.Development.equals(facesContext.getApplication().getProjectStage())) {
        setStatus(AuthenticationStatus.SUCCESS);
        setUser(new SimpleUser("developer"));
//        } else {
//            setStatus(AuthenticationStatus.FAILURE);
//        }
    }
}
