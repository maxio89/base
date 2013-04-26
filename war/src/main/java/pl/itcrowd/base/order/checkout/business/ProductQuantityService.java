package pl.itcrowd.base.order.checkout.business;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public class ProductQuantityService implements Serializable {

    private EntityManager entityManager;

    @SuppressWarnings("UnusedDeclaration")
    public ProductQuantityService()
    {
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public ProductQuantityService(Instance<EntityManager> emf)
    {
        this.entityManager = emf.get();
    }

    /**
     * Method checks availability of products associated with line items from order.
     *
     * @param order order
     *
     * @return true if all products has quantities equal or more than quantity requested in order
     */
    public boolean checkProductsQuantity(Order order)
    {
        if (order == null || order.getLineItems() == null || order.getLineItems().isEmpty()) {
            throw new IllegalArgumentException("Order or order items was null, or order items was empty");
        }

        //check product quantity
        List<OrderLineItem> orderItems = order.getLineItems();
        for (OrderLineItem item : orderItems) {
            if (item.getProduct() == null) {
                return false;
            } else {
                Long id = item.getProduct().getId();
                Long quantity = getProductQuantity(id);
                if (quantity == null || quantity < item.getQuantity()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method will update quantity of line items associated products.
     * Before update you should invoke checkProductQuantity(Order) to make sure if products can be updated properly
     *
     * @param order order
     *
     * @return true if all updates was successful
     */
    public boolean updateProductsQuantity(Order order)
    {
        if (order == null || order.getLineItems() == null || order.getLineItems().isEmpty()) {
            throw new IllegalArgumentException("Order or order items was null, or order items was empty");
        }
        List<OrderLineItem> orderItems = order.getLineItems();

        //update quantities
        for (OrderLineItem item : orderItems) {
            Long productId = item.getProduct().getId();
            updateProductQuantity(productId, item.getQuantity());
        }

        entityManager.flush();
        return true; //true for now
    }

    private Long getProductQuantity(Long id)
    {
        final Long quantity;
        try {
            quantity = (Long) entityManager.createQuery("select p.quantity from Product p where p.id=:id").setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

        return quantity;
    }

    private void updateProductQuantity(Long productId, Long quantity)
    {
        if (productId == null || quantity == null) {
            throw new IllegalArgumentException("productId or quantity was null");
        }

        //noinspection UnusedDeclaration
        entityManager.createQuery("update Product p set p.quantity=p.quantity-:qtty where p.id=:pId").setParameter("qtty", quantity).setParameter("pId", productId).executeUpdate();
    }
}
