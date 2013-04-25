package pl.itcrowd.base.language;

import pl.itcrowd.base.domain.Language;
import pl.itcrowd.base.framework.business.EntityQuery;

import java.io.Serializable;

public class LanguageList extends EntityQuery<Language> implements Serializable {
// --------------------------- CONSTRUCTORS ---------------------------

    public LanguageList()
    {
        setEjbql("select l from Language l");
    }
}
