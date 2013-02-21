package com.project.language;

import com.project.language.Language;
import pl.com.it_crowd.seam.framework.EntityHome;

public class LanguageHome extends EntityHome<Language> {
// -------------------------- OTHER METHODS --------------------------

    /**
     * Gets language with given ISO 6391 code
     *
     * @param code ISO code of language
     *
     * @return matching language
     *
     * @throws javax.persistence.NoResultException
     *          if no language with such code is found
     */
    public Language getLanguageByISO6391(String code)
    {
        return (Language) getEntityManager().createQuery("select l from Language l where l.iso6391=:iso").setParameter("iso", code).getSingleResult();
    }
}
