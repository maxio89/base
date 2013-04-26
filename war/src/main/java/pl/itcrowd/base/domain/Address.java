package pl.itcrowd.base.domain;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Address implements Serializable {

    //    ------------ FIELDS --------------------------

    @NotNull
    @Column(name = "STREET", nullable = false, length = 127)
    private String street;

    @NotNull
    @Column(name = "ZIPCODE", nullable = false, length = 20)
    private String zipCode;

    @NotNull
    @Length(min = 2, max = 255)
    @Column(name = "CITY", nullable = false, length = 127)
    private String city;

    @NotNull
    @ManyToOne(optional = false)
    @ForeignKey(name = "FK___USER___COUNTRY")
    @JoinColumn(name = "COUNTRY_NAME")
    private Country country;

    //    ------------------ GETTERS & SETTERS ----------------------------

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }
}
