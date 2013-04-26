package pl.itcrowd.base.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "QUANTITY_DISCOUNT")
public class QuantityDiscount extends Discount {

    //    ------------ FIELDS --------------------------

    @NotNull
    @Column(nullable = false, name = "QUANTITY")
    @Min(2)
    private Long quantity;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public Long getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }
}
