package pl.itcrowd.base.setting.view;

import pl.itcrowd.base.setting.business.ProjectConfig;
import pl.itcrowd.base.web.BundleKeys;
import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.setting.business.ProjectConfig;
import pl.itcrowd.base.web.BundleKeys;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProjectConfigView implements Serializable {

    private String activeAccountUrl;

    private String appURL;

    private String defaultLanguage;

    private boolean edit = false;

    private String emailFromAddress;

    private String emailFromName;

    private Messages messages;

    private String passwordResetLink;

    @SuppressWarnings({"UnusedDeclaration", "CdiInjectionPointsInspection"})
    @Inject
    private ProjectConfig projectConfig;

    private String replyToEmail;

    @SuppressWarnings("UnusedDeclaration")
    public ProjectConfigView()
    {
    }

    @SuppressWarnings({"CdiInjectPointsInspection", "UnusedDeclaration"})
    @Inject
    public ProjectConfigView(Messages messages)
    {
        this.messages = messages;
    }

    public String getActiveAccountUrl()
    {
        return projectConfig.getActiveAccountUrl();
    }

    public void setActiveAccountUrl(String activeAccountUrl)
    {
        this.activeAccountUrl = activeAccountUrl;
    }

    public String getAppURL()
    {
        return projectConfig.getAppURL();
    }

    public void setAppURL(String appURL)
    {
        this.appURL = appURL;
    }

    public String getDefaultLanguage()
    {
        return projectConfig.getDefaultLanguage();
    }

    public void setDefaultLanguage(String defaultLanguage)
    {
        this.defaultLanguage = defaultLanguage;
    }

    public String getEmailFromAddress()
    {
        return projectConfig.getEmailFromAddress();
    }

    public void setEmailFromAddress(String emailFromAddress)
    {
        this.emailFromAddress = emailFromAddress;
    }

    public String getEmailFromName()
    {
        return projectConfig.getEmailFromName();
    }

    public void setEmailFromName(String emailFromName)
    {
        this.emailFromName = emailFromName;
    }

    public String getPasswordResetLink()
    {
        return projectConfig.getPasswordResetLink();
    }

    public void setPasswordResetLink(String passwordResetLink)
    {
        this.passwordResetLink = passwordResetLink;
    }

    public String getReplyToEmail()
    {
        return projectConfig.getReplyToEmail();
    }

    public void setReplyToEmail(String replyToEmail)
    {
        this.replyToEmail = replyToEmail;
    }

    public void editAtr()
    {
        edit = true;
    }

    public boolean isEdit()
    {
        return edit;
    }

    public void save()
    {
        edit = false;
        projectConfig.setActiveAccountUrl(activeAccountUrl);
        projectConfig.setAppURL(appURL);
        projectConfig.setDefaultLanguage(defaultLanguage);
        projectConfig.setEmailFromAddress(emailFromAddress);
        projectConfig.setEmailFromName(emailFromName);
        projectConfig.setPasswordResetLink(passwordResetLink);
        projectConfig.setReplyToEmail(replyToEmail);
        messages.info(BundleKeys.DATA_SAVED_SUCCSSFULLY);
    }
}
