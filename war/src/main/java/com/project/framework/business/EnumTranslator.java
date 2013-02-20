package com.project.framework.business;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.builder.BundleKey;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class EnumTranslator {
// ------------------------------ FIELDS ------------------------------

    @Inject
    private MessageFactory messageFactory;

// -------------------------- OTHER METHODS --------------------------

    public String translate(Enum value)
    {
        if (value == null) {
            return messageFactory.info(new BundleKey("business", EnumTranslator.class.getCanonicalName() + "noData")).build().getText();
        }
        return messageFactory.info(new BundleKey("domain", value.getClass().getCanonicalName() + "." + value.toString())).build().getText();
    }
}
