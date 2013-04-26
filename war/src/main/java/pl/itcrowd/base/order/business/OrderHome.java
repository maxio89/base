package pl.itcrowd.base.order.business;

import pl.itcrowd.base.domain.Order;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.seam3.persistence.EntityRemoved;

import javax.enterprise.util.AnnotationLiteral;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OrderHome extends EntityHome<Order> {

    @Override
    public int remove(Collection<Order> elements)
    {
        if (elements.isEmpty()) {
            return 0;
        }
        final Set<Long> ids = new HashSet<Long>();
        for (Order order : elements) {
            ids.add(order.getId());
        }
        ids.remove(null);
        final int count = getEntityManager().createQuery("delete Order where id in (:ids)").setParameter("ids", ids).executeUpdate();
        for (Order element : elements) {
            beanManager.fireEvent(element, new AnnotationLiteral<EntityRemoved>() {
            });
        }
        return count;
    }
}
