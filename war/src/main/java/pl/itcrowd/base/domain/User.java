package pl.itcrowd.base.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(name = "UNIQUE___USERS___EMAIL", columnNames = "EMAIL"))
public class User implements Serializable, Identifiable<Long> {

    @Id
    @GeneratedValue(generator = "USERS_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "USERS_ID_SEQUENCE", sequenceName = "USERS_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Email
    @Length(min = 1, max = 255)
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @NotNull
    @Length(max = 255)
    @Column(name = "FIRSTNAME", nullable = false, length = 255)
    private String firstName;

    @NotNull
    @Length(max = 255)
    @Column(name = "LASTNAME", nullable = false, length = 255)
    private String lastName;

    @Column(name = "PASSWORD_DIGEST", length = 255)
    @Length(max = 255)
    private String passwordDigest;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;

    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private RoleEnum role;

    @NotNull
    @Column(name = "CLIENT_LANGUAGE", length = 3, nullable = false)
    private String clientLanguage;

    // ----- GETTERS & SETTERS

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPasswordDigest()
    {
        return passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest)
    {
        this.passwordDigest = passwordDigest;
    }

    public Date getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public RoleEnum getRole()
    {
        return role;
    }

    public void setRole(RoleEnum role)
    {
        this.role = role;
    }

    public String getClientLanguage()
    {
        return clientLanguage;
    }

    public void setClientLanguage(String clientLanguage)
    {
        this.clientLanguage = clientLanguage;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null);
    }

    @Override
    public int hashCode()
    {
        return id != null ? id.hashCode() : 0;
    }
}
