package pl.itcrowd.base.setting.business;

import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;
import org.jboss.solder.servlet.WebApplication;
import org.jboss.solder.servlet.event.Initialized;
import pl.itcrowd.utils.config.ApplicationConfig;

import javax.enterprise.event.Observes;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

/**
 * Application settings.
 * All settings values specified in enum at the end of this class,
 * should be initialized in .sql scripts.
 */
@SuppressWarnings("UnusedDeclaration")
@ApplicationScoped
public class ProjectConfig extends ApplicationConfig implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private String activeAccountUrl;

    private String appURL;

    private String defaultLanguage;

    private String emailFromAddress;

    private String emailFromName;

    @SuppressWarnings({"CdiInjectionPointsInspection", "UnusedDeclaration"})
    @Inject
    private Logger logger;

    private String passwordResetLink;

    private Boolean production;

    private String replyToEmail;


// --------------------- GETTER / SETTER METHODS ---------------------

    public String getActiveAccountUrl()
    {
        return activeAccountUrl;
    }

    public void setActiveAccountUrl(String activeAccountUrl)
    {
        save(KEY.ACTIVE_ACCOUNT_URL, activeAccountUrl);
        this.activeAccountUrl = activeAccountUrl;
    }

    public String getAppURL()
    {
        return appURL;
    }

    public void setAppURL(String appURL)
    {
        save(KEY.APP_URL, appURL);
        this.appURL = appURL;
    }

    public String getDefaultLanguage()
    {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage)
    {
        save(KEY.DEFAULT_LANGUAGE, defaultLanguage);
        this.defaultLanguage = defaultLanguage;
    }

    public String getEmailFromAddress()
    {
        return emailFromAddress;
    }

    public void setEmailFromAddress(String emailFromAddress)
    {
        save(KEY.EMAIL_FROM_ADDRESS, emailFromAddress);
        this.emailFromAddress = emailFromAddress;
    }

    public String getEmailFromName()
    {
        return emailFromName;
    }

    public void setEmailFromName(String emailFromName)
    {
        save(KEY.REPLY_TO_EMAIL, emailFromName);
        this.emailFromName = emailFromName;
    }

    public String getPasswordResetLink()
    {
        return passwordResetLink;
    }

    public void setPasswordResetLink(String passwordResetLink)
    {
        save(KEY.PASSWORD_RESET_LINK, passwordResetLink);
        this.passwordResetLink = passwordResetLink;
    }

    public String getReplyToEmail()
    {
        return replyToEmail;
    }

    public void setReplyToEmail(String replyToEmail)
    {
        save(KEY.REPLY_TO_EMAIL, replyToEmail);
        this.replyToEmail = replyToEmail;
    }

    @SuppressWarnings("UnusedDeclaration")
    public boolean isProduction()
    {
        if (production == null) {
            try {
                production = Boolean.parseBoolean(new InitialContext().lookup("java:comp/env/jsf/ProjectStage").toString());
            } catch (NamingException e) {
                production = false;
                logger.errorv(e, "Cannot lookup JSF project stage in JNDI");
            }
        }
        return production;
    }

    /**
     * Method starts on application startup (seam event)
     *
     * @param ignore
     */
    @Transactional
    @SuppressWarnings({"UnusedDeclaration", "JavaDoc"})
    protected void onStartup(@Observes @Initialized WebApplication ignore) throws Exception
    {
        init();
        reload();
    }

    protected void reload()
    {
        emailFromName = load(KEY.EMAIL_FROM_NAME);
        emailFromAddress = load(KEY.EMAIL_FROM_ADDRESS);
        replyToEmail = load(KEY.REPLY_TO_EMAIL);
        defaultLanguage = load(KEY.DEFAULT_LANGUAGE);
        appURL = load(KEY.APP_URL);
        activeAccountUrl = load(KEY.ACTIVE_ACCOUNT_URL);
        passwordResetLink = load(KEY.PASSWORD_RESET_LINK);
    }


// -------------------------- ENUMERATIONS --------------------------

    /**
     * Application specific settings.
     * They are must be exists in DB before deploy.
     */
    public static enum KEY {
        REPLY_TO_EMAIL,
        EMAIL_FROM_NAME,
        EMAIL_FROM_ADDRESS,
        DEFAULT_LANGUAGE,
        APP_URL,
        ACTIVE_SUBSCRIPTION_URL,
        ACTIVE_ACCOUNT_URL,
        PASSWORD_RESET_LINK,
    }
}
