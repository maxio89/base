package pl.itcrowd.base.product.category.business;

import pl.itcrowd.base.domain.ProductCategory;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.seam3.persistence.EntityRemoved;

import javax.enterprise.util.AnnotationLiteral;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductCategoryHome extends EntityHome<ProductCategory> {

    public List<ProductCategory> getAllCategories()
    {
        //noinspection unchecked
        return getEntityManager().createQuery("select p from ProductCategory p").getResultList();
    }

    public List<ProductCategory> checkDuplicate()
    {
        //noinspection unchecked
        return getEntityManager().createQuery("select p from ProductCategory p where p.name=:name").setParameter("name", getInstance().getName()).getResultList();
    }

    @Override
    public int remove(Collection<ProductCategory> elements)
    {
        if (elements.isEmpty()) {
            return 0;
        }
        final Set<Long> ids = new HashSet<Long>();
        for (ProductCategory message : elements) {
            ids.add(message.getId());
        }
        ids.remove(null);

        final int count = getEntityManager().createQuery("delete ProductCategory where id in (:ids)").setParameter("ids", ids).executeUpdate();
        for (ProductCategory element : elements) {
            beanManager.fireEvent(element, new AnnotationLiteral<EntityRemoved>() {
            });
        }
        return count;
    }

//    /*
//    * remove from entityHome not behaving correctly*/
//    @Override
//    public boolean remove()
//    {
//        int i = getEntityManager().createQuery("delete ProductCategory where id in (:ids)").setParameter("ids", getId()).executeUpdate();
//        getEntityManager().flush();
//        clearInstance();
//        return i > 0;
//    }
}
