package pl.itcrowd.base.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Embeddable
public class ContactInfo extends Address implements Serializable {

    //    ------------ FIELDS --------------------------

    @Pattern(regexp = "^[0-9]+$", message = "{ContactInfo.telephonePattern}")
    @NotNull
    @Length(min = 3, max = 32)
    @Column(name = "TELEPHONE", nullable = false, length = 32)
    private String telephone;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }
}
