package pl.itcrowd.base.order.cart.view;

import org.jboss.seam.international.status.Messages;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CartView implements Serializable {

    private Cart cart;

    private Messages messages;

    @SuppressWarnings("UnusedDeclaration")
    public CartView()
    {
    }

    @Inject
    public CartView(Cart cart, Messages messages)
    {
        this.cart = cart;
        this.messages = messages;
    }

    public Cart getCart()
    {
        return cart;
    }

    public void addProduct(Product product)
    {
        try {
            cart.addProduct(product);
        } catch (DifferentSellerForOrderException e) {
            messages.error(BundleKeys.DIFFERENT_SELLER_FOR_ORDER);
            return;
        } catch (MaxProductQuantityReached mpqr) {
            messages.error(BundleKeys.MAX_QUANTITY_REACHED_ERROR);
            return;
        }
        messages.info(BundleKeys.CART_ITEM_ADDED);
    }

    public void addProduct(Product product, Long quantity)
    {
        if (quantity == null || (quantity == 0 || quantity < 0)) {
            messages.error(BundleKeys.WRONG_QUANTITY_VALUE);
            return;
        }
        if (quantity > product.getQuantity()) {
            messages.error(BundleKeys.CART_QUANTITY_TO_HIGH, product.getQuantity());
            return;
        }
        try {
            cart.addProduct(product, quantity);
        } catch (DifferentSellerForOrderException e) {
            messages.error(BundleKeys.DIFFERENT_SELLER_FOR_ORDER);
            return;
        } catch (MaxProductQuantityReached maxProductQuantityReached) {
            messages.error(BundleKeys.MAX_QUANTITY_REACHED_ERROR);
            return;
        }
        messages.info(BundleKeys.CART_ITEM_ADDED);
    }

    public void clearCart()
    {
        cart.clear();
        messages.info(BundleKeys.CART_CLEARED);
    }

    public void removeItem(OrderLineItem item)
    {
        cart.removeItem(item);
        messages.info(BundleKeys.CART_ITEM_REMOVED);
    }

    @SuppressWarnings("UnusedParameters")
    public void updateItems(AjaxBehaviorEvent event)
    {
        cart.recalculatePrices();
    }
}
