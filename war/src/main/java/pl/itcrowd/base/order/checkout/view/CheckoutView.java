package pl.itcrowd.base.order.checkout.view;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.country.business.CountryList;
import pl.itcrowd.base.domain.Country;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.order.business.OrderHome;
import pl.itcrowd.base.order.cart.business.Cart;

import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class CheckoutView implements Serializable {

    private Cart cart;

    private List<Country> countries;

    private CountryList countryList;

    private CreditBank creditBank;

    private User currentUser;

    private Logger logger;

    private MessageHome messageHome;

    private Messages messages;

    private Event<Order> orderEvent;

    private OrderHome orderHome;

    private ProductQuantityService productQuantityService;

    private Country selectedCountry;

    private CountryRegion selectedCountryRegion;

    private BigDecimal suggestedPrice;

    private String suggestedPriceMessage;

    private boolean useExistingAddress = true;

    private UserHome userHome;

    private boolean userWantNegotiation = false;

    @SuppressWarnings("UnusedDeclaration")
    public CheckoutView()
    {
    }

    @SuppressWarnings({"UnusedDeclaration", "CdiInjectionPointsInspection"})
    @Inject
    public CheckoutView(OrderHome orderHome, Messages messages, Cart cart, @CurrentUser User currentUser, UserHome userHome, CountryList countryList, MessageHome messageHome,
                        CreditBank creditBank, @OrderStatusChanged Event<Order> orderEvent, ProductQuantityService pqs, Logger logger)
    {
        this.orderHome = orderHome;
        this.messages = messages;
        this.cart = cart;
        this.currentUser = currentUser;
        this.userHome = userHome;
        this.messageHome = messageHome;
        this.countryList = countryList;
        this.creditBank = creditBank;
        this.orderEvent = orderEvent;
        this.productQuantityService = pqs;
        this.logger = logger;
    }

    public List<Country> getCountries()
    {
        return countries;
    }

    public Order getOrder()
    {
        return orderHome.getInstance();
    }

    public Country getSelectedCountry()
    {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry)
    {
        this.selectedCountry = selectedCountry;
    }

    public CountryRegion getSelectedCountryRegion()
    {
        return selectedCountryRegion;
    }

    public void setSelectedCountryRegion(CountryRegion selectedCountryRegion)
    {
        this.selectedCountryRegion = selectedCountryRegion;
    }

    public BigDecimal getSuggestedPrice()
    {
        return suggestedPrice;
    }

    public void setSuggestedPrice(BigDecimal suggestedPrice)
    {
        this.suggestedPrice = suggestedPrice;
    }

    public String getSuggestedPriceMessage()
    {
        return suggestedPriceMessage;
    }

    public void setSuggestedPriceMessage(String suggestedPriceMessage)
    {
        this.suggestedPriceMessage = suggestedPriceMessage;
    }

    public void initView()
    {
        if (currentUser == null || cart.getSeller() == null || cart.cartSize() == 0) {
            throw new AuthorizationException("Unlogged user or wrong cart state");
        }
        this.countries = countryList.getResultList();

        prepareOrder(orderHome.getInstance());
        suggestedPrice = orderHome.getInstance().getShippingPrice();
    }

    /**
     * Method invoked in s:viewAction, validate cart
     *
     * @return status, handled in faces-config rule.
     */
    public boolean isCartEmpty()
    {
        return currentUser == null || cart.cartSize() == 0;
    }

    public boolean isUseExistingAddress()
    {
        return useExistingAddress;
    }

    public void setUseExistingAddress(boolean useExistingAddress)
    {
        //if set
        if (useExistingAddress) {
            assignBuyerAddress(orderHome.getInstance());
        }
        this.useExistingAddress = useExistingAddress;
    }

    public boolean isUserWantNegotiation()
    {
        return userWantNegotiation;
    }

    public void setUserWantNegotiation(boolean userWantNegotiation)
    {
        this.userWantNegotiation = userWantNegotiation;
    }

    /**
     * Method binded with "Submit order" button.
     * Method saves order.
     *
     * @return persist status
     */
    public String saveOrder()
    {
        Order order = orderHome.getInstance();
        if (!isUseExistingAddress()) {
            order.setBuyerCountry(selectedCountry.getName());
            order.setBuyerRegion(selectedCountryRegion.getName());
        }

        if (!isCartEmpty()) {
            order.setLineItems(cart.getItems());
            order.setTotalPrice(cart.getCartTotalValue());
            order.setShippingPrice(cart.getTotalShippingValue());
        } else {
            messages.error(BundleKeys.EMPTY_CART_ERROR);
            return Constants.OUTCOME_FAILURE;
        }

        if (order.getBuyer() != null && order.getSeller() != null && order.getBuyer().equals(order.getSeller())) {
            messages.error(BundleKeys.BUY_FROM_YOURSELF);
            cart.clear();
            return Constants.OUTCOME_FAILURE;
        }

        if (!productQuantityService.checkProductsQuantity(order)) {
            messages.error(BundleKeys.PRODUCT_QUANTITY_ISNT_WALID);
            return Constants.OUTCOME_FAILURE;
        }

        if (!creditBank.userCanBuyService(order.getTotalPrice())) {
            messages.error(BundleKeys.CHECKOUT_NOT_ENOUGH_CREDITS);
            return Constants.OUTCOME_FAILURE;
        }

        if (userWantNegotiation) {
            if (suggestedPrice == null || suggestedPriceMessage == null) {
                return Constants.OUTCOME_FAILURE;
            }
            //user want negotiation so status for waiting is set
            order.setStatus(OrderStatusEnum.WAITING_FOR_APPROVAL);
        } else {
            order.setStatus(OrderStatusEnum.SUBMITTED);
            if (!creditBank.buyOrder(order)) {
                messages.error(BundleKeys.ORDER_CHARGING_ERROR);
                return Constants.OUTCOME_FAILURE;
            }
        }

        boolean persistResult = orderHome.persist();

        //send message to seller
        if (persistResult && userWantNegotiation) {
            Message message = messageHome.getInstance();
            message.setRecipient(orderHome.getInstance().getSeller());
            message.setSender(orderHome.getInstance().getBuyer());
            message.setContent(suggestedPriceMessage);
            messageHome.persistAndSendProposal(suggestedPrice, (Long) orderHome.getId());
        }

        if (persistResult) {
            cart.clear();
            if (!productQuantityService.updateProductsQuantity(order)) {
                logger.error("Product quantity update wasn't successful after order persist.");
            }

            orderEvent.fire(orderHome.getInstance());
            return Constants.OUTCOME_SUCCESS;
        } else {
            return Constants.OUTCOME_FAILURE;
        }
    }

    private void assignBuyerAddress(Order order)
    {
        order.setBuyer(userHome.getInstance());

        //set buyer country and region
        this.selectedCountryRegion = order.getBuyer().getCountryRegion();
        this.selectedCountry = userHome.getInstance().getCountryRegion().getCountry();
    }

    private void prepareOrder(Order order)
    {
        userHome.setId(currentUser.getId());
        order.setCreatedTs(new Date());
        order.setPaymentDate(null);

        //save seller details
        order.setSeller(cart.getSeller());

        //save buyer details
        assignBuyerAddress(order);

        //user in user home is BUYER now
    }

    public boolean isInsufficientFunds()
    {
        return currentUser.getCredits().compareTo(cart.getCartTotalValue()) == Constants.LESS_THAN;
    }

    public Long getBuyDifferenceValue()
    {
        BigDecimal diff = cart.getCartTotalValue().subtract(currentUser.getCredits());
        if (diff.compareTo(BigDecimal.ZERO) == Constants.LESS_THAN) {
            return 0L;
        } else {
            return diff.setScale(0, RoundingMode.CEILING).longValue();
        }
    }
}
