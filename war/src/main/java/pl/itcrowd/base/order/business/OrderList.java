package pl.itcrowd.base.order.business;

import pl.itcrowd.base.domain.Order;
import pl.itcrowd.base.domain.OrderStatusEnum;
import pl.itcrowd.base.framework.business.EntityQuery;
import pl.itcrowd.seam3.persistence.conditions.AbstractCondition;
import pl.itcrowd.seam3.persistence.conditions.AndCondition;
import pl.itcrowd.seam3.persistence.conditions.DynamicParameter;
import pl.itcrowd.seam3.persistence.conditions.FreeCondition;
import pl.itcrowd.seam3.persistence.conditions.OrCondition;

import java.io.Serializable;
import java.util.Arrays;

public class OrderList extends EntityQuery<Order> implements Serializable {

    private Criteria searchCriteria = new Criteria();

    public OrderList()
    {
        setEjbql("select o from Order o");
        final FreeCondition customerIdCondition = new FreeCondition("o.seller.id=", searchCriteria.sellerIdBridge);
        final FreeCondition buyerIdCondition = new FreeCondition("o.buyer.id=", searchCriteria.buyerIdBridge);
        final FreeCondition statusCondition = new FreeCondition("o.status=", searchCriteria.statusBridge);
        final AbstractCondition anyNameCondition = new OrCondition(customerIdCondition, buyerIdCondition);
        final AndCondition and = new AndCondition(anyNameCondition, statusCondition);
        setConditions(Arrays.asList(and));

        setOrderColumn("o.createdTs");
        setOrderDirection(DIR_DESC);
    }

    public Criteria getSearchCriteria()
    {
        return searchCriteria;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static class Criteria implements Serializable {

        private Long buyerId;

        private DynamicParameter<Long> buyerIdBridge = new DynamicParameter<Long>() {
            @Override
            public Long getValue()
            {
                return buyerId;
            }
        };

        private Long sellerId;

        private DynamicParameter<Long> sellerIdBridge = new DynamicParameter<Long>() {
            @Override
            public Long getValue()
            {
                return sellerId;
            }
        };

        private OrderStatusEnum status;

        private DynamicParameter<OrderStatusEnum> statusBridge = new DynamicParameter<OrderStatusEnum>() {
            @Override
            public OrderStatusEnum getValue()
            {
                return status;
            }
        };

        public Long getBuyerId()
        {
            return buyerId;
        }

        public void setBuyerId(Long buyerId)
        {
            this.buyerId = buyerId;
        }

        public Long getSellerId()
        {
            return sellerId;
        }

        public void setSellerId(Long sellerId)
        {
            this.sellerId = sellerId;
        }

        public OrderStatusEnum getStatus()
        {
            return status;
        }

        public void setStatus(OrderStatusEnum status)
        {
            this.status = status;
        }
    }
}
