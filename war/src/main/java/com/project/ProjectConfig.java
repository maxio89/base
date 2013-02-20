package com.project;

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

@Named
@ApplicationScoped
public class ProjectConfig extends ApplicationConfig implements Serializable {
// ------------------------------ FIELDS ------------------------------

  private String defaultLanguage;

  private String emailFromAddress;

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

  @SuppressWarnings("UnusedDeclaration")
  protected void onStartup(@Observes @Initialized WebApplication ignore)
  {
    init();
    reload();
  }

  protected void reload()
  {
    emailFromAddress = load(KEY.EMAIL_FROM_ADDRESS);
    replyToEmail = load(KEY.REPLY_TO_EMAIL);
    defaultLanguage = load(KEY.DEFAULT_LANGUAGE);
  }

// -------------------------- ENUMERATIONS --------------------------

  private static enum KEY {
    DEFAULT_LANGUAGE,
    REPLY_TO_EMAIL,
    EMAIL_FROM_ADDRESS
  }
}
