package pl.itcrowd.base.domain;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER")
public class Order implements Serializable, Identifiable<Long> {

//    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "ORDER_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ORDER_ID_SEQUENCE", sequenceName = "ORDER_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TS", nullable = false)
    private Date createdTs;

    @ManyToOne(optional = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ForeignKey(name = "FK___ORDER___BUYER")
    @JoinColumn(name = "BUYER_ID", nullable = true)
    private User buyer;

    @NotNull
    @Column(name = "BUYER_CONTACT_INFO", nullable = false, length = 255)
    private ContactInfo buyerContactInfo;

    @ManyToOne(optional = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ForeignKey(name = "FK___ORDER___SELLER")
    @JoinColumn(name = "SELLER_ID", nullable = true)
    private User seller;

    @NotNull
    private ContactInfo sellerContactInfo;

    @NotNull
    @Column(name = "SHIPPING_PRICE", nullable = false)
    private BigDecimal shippingPrice;

    @NotNull
    @Column(name = "TOTAL_PRICE", nullable = false)
    private BigDecimal totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private OrderStatusEnum status;

    @NotNull
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_FK_ID")
    @ForeignKey(name = "FK___ORDER___ORDER_LINE___ITEM")
    private List<OrderLineItem> lineItems;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "PAYMENT_TS", nullable = true)
    private Date paymentDate;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getCreatedTs()
    {
        return createdTs;
    }

    public void setCreatedTs(Date createdTs)
    {
        this.createdTs = createdTs;
    }

    public User getBuyer()
    {
        return buyer;
    }

    public void setBuyer(User buyer)
    {
        this.buyer = buyer;
    }

    public User getSeller()
    {
        return seller;
    }

    public void setSeller(User seller)
    {
        this.seller = seller;
    }

    public BigDecimal getShippingPrice()
    {
        return shippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice)
    {
        this.shippingPrice = shippingPrice;
    }

    public BigDecimal getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public OrderStatusEnum getStatus()
    {
        return status;
    }

    public void setStatus(OrderStatusEnum status)
    {
        this.status = status;
    }

    public List<OrderLineItem> getLineItems()
    {
        return lineItems;
    }

    public void setLineItems(List<OrderLineItem> lineItems)
    {
        this.lineItems = lineItems;
    }

    public Date getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public ContactInfo getBuyerContactInfo()
    {
        return buyerContactInfo;
    }

    public void setBuyerContactInfo(ContactInfo buyerContactInfo)
    {
        this.buyerContactInfo = buyerContactInfo;
    }

    public ContactInfo getSellerContactInfo()
    {
        return sellerContactInfo;
    }

    public void setSellerContactInfo(ContactInfo sellerContactInfo)
    {
        this.sellerContactInfo = sellerContactInfo;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }

        Order order = (Order) o;

        return !(this.getId() != null ? !this.getId().equals(order.getId()) : order.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return getId() != null ? getId().hashCode() : 0;
    }
}

