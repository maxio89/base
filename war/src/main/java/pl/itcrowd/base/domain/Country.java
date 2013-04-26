package pl.itcrowd.base.domain;

import org.hibernate.validator.constraints.NotEmpty;
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
@Table(name = "COUNTRY", uniqueConstraints = @UniqueConstraint(name = "UNIQUE___COUNTRY___NAME", columnNames = "NAME"))
public class Country implements Serializable, Identifiable<Long> {

    //    ------------ FIELDS --------------------------

    @Id
    @GeneratedValue(generator = "COUNTRY_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "COUNTRY_ID_SEQUENCE", sequenceName = "COUNTRY_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "NAME", length = 52, nullable = false)
    private String name;

    // --------------------------- CONSTRUCTORS ---------------------------

    public Country()
    {
    }

    public Country(String name)
    {
        this.name = name;
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

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }

        Country country = (Country) o;

        return !(this.getId() != null ? !this.getId().equals(country.getId()) : country.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
