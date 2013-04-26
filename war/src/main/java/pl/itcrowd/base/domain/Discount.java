package pl.itcrowd.base.domain;

import org.hibernate.annotations.ForeignKey;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Discount implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "DISCOUNT_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "DISCOUNT_ID_SEQUENCE", sequenceName = "DISCOUNT_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "START_DATE_TS", nullable = false)
    private Date startDate;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "END_DATE_TS", nullable = false)
    private Date endDate;

    @NotNull
    @Column(name = "PERCENTAGE", nullable = false)
    @Min(1)
    private BigDecimal percentage;

    @NotNull
    @ManyToOne(optional = false)
    @ForeignKey(name = "FK___DISCOUNT___PRODUCT")
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public BigDecimal getPercentage()
    {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage)
    {
        this.percentage = percentage;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discount)) {
            return false;
        }

        Discount discount = (Discount) o;

        return !(this.getId() != null ? !this.getId().equals(discount.getId()) : discount.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
