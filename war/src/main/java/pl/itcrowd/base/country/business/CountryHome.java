package pl.itcrowd.base.country.business;

import pl.itcrowd.base.domain.Country;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.seam3.persistence.EntityRemoved;

import javax.enterprise.util.AnnotationLiteral;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CountryHome extends EntityHome<Country> {

    public List<Country> getAllCountries()
    {
        //noinspection unchecked
        return getEntityManager().createQuery("select p from Country p").getResultList();
    }

    public List<Country> checkDuplicate()
    {
        //noinspection unchecked
        return getEntityManager().createQuery("select p from Country p where p.name=:name").setParameter("name", getInstance().getName()).getResultList();
    }

    @Override
    public int remove(Collection<Country> elements)
    {
        if (elements.isEmpty()) {
            return 0;
        }
        final Set<Long> ids = new HashSet<Long>();
        for (Country message : elements) {
            ids.add(message.getId());
        }
        ids.remove(null);

        final int count = getEntityManager().createQuery("delete Country where id in (:ids)").setParameter("ids", ids).executeUpdate();
        for (Country element : elements) {
            beanManager.fireEvent(element, new AnnotationLiteral<EntityRemoved>() {
            });
        }
        return count;
    }

    /*
    * update from entityHome not behaving correctly*/
    @Override
    public boolean update()
    {
        int i = getEntityManager().createQuery("update Country set name=:name where id=:id")
            .setParameter("id", getId())
            .setParameter("name", getInstance().getName())
            .executeUpdate();
        getEntityManager().flush();
        clearInstance();
        return i > 0;
    }
}
