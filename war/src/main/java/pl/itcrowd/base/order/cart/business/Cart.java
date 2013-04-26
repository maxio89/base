package pl.itcrowd.base.order.cart.business;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Named
@SessionScoped
public class Cart implements Serializable {

    private static final Long DEFAULT_QUANTITY = 1L;

    private DiscountProcessor discountProcessor;

    private List<OrderLineItem> items = new ArrayList<OrderLineItem>();

    private ProductHome productHome;

    private User seller;

    private ShippingProcessor shippingProcessor;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    private Long totalQuantity = 0L;

    private BigDecimal totalShippingPrice = BigDecimal.ZERO;

    @SuppressWarnings("UnusedDeclaration")
    public Cart()
    {
    }

    @Inject
    public Cart(DiscountProcessor discountProcessor, ProductHome productHome, ShippingProcessor shippingProcessor)
    {
        this.discountProcessor = discountProcessor;
        this.productHome = productHome;
        this.shippingProcessor = shippingProcessor;
    }

    public BigDecimal getCartTotalValue()
    {
        return totalPrice;
    }

    public List<OrderLineItem> getItems()
    {
        return items;
    }

    public User getSeller()
    {
        return seller;
    }

    public Long getTotalQuantity()
    {
        return totalQuantity;
    }

    public BigDecimal getTotalShippingValue()
    {
        return totalShippingPrice;
    }

    /**
     * Overloaded method addProduct, useful with single button "Buy/Add to cart".
     * Method add product with default quantity.
     *
     * @param product product
     *
     * @throws DifferentSellerForOrderException
     *
     */
    public void addProduct(Product product) throws DifferentSellerForOrderException, MaxProductQuantityReached
    {
        this.addProduct(product, DEFAULT_QUANTITY);
    }

    /**
     * This method will add method from argument if it not exists in cart,
     * if exists, it will increment quantity + quantity from argument.
     * Method throw exception from signature, when user want to add product to cart from
     * different seller than other products.
     *
     * @param product  product
     * @param quantity quantity
     *
     * @throws DifferentSellerForOrderException
     *
     */
    public void addProduct(Product product, Long quantity) throws DifferentSellerForOrderException, MaxProductQuantityReached
    {
        if (product == null || quantity == null) {
            throw new IllegalArgumentException("Product or Quantity from argument was null");
        }

        if (items.isEmpty()) {
            seller = productHome.getPsychicFromProduct(product);
        }

        if (!seller.equals(productHome.getPsychicFromProduct(product))) {
            throw new DifferentSellerForOrderException("User is already defined for this cart, and you have tried to add product from different seller");
        }


        OrderLineItem found = null;
        for (OrderLineItem item : items) {
            if (product.equals(item.getProduct())) {
                found = item;
                break;
            }
        }

        if (found != null) {
            if ((found.getQuantity() + quantity) > found.getProduct().getQuantity()) {
                throw new MaxProductQuantityReached("Max product quantity reached!");
            }
            found.setQuantity(found.getQuantity() + quantity);
        } else {
            addProductAsLineItem(product, quantity);
        }

        recalculatePrices();
    }

    /**
     * Method return total number of products types (regardless of quantity).
     * If you want total number of products, use totalQuantity()
     *
     * @return number of product types
     */
    public int cartSize()
    {
        return items.size();
    }

    /**
     * Method removes all items from cart
     */
    public void clear()
    {
        items = new ArrayList<OrderLineItem>();
        seller = null;
        totalPrice = BigDecimal.ZERO;
        totalShippingPrice = BigDecimal.ZERO;
        totalQuantity = 0L;
    }

    public void recalculatePrices()
    {
        totalPrice = BigDecimal.ZERO;
        totalShippingPrice = BigDecimal.ZERO;
        totalQuantity = 0L;

        for (OrderLineItem item : items) {
            item = updateItem(item);
            totalShippingPrice = totalShippingPrice.add(item.getShippingCost());
            totalPrice = totalPrice.add(item.getTotalPrice()).add(item.getShippingCost());
            totalQuantity = totalQuantity + item.getQuantity();
        }
    }

    public void removeItem(OrderLineItem item)
    {
        final Iterator<OrderLineItem> iterator = items.iterator();
        final Product product = item.getProduct();
        while (iterator.hasNext()) {
            if (iterator.next().getProduct().equals(product)) {
                iterator.remove();
            }
        }
        recalculatePrices();
    }

    private void addProductAsLineItem(Product product, Long quantity)
    {
        OrderLineItem newItem = new OrderLineItem();
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setName(product.getName());

        newItem = updateItem(newItem);
        this.items.add(newItem);
    }

    /**
     * Method will update object from arguments:
     *
     * @param orderLineItem order line item
     *
     * @return order line item
     */
    private OrderLineItem updateItem(OrderLineItem orderLineItem)
    {
        //update price per unit
        BigDecimal pricePerUnit = discountProcessor.getUnitPriceAfterDiscounts(orderLineItem.getProduct(), orderLineItem.getQuantity());
        orderLineItem.setPricePerUnit(pricePerUnit);

        //update shipping cost
        BigDecimal shippingCost = shippingProcessor.getShippingPrice(orderLineItem.getProduct(), orderLineItem.getQuantity());
        orderLineItem.setShippingCost(shippingCost);

        //update total price
        BigDecimal itemTotalPrice = pricePerUnit.multiply(new BigDecimal(orderLineItem.getQuantity()));
        orderLineItem.setTotalPrice(itemTotalPrice);

        return orderLineItem;
    }

    /**
     * Method returns summarized price for products without shipping
     *
     * @return total cost of items without shipping
     */
    public BigDecimal getTotalItemsPrice()
    {
        return getCartTotalValue().subtract(getTotalShippingValue());
    }
}
