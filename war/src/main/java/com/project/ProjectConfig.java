package com.project;

import com.project.language.Language;
import org.jboss.solder.logging.Logger;
import org.jboss.solder.servlet.WebApplication;
import org.jboss.solder.servlet.event.Initialized;
import pl.com.it_crowd.utils.config.ApplicationConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

/**
 * Application settings.
 * All settings values specified in enum at the end of this class,
 * should be initialized in .sql scripts.
 */

@Named
@ApplicationScoped
public class ProjectConfig extends ApplicationConfig implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private String emailFromAddress;

    private String emailFromName;

    private String defaultLanguage;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger logger;

    private Boolean production;

    private String replyToEmail;

// --------------------- GETTER / SETTER METHODS ---------------------

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

    public String getReplyToEmail()
    {
        return replyToEmail;
    }

    public void setReplyToEmail(String replyToEmail)
    {
        save(KEY.REPLY_TO_EMAIL, replyToEmail);
        this.replyToEmail = replyToEmail;
    }

    public String getEmailFromName()
    {
        return emailFromName;
    }

    public void setEmailFromName(String emailFromName)
    {
        save(KEY.REPLY_TO_EMAIL, replyToEmail);
        this.emailFromName = emailFromName;
    }

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
     * @param ignore
     */
    @SuppressWarnings("UnusedDeclaration")
    protected void onStartup(@Observes @Initialized WebApplication ignore)
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
    }

// -------------------------- ENUMERATIONS --------------------------

    /**
     * Application specific settings.
     * They are must be exists in DB before deploy.
     */
    private static enum KEY {
        REPLY_TO_EMAIL,
        EMAIL_FROM_NAME,
        EMAIL_FROM_ADDRESS,
        DEFAULT_LANGUAGE
    }
}
