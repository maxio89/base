package com.project.language;

import com.project.ProjectConfig;
import com.project.domain.Language;
import com.project.framework.business.ApplicationDefault;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class DefaultLanguageProducer {
// ------------------------------ FIELDS ------------------------------

    private Language defaultLanguage;

    @Inject
    private LanguageHome languageHome;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private ProjectConfig projectConfig;

// --------------------- GETTER / SETTER METHODS ---------------------

    @ApplicationDefault
    @Named
    @Produces
    public Language getDefaultLanguage()
    {
        if (defaultLanguage == null) {
            final String language = projectConfig.getDefaultLanguage();
            languageHome.clearInstance();
            defaultLanguage = languageHome.getLanguageByISO6391(language);
        }
        return defaultLanguage;
    }
}
