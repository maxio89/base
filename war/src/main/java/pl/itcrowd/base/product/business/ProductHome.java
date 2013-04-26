package pl.itcrowd.base.product.business;

import pl.itcrowd.base.domain.Discount;
import pl.itcrowd.base.domain.Product;
import pl.itcrowd.base.domain.ProductCategory;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.seam3.persistence.EntityRemoved;

import javax.enterprise.util.AnnotationLiteral;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductHome extends EntityHome<Product> {

    /**
     * Method returns last added products.
     *
     * @param maxResult limit
     *
     * @return list of product
     */
    public List<Product> getLastProducts(int maxResult)
    {
        //sorting this by ID, because we haven't appropriate field.
        //noinspection unchecked
        return getEntityManager().createQuery("SELECT p FROM Product p ORDER BY id DESC").setMaxResults(maxResult).getResultList();
    }

    /**
     * returns discounts for product which are applicable now
     *
     * @return discounts list
     */
    public List<Discount> getProductDiscounts()
    {
        return getEntityManager().createQuery("SELECT d from Discount d where d.product.id=:id").setParameter("id", getId()).getResultList();
    }

    public Long getProductsForCategory(ProductCategory category)
    {
        return (Long) getEntityManager().createQuery("select count(p) from Product p join p.category c where c=:category").setParameter("category", category).getSingleResult();
    }

    @Override
    public int remove(Collection<Product> elements)
    {
        if (elements.isEmpty()) {
            return 0;
        }
        final Set<Long> ids = new HashSet<Long>();
        for (Product message : elements) {
            ids.add(message.getId());
        }
        ids.remove(null);

        final int count = getEntityManager().createQuery("delete Product where id in (:ids)").setParameter("ids", ids).executeUpdate();
        for (Product element : elements) {
            beanManager.fireEvent(element, new AnnotationLiteral<EntityRemoved>() {
            });
        }
        return count;
    }
}
