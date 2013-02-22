package com.project.mail;

import com.project.ProjectConfig;
import com.project.domain.User;
import com.project.web.BundleKeys;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import org.jboss.seam.mail.api.MailMessage;
import org.jboss.seam.mail.core.EmailContact;
import org.jboss.seam.mail.templating.freemarker.FreeMarkerTemplate;
import org.jboss.solder.resourceLoader.ResourceProvider;
import pl.com.it_crowd.seam3.mailman.Mailman;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ProjectMailman {

    @Inject
    private ProjectConfig projectConfig;

    @Inject
    private Mailman mailman;

    @Inject
    private MailMessage mailMessage;

    @Inject
    private BundleTemplateMessage messageBuilder;

    @Inject
    private ResourceProvider resourceProvider;

    /**
     * Method send registration mail from 'registration.html.ftl' template.
     * Email's FROM section will be fetched from ProjectConfig setting.
     *
     * @param user          recipient
     * @param activationURL URL with activation link
     */
    public void sendRegistrationMail(User user, String activationURL)
    {

        final String subject = messageBuilder.key(BundleKeys.REGISTER_EMAIL_SUBJECT).defaults(BundleKeys.EMAIL_ALREADY_REGISTERED.toString()).build().getText();

        final Map<String, Object> context = new HashMap<String, Object>();
        context.put("activationURL", activationURL);

        mailMessage.from(new EmailContactImpl(projectConfig.getEmailFromName(), projectConfig.getEmailFromAddress()))
            .replyTo(projectConfig.getReplyToEmail())
            .to(user.getEmail())
            .subject(subject)
            .bodyHtml(new FreeMarkerTemplate(resourceProvider.loadResourceStream("mail/registration.html.ftl")))
            .put(context);
        mailman.send(mailMessage.mergeTemplates());
    }


// -------------------------- INNER CLASSES --------------------------

    private static final class EmailContactImpl implements EmailContact {
// ------------------------------ FIELDS ------------------------------

        private final String address;

        private final String name;

// --------------------------- CONSTRUCTORS ---------------------------

        private EmailContactImpl(String name, String address)
        {
            this.name = name;
            this.address = address;
        }

// --------------------- GETTER / SETTER METHODS ---------------------

        @Override
        public String getAddress()
        {
            return address;
        }

        @Override
        public String getName()
        {
            return name;
        }
    }
}
