package pl.itcrowd.base.country.business;

import pl.itcrowd.base.domain.Country;
import pl.itcrowd.base.framework.business.EntityQuery;

import java.io.Serializable;

public class CountryList extends EntityQuery<Country> implements Serializable {

    public CountryList()
    {
        setEjbql("select distinct c from Country c");
    }
}
