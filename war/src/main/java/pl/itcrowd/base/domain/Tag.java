package pl.itcrowd.base.domain;

import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "TAG", uniqueConstraints = @UniqueConstraint(name = "UNIQUE___TAG___NAME", columnNames = "NAME"))
public class Tag implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "TAG_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "TAG_ID_SEQUENCE", sequenceName = "TAG_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "POPULARITY", nullable = false)
    private Long popularity;

    // --------------------------- CONSTRUCTORS ---------------------------

    public Tag()
    {
    }

    public Tag(String name)
    {
        this.name = name;
        this.popularity = 0L;
    }

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

    public Long getPopularity()
    {
        return popularity;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }

        Tag tag = (Tag) o;

        return !(this.getName() != null ? !this.getName().equals(tag.getName()) : tag.getName() != null);
    }

    @Override
    public int hashCode()
    {
        return getId() != null ? getId().hashCode() : 0;
    }
}
