package pl.itcrowd.base.domain;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SHOP")
public class Shop implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "SHOP_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SHOP_ID_SEQUENCE", sequenceName = "SHOP_ID_SEQUENCE", allocationSize = 1, initialValue = 1)
    private Long id;

    @NotNull
    @Column(name = "OWNER")
    @OneToOne(optional = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ForeignKey(name = "FK___SHOP_OWNER___USER")
    @JoinColumn(name = "USER_ID", nullable = false)
    private User owner;

    @OneToMany
    @JoinColumn(name = "SHOP_ID")
    @ForeignKey(name = "FK___SHOP___PRODUCT")
    private List<Product> products;

    @OneToMany
    @JoinColumn(name = "SHOP_ID")
    @ForeignKey(name = "FK___SHOP___PRODUCT")
    private List<User> sellers;

    @NotNull
    private ContactInfo contactInfo;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date date;

    /* TODO ADD REVIEWS */

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

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public List<User> getSellers()
    {
        return sellers;
    }

    public void setSellers(List<User> sellers)
    {
        this.sellers = sellers;
    }

    public ContactInfo getContactInfo()
    {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo)
    {
        this.contactInfo = contactInfo;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shop)) {
            return false;
        }

        Shop product = (Shop) o;

        return !(this.getId() != null ? !this.getId().equals(product.getId()) : product.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
