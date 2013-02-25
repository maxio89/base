package com.project.user.business;

import com.project.domain.User;
import com.project.framework.business.EntityQuery;
import pl.com.it_crowd.seam.framework.conditions.AbstractCondition;
import pl.com.it_crowd.seam.framework.conditions.DynamicParameter;
import pl.com.it_crowd.seam.framework.conditions.FreeCondition;
import pl.com.it_crowd.seam.framework.conditions.OrCondition;

import java.io.Serializable;
import java.util.Arrays;

/**
 * User: Rafal Gielczowski
 * Date: 2/25/13 Time: 10:09 AM
 */
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
