package com.project.language.business;

import com.project.domain.Language;
import com.project.framework.business.EntityQuery;

import java.io.Serializable;

public class LanguageList extends EntityQuery<Language> implements Serializable {
// --------------------------- CONSTRUCTORS ---------------------------

    public LanguageList()
    {
        setEjbql("select l from Language l");
    }
}
