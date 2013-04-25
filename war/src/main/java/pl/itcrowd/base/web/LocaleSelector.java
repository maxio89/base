package pl.itcrowd.base.web;

import org.jboss.seam.international.Alter;
import org.jboss.solder.core.Client;

import javax.annotation.Nonnull;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleSelector implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private List<Locale> availableLocales;

    @Inject
    @Alter
    @Client
    private Event<Locale> localeChangeEvent;

    @Inject
    @Client
    private Instance<Locale> userLocale;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Nonnull
    public List<Locale> getAvailableLocales()
    {
        if (availableLocales == null) {
            availableLocales = new ArrayList<Locale>();
            Iterator<Locale> localeIterator = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
            while (localeIterator.hasNext()) {
                availableLocales.add(localeIterator.next());
            }
        }
        return availableLocales;
    }

// -------------------------- OTHER METHODS --------------------------

    public Locale getSelectedLocale()
    {
        return userLocale.get();
    }

    public void setSelectedLocale(Locale locale)
    {
        localeChangeEvent.fire(locale);
    }
}
