package pl.itcrowd.base.mail;

import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.setting.business.ProjectConfig;
import pl.itcrowd.base.web.BundleKeys;
import pl.itcrowd.base.web.LocaleSelector;
import org.apache.commons.lang.LocaleUtils;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import org.jboss.seam.mail.api.MailMessage;
import org.jboss.seam.mail.core.EmailContact;
import org.jboss.seam.mail.templating.freemarker.FreeMarkerTemplate;
import org.jboss.solder.resourceLoader.ResourceProvider;
import pl.itcrowd.seam3.mailman.Mailman;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Locale;
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

    @Inject
    private LocaleSelector localeSelector;

    @Inject
    private Instance<BundleTemplateMessage> messageBuilderFactory;

    /**
     * This method send mail with link to change password from 'passwordChange.html.ftl' template.
     * Email's FROM section will be fetched from ProjectConfig setting.
     *
     * @param user          recipient
     * @param passChangeURL URL with password change link
     */
    public void sendPasswordChangeMail(User user, String passChangeURL)
    {
        Locale before = localeSelector.getSelectedLocale();
        Locale localeForMail = LocaleUtils.toLocale(user.getClientLanguage());
        localeSelector.setSelectedLocale(localeForMail);

        BundleTemplateMessage messageBuilder = messageBuilderFactory.get();
        final String subject = messageBuilder.key(BundleKeys.RESET_PASSWORD_EMAIL_SUBJECT).defaults("Reset password default subject").build().getText();
        final String dearUser = messageBuilder.key(BundleKeys.MAIL_DEAR_USER_HEADER).params(user.getFirstName()).build().getText();
        final String thanks = messageBuilder.key(BundleKeys.MAIL_RESET_PASS_THANKS).build().getText();
        final String linkInfo = messageBuilder.key(BundleKeys.MAIL_RESET_PASS_ACTIV_LINK_INFO).build().getText();
        final String thankYou = messageBuilder.key(BundleKeys.MAIL_THANK_YOU_TXT).build().getText();
        final String firmName = messageBuilder.key(BundleKeys.MAIL_FIRM_NAME).build().getText();

        final Map<String, Object> context = new HashMap<String, Object>();
        context.put("resetURL", passChangeURL);
        context.put("dearUser", dearUser);
        context.put("thanks", thanks);
        context.put("linkInfo", linkInfo);
        context.put("thankYou", thankYou);
        context.put("firmName", firmName);

        mailMessage.from(new EmailContactImpl(projectConfig.getEmailFromName(), projectConfig.getEmailFromAddress()))
            .replyTo(projectConfig.getReplyToEmail())
            .to(user.getEmail())
            .subject(subject)
            .bodyHtml(new FreeMarkerTemplate(resourceProvider.loadResourceStream("mail/resetPassword.html.ftl")))
            .put(context);

        mailman.send(mailMessage.mergeTemplates());
        localeSelector.setSelectedLocale(before);
    }

    /**
     * Method send registration mail from 'registration.html.ftl' template.
     * Email's FROM section will be fetched from ProjectConfig setting.
     *
     * @param user             recipient
     * @param activeAccountUrl URL with activation link
     */
    public void sendRegistrationMail(User user, String activeAccountUrl)
    {
        BundleTemplateMessage messageBuilder = messageBuilderFactory.get();
        final String subject = messageBuilder.key(BundleKeys.REGISTER_EMAIL_SUBJECT).build().getText();
        final String dearUser = messageBuilder.key(BundleKeys.MAIL_DEAR_USER_HEADER).params(user.getFirstName()).build().getText();
        final String thanks = messageBuilder.key(BundleKeys.MAIL_REGISTRATION_THANKS).build().getText();
        final String linkInfo = messageBuilder.key(BundleKeys.MAIL_REGISTRATION_ACTIV_LINK_INFO).build().getText();
        final String thankYou = messageBuilder.key(BundleKeys.MAIL_THANK_YOU_TXT).build().getText();
        final String firmName = messageBuilder.key(BundleKeys.MAIL_FIRM_NAME).build().getText();

        final Map<String, Object> context = new HashMap<String, Object>();
        context.put("activeAccountUrl", activeAccountUrl);
        context.put("dearUser", dearUser);
        context.put("thanks", thanks);
        context.put("linkInfo", linkInfo);
        context.put("thankYou", thankYou);
        context.put("firmName", firmName);

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
