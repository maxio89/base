package pl.itcrowd.base.domain;

import org.hibernate.annotations.ForeignKey;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PRODUCT_CATEGORY", uniqueConstraints = @UniqueConstraint(name = "UNIQUE___PRODUCT_CATEGORY___NAME", columnNames = "NAME"))
public class ProductCategory implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "PRODUCT_CATEGORY_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRODUCT_CATEGORY_ID_SEQUENCE", sequenceName = "PRODUCT_CATEGORY_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<ProductCategory> childrens;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = true, name = "PARENT_PRODUCT_CATEGORY_ID")
    @ForeignKey(name = "FK__PARENT_PRODUCT_CATEGORY")
    private ProductCategory parentCategory;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public List<ProductCategory> getChildrens()
    {
        return childrens;
    }

    public void setChildrens(List<ProductCategory> childrens)
    {
        this.childrens = childrens;
    }

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

    public ProductCategory getParentCategory()
    {
        return parentCategory;
    }

    public void setParentCategory(ProductCategory parentCategory)
    {
        this.parentCategory = parentCategory;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }

        ProductCategory productCategory = (ProductCategory) o;

        return !(this.getId() != null ? !this.getId().equals(productCategory.getId()) : productCategory.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return getId() != null ? getId().hashCode() : 0;
    }
}
