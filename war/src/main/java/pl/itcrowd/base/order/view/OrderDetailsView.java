package pl.itcrowd.base.order.view;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.Constants;
import pl.itcrowd.base.domain.Order;
import pl.itcrowd.base.domain.OrderStatusEnum;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.order.business.OrderHome;
import pl.itcrowd.base.order.business.OrderStatusChanged;
import pl.itcrowd.base.user.CurrentUser;

import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.math.BigDecimal;

@Named
@ViewScoped
public class OrderDetailsView implements Serializable {

    private User currentUser;

    private Logger logger;

    private Messages messages;

    private BigDecimal newShippingPrice;

    private OrderStatusEnum nextAvailableStatus;

    private OrderHome orderHome;

    private Event<Order> orderStatusChangeEvent;

    @SuppressWarnings("UnusedDeclaration")
    public OrderDetailsView()
    {
    }

    @SuppressWarnings({"CdiInjectionPointsInspection", "UnusedDeclaration"})
    @Inject
    public OrderDetailsView(OrderHome orderHome, Messages messages, @CurrentUser User currentUser, @OrderStatusChanged Event<Order> orderStatusChangeEvent, Logger logger)
    {
        this.messages = messages;
        this.currentUser = currentUser;
        this.orderHome = orderHome;
        this.orderStatusChangeEvent = orderStatusChangeEvent;
        this.logger = logger;
    }

    public OrderStatusEnum getNextAvailableStatus()
    {
        return nextAvailableStatus;
    }

    public Order getOrder()
    {
        return orderHome.getInstance();
    }

    public Long getOrderId()
    {
        return (Long) orderHome.getId();
    }

    public void setOrderId(Long id)
    {
        orderHome.setId(id);
    }

    public BigDecimal getShippingPrice()
    {
        return newShippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice)
    {
        this.newShippingPrice = shippingPrice;
    }

    public String changeOrderStatus()
    {
//        Order order = orderHome.getInstance();
//        OrderStatusEnum nextStatus = chooseNextAvailableStatus();
//
//        if (nextStatus == OrderStatusEnum.APPROVED) {
//            if (newShippingPrice != null && (newShippingPrice.compareTo(order.getShippingPrice()) == Constants.EQUAL
//                || newShippingPrice.compareTo(order.getShippingPrice()) == Constants.LESS_THAN)) {
//                BigDecimal old = order.getShippingPrice();
//                BigDecimal difference = old.subtract(newShippingPrice);
//                order.setShippingPrice(newShippingPrice);
//                order.setTotalPrice(order.getTotalPrice().subtract(difference));
//            } else {
//                messages.error(BundleKeys.SHIPPING_PRICE_PROPOSAL_WRONG_VALUE);
//                return Constants.OUTCOME_STAY;
//            }
//        }
//
//        order.setStatus(nextStatus);
//        if (OrderStatusEnum.SUBMITTED.equals(nextStatus) && order.getBuyer().equals(currentUser)) {
//            if (creditBank.buyOrder(order)) {
//                messages.info(BundleKeys.ORDER_CHARGED);
//            } else {
//                messages.info(BundleKeys.ORDER_CHARGING_ERROR);
//                return Constants.OUTCOME_FAILURE;
//            }
//        }
//
//        boolean result;
//        try {
//            result = orderHome.update();
//            messages.info(BundleKeys.ORDER_STATUS_CHANGED);
//            orderStatusChangeEvent.fire(orderHome.getInstance());
//        } catch (Exception e) {
//            result = false;
//            logger.error("Order home update error", e);
//        }
//        if (!result) {
//            messages.error(BundleKeys.ORDER_STATUS_CHANGED_ERROR);
//            return Constants.OUTCOME_FAILURE;
//        }
//
//        this.nextAvailableStatus = chooseNextAvailableStatus();
        return Constants.OUTCOME_SUCCESS;
    }

    public void initView()
    {
        if (currentUser == null) {
            throw new AuthorizationException("Current user is null");
        }
        orderHome.getInstance();
        if (!orderHome.isIdDefined()) {
            throw new EntityNotFoundException();
        }

        //if current user isn't seller or buyer, he can't see order
        if (!(orderHome.getInstance().getBuyer().equals(currentUser) || orderHome.getInstance().getSeller().equals(currentUser))) {
            throw new AuthorizationException("User can't see this order");
        }
        this.nextAvailableStatus = chooseNextAvailableStatus();
    }

    public boolean isSeller()
    {
        return orderHome.getInstance().getSeller().equals(currentUser);
    }

    private OrderStatusEnum chooseNextAvailableStatus()
    {
//        Order order = orderHome.getInstance();
//        OrderStatusEnum orderStatus = order.getStatus();
//
//        if (OrderStatusEnum.WAITING_FOR_APPROVAL.equals(orderStatus)) {
//            if (isSeller()) {
//                return OrderStatusEnum.APPROVED;
//            } else {
//                return null;
//            }
//        }
//        if (OrderStatusEnum.APPROVED.equals(orderStatus)) {
//            if (isSeller()) {
//                return null;
//            } else {
//                return OrderStatusEnum.SUBMITTED;
//            }
//        }
//        if (OrderStatusEnum.SUBMITTED.equals(orderStatus)) {
//            if (isSeller()) {
//                return OrderStatusEnum.IN_REALIZATION;
//            } else {
//                return null;
//            }
//        }
//        if (OrderStatusEnum.IN_REALIZATION.equals(orderStatus)) {
//            if (isSeller()) {
//                return OrderStatusEnum.SENT;
//            } else {
//                return null;
//            }
//        }
//        if (OrderStatusEnum.SENT.equals(orderStatus)) {
//            if (isSeller()) {
//                return null;
//            } else {
//                return OrderStatusEnum.REALIZED;
//            }
//        }
        return null;
    }
}
