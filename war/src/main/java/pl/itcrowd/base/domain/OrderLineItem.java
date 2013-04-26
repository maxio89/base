package pl.itcrowd.base.domain;

import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_LINE_ITEM")
public class OrderLineItem implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "ORDER_LINE_ITEM_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ORDER_LINE_ITEM_ID_SEQUENCE", sequenceName = "ORDER_LINE_ITEM_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @NotNull
    @Column(name = "PRICE_UNIT", nullable = false)
    private BigDecimal pricePerUnit;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @NotNull
    @Column(name = "SHIPPING_COST", nullable = false)
    private BigDecimal shippingCost;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private BigDecimal totalPrice;

//    @NotNull
//    @ManyToOne
//    @JoinColumn(name = "ORDER_ID", nullable = false)
//    private Order order;

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getPricePerUnit()
    {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit)
    {
        this.pricePerUnit = pricePerUnit;
    }

    public Long getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getShippingCost()
    {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost)
    {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLineItem)) {
            return false;
        }

        OrderLineItem orderLineItem = (OrderLineItem) o;

        return !(this.getId() != null ? !this.getId().equals(orderLineItem.getId()) : orderLineItem.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return getId() != null ? getId().hashCode() : 0;
    }
}
