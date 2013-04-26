package pl.itcrowd.base.order.view;

import org.jboss.seam.international.status.Messages;

import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class OrderListView extends AbstractListView<Order> implements Serializable {

    private OrderList orderList;

    @SuppressWarnings("UnusedDeclaration")
    public OrderListView()
    {
    }

    @SuppressWarnings({"UnusedDeclaration", "CdiInjectionPointsInspection"})
    @Inject
    public OrderListView(OrderHome orderHome, OrderList orderList, @EntitySelected Event<Order> entitySelectedEvent, @CurrentUser User currentUser, Messages messages)
    {
        super(orderHome, orderList, entitySelectedEvent, messages);
        this.orderList = orderList;
        if (currentUser != null) {
            this.orderList.getSearchCriteria().setBuyerId(currentUser.getId());
            this.orderList.getSearchCriteria().setSellerId(currentUser.getId());
        }
    }

    public OrderList getOrderList()
    {
        return orderList;
    }
}
