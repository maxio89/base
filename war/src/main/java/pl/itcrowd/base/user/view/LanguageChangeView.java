package pl.itcrowd.base.user.view;

import pl.itcrowd.base.user.business.UserLocaleChanged;
import pl.itcrowd.base.web.LocaleSelector;
import pl.itcrowd.base.user.business.UserLocaleChanged;
import pl.itcrowd.base.web.LocaleSelector;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class LanguageChangeView implements Serializable {

    @UserLocaleChanged
    private Event<Locale> localeChangeEvent;

    private LocaleSelector localeSelector;

    @SuppressWarnings("UnusedDeclaration")
    public LanguageChangeView()
    {
    }

    @Inject
    public LanguageChangeView(@UserLocaleChanged Event<Locale> localeChangeEvent, LocaleSelector localeSelector)
    {
        this.localeChangeEvent = localeChangeEvent;
        this.localeSelector = localeSelector;
    }

    public void changeUserLocale(Locale locale)
    {
        //save change in DB
        localeChangeEvent.fire(locale);
        //change language
        localeSelector.setSelectedLocale(locale);
    }
}
