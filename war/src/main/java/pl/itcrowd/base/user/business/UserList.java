package pl.itcrowd.base.user.business;

import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.framework.business.EntityQuery;
import pl.itcrowd.seam3.persistence.conditions.AbstractCondition;
import pl.itcrowd.seam3.persistence.conditions.DynamicParameter;
import pl.itcrowd.seam3.persistence.conditions.FreeCondition;
import pl.itcrowd.seam3.persistence.conditions.OrCondition;

import java.io.Serializable;
import java.util.Arrays;

public class UserList extends EntityQuery<User> implements Serializable {

    private Criteria searchCriteria = new Criteria();

    public UserList()
    {
        setEjbql("select distinct u from User u");

        //search criteria for user entity (by firstname, lastname or email)
        final FreeCondition firstnameCondition = new FreeCondition("lower(u.firstName) like lower(concat(", searchCriteria.nameBridge, ",'%'))");
        final FreeCondition lastnameCondition = new FreeCondition("lower(u.lastName) like lower(concat(", searchCriteria.nameBridge, ",'%'))");
        final FreeCondition emailCondition = new FreeCondition("lower(u.email) like lower(concat(", searchCriteria.nameBridge, ",'%'))");
        final AbstractCondition anyNameCondition = new OrCondition(firstnameCondition, lastnameCondition, emailCondition);
        setConditions(Arrays.asList(anyNameCondition));
    }

    public Criteria getSearchCriteria()
    {
        return searchCriteria;
    }

    public static class Criteria implements Serializable {
// ------------------------------ FIELDS ------------------------------

        private String name;

        private DynamicParameter<String> nameBridge = new DynamicParameter<String>() {
            @Override
            public String getValue()
            {
                return name;
            }
        };

// --------------------- GETTER / SETTER METHODS ---------------------

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }
}
