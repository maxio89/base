package pl.itcrowd.base.product.tag.business;

import pl.itcrowd.base.domain.Tag;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.seam3.persistence.EntityRemoved;

import javax.enterprise.util.AnnotationLiteral;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagHome extends EntityHome<Tag> {

    public List<Tag> getExistingTags(Set<String> splitTags)
    {
        //noinspection unchecked
        return getEntityManager().createQuery("select t from Tag t where t.name in (:tags)").setParameter("tags", splitTags).getResultList();
    }

    public List<Tag> getTopTags(int maxResults)
    {
        //noinspection unchecked
        return getEntityManager().createQuery("select t from Tag t order by t.popularity desc").setMaxResults(maxResults).getResultList();
    }

    @Override
    public int remove(Collection<Tag> elements)
    {
        if (elements.isEmpty()) {
            return 0;
        }
        final Set<Long> ids = new HashSet<Long>();
        for (Tag message : elements) {
            ids.add(message.getId());
        }
        ids.remove(null);

        final int count = getEntityManager().createQuery("delete Tag where id in (:ids)").setParameter("ids", ids).executeUpdate();
        for (Tag element : elements) {
            beanManager.fireEvent(element, new AnnotationLiteral<EntityRemoved>() {
            });
        }
        return count;
    }
}
