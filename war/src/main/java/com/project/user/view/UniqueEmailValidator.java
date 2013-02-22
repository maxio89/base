package com.project.user.view;

import com.project.user.business.UserHome;
import com.project.web.BundleKeys;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.persistence.NoResultException;

@FacesValidator("uniqueEmailValidator")
@RequestScoped
public class UniqueEmailValidator implements Validator {
// ------------------------------ FIELDS ------------------------------

    @Inject
    private BundleTemplateMessage messageBuilder;

    @Inject
    private UserHome userHome;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Validator ---------------------

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        String email = (String) value;
        try {
            userHome.getByEmail(email);
            final String text = messageBuilder.key(BundleKeys.EMAIL_ALREADY_REGISTERED)
                .defaults(BundleKeys.EMAIL_ALREADY_REGISTERED.toString())
                .build()
                .getText();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
        } catch (NoResultException e) {
            //email is unique
        }
    }
}
