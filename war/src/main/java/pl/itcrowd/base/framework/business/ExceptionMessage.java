package pl.itcrowd.base.framework.business;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.builder.BundleKey;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ExceptionMessage implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private String internationalizedMessage;

    private Serializable message;

    @Inject
    private Instance<MessageFactory> messageFactoryInstance;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getInternationalizedMessage()
    {
        if (message instanceof BundleKey) {
            internationalizedMessage = messageFactoryInstance.get().error((BundleKey) message).build().getText();
        } else {
            internationalizedMessage = message == null ? null : message.toString();
        }
        return internationalizedMessage;
    }

    public Serializable getMessage()
    {
        return message;
    }

// -------------------------- OTHER METHODS --------------------------

    public void setMessage(Serializable message)
    {
        this.message = message;
        internationalizedMessage = null;
    }
}
