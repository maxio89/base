package pl.itcrowd.base.country.view;

import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.Constants;
import pl.itcrowd.base.country.business.CountryHome;
import pl.itcrowd.base.domain.Country;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ConversationScoped
public class CountryListView implements Serializable {

    private List<Country> allCountries = new ArrayList<Country>();

    private Conversation conversation;

    private CountryHome countryHome;

    private Messages messages;

    private Country selectedCountry;

    private UserHome userHome;

    @SuppressWarnings({"UnusedDeclaration", "CdiInjectionPointsInspection"})
    @Inject
    public CountryListView(CountryHome countryHome, UserHome userHome, Conversation conversation, Messages messages)
    {
        this.countryHome = countryHome;
        this.userHome = userHome;
        this.conversation = conversation;
        this.messages = messages;
    }

    @SuppressWarnings("UnusedDeclaration")
    public CountryListView()
    {
    }

    public List<Country> getAllCountries()
    {
        return allCountries;
    }

    public Country getEditedCountryHome()
    {
        return countryHome.getInstance();
    }

    public Country getSelectedCountry()
    {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry)
    {
        this.selectedCountry = selectedCountry;
        countryHome.clearInstance();
        if (selectedCountry == null || selectedCountry.getId() == null) {
            countryHome.setInstance(selectedCountry);
        } else {
            countryHome.setId(selectedCountry.getId());
        }
    }

    public String deleteCountry()
    {
        countryHome.clearInstance();
        countryHome.setId(selectedCountry.getId());
        countryHome.remove();
        initView();
        newCountry();
        messages.info(BundleKeys.COUNTRY_DELETE);
        return Constants.OUTCOME_SUCCESS;
    }

    public void initConversation()
    {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void initView()
    {
        initConversation();
        allCountries = countryHome.getAllCountries();
    }

    public boolean isUserForCountry()
    {
        return !(selectedCountry != null && selectedCountry.getId() != null && userHome.getCountUserForCountry(selectedCountry) == 0);
    }

    public String newCountry()
    {
        selectedCountry = new Country();
        setSelectedCountry(selectedCountry);
        return Constants.OUTCOME_SUCCESS;
    }

    public String save()
    {
        if (countryHome.isIdDefined()) {
            countryHome.update();
            messages.info(BundleKeys.COUNTRY_UPDATE);
        } else {
            countryHome.persist();
            messages.info(BundleKeys.COUNTRY_ADD);
        }
        initView();
        return Constants.OUTCOME_SUCCESS;
    }
}
