package pl.itcrowd.base.domain;

import org.hibernate.validator.constraints.Length;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ProductFile implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "PRODUCT_FILE_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRODUCT_FILE_ID_SEQUENCE", sequenceName = "PRODUCT_FILE_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(min = 1, max = 5000000)
    @Column(name = "CONTENT", length = 5000000)
    private byte[] content;

    @NotNull
    @Length(min = 1, max = 255)
    @Column(name = "NAME", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "MIME_TYPE", length = 127, nullable = false)
    private String mimeType;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductFile)) {
            return false;
        }

        ProductFile productFile = (ProductFile) o;

        return !(this.getId() != null ? !this.getId().equals(productFile.getId()) : productFile.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return getId() != null ? getId().hashCode() : 0;
    }
}
