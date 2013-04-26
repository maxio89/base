package pl.itcrowd.base.order.cart.business;

import pl.itcrowd.base.domain.Product;

import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.math.BigDecimal;

@RequestScoped
public class ShippingProcessor implements Serializable {

    /**
     * Method returns shipping cost for product for selected quantity.
     *
     * @param product  product
     * @param quantity quantity
     *
     * @return shipping price
     */
    public BigDecimal getShippingPrice(Product product, Long quantity)
    {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity is null");
        }

        if (product == null) {
            throw new IllegalArgumentException("Product is null");
        }

        BigDecimal shipping = product.getShippingCost();
        Long maxQuantity = product.getMaxQuantityInParcel();

        if (maxQuantity <= 1) {
            return shipping.multiply(new BigDecimal(quantity));
        }

        if (quantity < maxQuantity) {
            return shipping;
        } else {
            double packages = Math.ceil((quantity * 1.0) / maxQuantity);
            return shipping.multiply(new BigDecimal(packages));
        }
    }
}
