package pl.itcrowd.base.security.view;

import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;
import pl.itcrowd.base.security.PasswordDigester;

import javax.annotation.Nonnull;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginView implements Serializable {

    @Inject
    private Identity identity;

    @Inject
    private Credentials credentials;

    @Inject
    private RememberMeService rememberMeService;

    @Inject
    private PasswordDigester passwordDigester;

    private String username;

    private String password;

    private boolean rememberMeEnabled;

    @Nonnull
    public String login()
    {
        credentials.setUsername(username);
        credentials.setCredential(new PasswordCredential(passwordDigester.getDigest(password)));
        String response = identity.login();
        if (rememberMeEnabled && Identity.RESPONSE_LOGIN_SUCCESS.equals(response)) {
            rememberMeService.createRememberMeToken();
        }
        return response;
    }

    public boolean isRememberMeEnabled()
    {
        return rememberMeEnabled;
    }

    public void setRememberMeEnabled(boolean rememberMeEnabled)
    {
        this.rememberMeEnabled = rememberMeEnabled;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isAutoLogged()
    {
        return rememberMeService.isUserAutologged();
    }
}

