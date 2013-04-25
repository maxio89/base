package pl.itcrowd.base.user.business;

import org.apache.commons.lang.LocaleUtils;
import org.jboss.seam.security.events.LoggedInEvent;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.CurrentUser;
import pl.itcrowd.base.web.LocaleSelector;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Locale;

@SuppressWarnings("UnusedDeclaration")
@RequestScoped
public class UserLocaleChangedService {

    private User currentUser;

    private EntityManager entityManager;

    private LocaleSelector localeSelector;

    public UserLocaleChangedService()
    {
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public UserLocaleChangedService(Instance<EntityManager> emf, @CurrentUser User currentUser, LocaleSelector localeSelector)
    {
        this.entityManager = emf.get();
        this.currentUser = currentUser;
        this.localeSelector = localeSelector;
    }

    public void onUserLocaleChange(@Observes @UserLocaleChanged Locale locale)
    {
        if (currentUser != null && currentUser.getId() != null && !currentUser.getClientLanguage().equals(locale.getLanguage())) {
            final String query = "update User u set u.clientLanguage=:lang where u.id=:userId";
            entityManager.createQuery(query).setParameter("lang", locale.getLanguage()).setParameter("userId", currentUser.getId()).executeUpdate();
        }
    }

    public void onUserLogin(@Observes final LoggedInEvent event)
    {
        if (currentUser != null) {
            final String query = "select u.clientLanguage from User u where u.id=:userId";
            final String language = (String) entityManager.createQuery(query).setParameter("userId", currentUser.getId()).getSingleResult();
            Locale locale = LocaleUtils.toLocale(language);
            localeSelector.setSelectedLocale(locale);
        }
    }
}
