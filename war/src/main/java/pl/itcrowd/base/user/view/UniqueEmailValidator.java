package pl.itcrowd.base.user.view;

import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import pl.itcrowd.base.web.BundleKeys;

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

    private UserHome userHome;

    private BundleTemplateMessage messageBuilder;

    @SuppressWarnings("UnusedDeclaration")
    public UniqueEmailValidator()
    {
    }

    @Inject
    public UniqueEmailValidator(UserHome userHome, BundleTemplateMessage messageBuilder)
    {
        this.userHome = userHome;
        this.messageBuilder = messageBuilder;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Validator ---------------------

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
    {
        String email = (String) value;
        try {
            userHome.loadByEmail(email);
            final String text = messageBuilder.key(BundleKeys.EMAIL_ALREADY_REGISTERED)
                .defaults(BundleKeys.EMAIL_ALREADY_REGISTERED.toString())
                .build()
                .getText();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
        } catch (NoResultException ignore) {
            //email is unique
        }
    }
}
