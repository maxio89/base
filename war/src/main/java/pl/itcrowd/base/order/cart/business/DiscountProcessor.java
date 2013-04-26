package pl.itcrowd.base.order.cart.business;

import org.joda.time.LocalDate;
import pl.itcrowd.base.domain.Discount;
import pl.itcrowd.base.domain.Product;
import pl.itcrowd.base.domain.QuantityDiscount;
import pl.itcrowd.base.domain.SpecialPriceDiscount;
import pl.itcrowd.base.product.business.ProductHome;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class DiscountProcessor implements Serializable {

    private static final int DIVISOR = 100;

    private static final int DIVISOR_SCALE = 12;

    private ProductHome productHome;

    @SuppressWarnings("UnusedDeclaration")
    public DiscountProcessor()
    {
    }

    @Inject
    public DiscountProcessor(ProductHome productHome)
    {
        this.productHome = productHome;
    }

    /**
     * Method return list of actual quantity discounts
     *
     * @param product for which we checking discounts
     *
     * @return list of quantity discounts
     */
    public List<QuantityDiscount> getActualQuantityDiscounts(Product product)
    {
        List<QuantityDiscount> quantityDiscounts = new ArrayList<QuantityDiscount>();

        for (Discount discount : product.getDiscounts()) {
            if (discount instanceof QuantityDiscount && validateDates(discount)) {
                quantityDiscounts.add((QuantityDiscount) discount);
            }
        }
        return quantityDiscounts;
    }

    /**
     * Method return the best price for product after special price discounts
     *
     * @param product for which we checking discounts
     *
     * @return best price
     */
    public BigDecimal getBestPriceAfterSpecialPriceDiscounts(Product product)
    {
        BigDecimal minimalPrice = null;

        for (Discount discount : product.getDiscounts()) {
            if (discount instanceof SpecialPriceDiscount) {
                BigDecimal discountPercentage = discount.getPercentage();
            }
        }
        return minimalPrice;
    }

    /**
     * Method returns the lowest price for actually active discounts for desired quantity
     *
     * @param product         product
     * @param desiredQuantity desired quantity
     *
     * @return price from product
     */
    public BigDecimal getUnitPriceAfterDiscounts(Product product, Long desiredQuantity)
    {
        productHome.setId(product.getId());
        productHome.getInstance();
        List<Discount> discounts = productHome.getProductDiscounts();

        //normal price for start
        BigDecimal minimalPrice = product.getPrice();

        for (Discount discount : discounts) {
            if (validateDates(discount)) {
                if (discount instanceof SpecialPriceDiscount) {
                } else if (discount instanceof QuantityDiscount) {
                    BigDecimal actual = product.getPrice()
                        .subtract(discount.getPercentage().multiply(product.getPrice()).divide(new BigDecimal(DIVISOR), DIVISOR_SCALE, RoundingMode.HALF_UP));
                    Long quantity = ((QuantityDiscount) discount).getQuantity();

                    long quantityResult = desiredQuantity.compareTo(quantity);

                    //if this discount is better than minimal
                    if ((quantityResult == 0 || quantityResult == 1) && actual.compareTo(minimalPrice) == -1) {
                        minimalPrice = actual;
                        minimalPrice = minimalPrice.setScale(2, RoundingMode.HALF_UP);
                    }
                }
            }
        }

        return minimalPrice;
    }

    /**
     * @param discount which dates are validated
     *
     * @return true  if start and end dates are chronological and if end date is before or equal current date
     */
    public boolean validateDates(Discount discount)
    {
        LocalDate startDateJ = new LocalDate(discount.getStartDate());
        LocalDate endDateJ = new LocalDate(discount.getEndDate());
        LocalDate currentDateJ = new LocalDate();
        return (startDateJ.isBefore(endDateJ) || startDateJ.isEqual(endDateJ)) && (endDateJ.isAfter(currentDateJ) || endDateJ.isEqual(currentDateJ));
    }
}
