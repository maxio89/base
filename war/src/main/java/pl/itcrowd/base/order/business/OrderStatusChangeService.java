package pl.itcrowd.base.order.business;

import pl.itcrowd.base.domain.Order;
import pl.itcrowd.base.mail.ProjectMailman;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public class OrderStatusChangeService {

    private ProjectMailman projectMailman;

    public OrderStatusChangeService()
    {
    }

    @Inject
    public OrderStatusChangeService(ProjectMailman projectMailman)
    {
        this.projectMailman = projectMailman;
    }

    public void onOrderStatusChange(@Observes @OrderStatusChanged Order order)
    {
        if (order.getStatus() != null) {
            projectMailman.sendOrderStatusChangedNotification(order.getBuyer(), order);
        }
    }
}
