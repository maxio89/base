package pl.itcrowd.base.domain;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Length;
import pl.itcrowd.base.Constants;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @NotNull
    @ManyToOne(optional = false)
    @ForeignKey(name = "FK___PRODUCT___PRODUCT_CATEGORY")
    @JoinColumn(name = "PARENT_CATEGORY_ID", nullable = true)
    private ProductCategory category;

    @NotNull
    @Length(min = 2, max = 255)
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<Discount> discounts;

    @OneToMany(cascade = CascadeType.ALL)
    @ForeignKey(name = "FK___PRODUCT___FILES")
    @JoinColumn(name = "PRODUCT_ID", nullable = true)
    private List<ProductFile> files;

    @Id
    @GeneratedValue(generator = "PRODUCT_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRODUCT_ID_SEQUENCE", sequenceName = "PRODUCT_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Min(1)
    @Column(name = "MAX_QUANTITY_IN_PARCEL", nullable = false)
    private Long maxQuantityInParcel;

    @NotNull
    @Length(min = 2, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @NotNull
    @Min(0)
    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @NotNull
    @Column(name = "SHIPPING_COST", nullable = false)
    private BigDecimal shippingCost;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "PRODUCT___TAG",
        joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")},
        inverseJoinColumns = {@JoinColumn(name = "TAG_ID", referencedColumnName = "ID")})
    @ForeignKey(name = "FK___PRODUCT___PRODUCT____TAG", inverseName = "FK___PRODUCT___TAG___PRODUCT")
    private List<Tag> tags;

    @Size(min = 1, max = 1000000)
    @Column(name = "THUMBNAIL_CONTENT", length = 1000000)
    private byte[] thumbnail;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public ProductCategory getCategory()
    {
        return category;
    }

    public void setCategory(ProductCategory category)
    {
        this.category = category;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Discount> getDiscounts()
    {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts)
    {
        this.discounts = discounts;
    }

    public List<ProductFile> getFiles()
    {
        return files;
    }

    public void setFiles(List<ProductFile> productFiles)
    {
        this.files = productFiles;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getMaxQuantityInParcel()
    {
        return maxQuantityInParcel;
    }

    public void setMaxQuantityInParcel(Long maxQuantityInParcel)
    {
        this.maxQuantityInParcel = maxQuantityInParcel;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
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

    public List<Tag> getTags()
    {
        if (tags == null) {
            tags = new ArrayList<Tag>();
        }
        return tags;
    }

    public void setTags(List<Tag> tags)
    {
        this.tags = tags;
    }

    public byte[] getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailToBase64()
    {
        return thumbnail != null ? Constants.BASE64_PREFIX + Base64.encodeBase64String(thumbnail) : null;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }

        Product product = (Product) o;

        return !(this.getId() != null ? !this.getId().equals(product.getId()) : product.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
