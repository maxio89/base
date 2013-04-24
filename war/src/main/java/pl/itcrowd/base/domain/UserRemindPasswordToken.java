package pl.itcrowd.base.domain;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_REMIND_PASSWORD_TOKEN")
public class UserRemindPasswordToken implements Serializable {

    @NotNull
    @Length(min = 1, max = 255)
    @Column(name = "TOKEN", length = 255, nullable = false)
    private String token;

    @OneToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ForeignKey(name = "FK___USER_REMIND_PASSWORD_TOKEN___USER")
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @SuppressWarnings("UnusedDeclaration")
    @Id
    @Column(name = "USER_ID", insertable = false, updatable = false)
    private Long userId;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "GENERATION_DATE", nullable = false)
    private Date generationDate;

    // --------- GETTERS & SETTERS -------------------

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public User getUser()
    {
        return user;
    }

    public Date getGenerationDate()
    {
        return generationDate;
    }

    public void setGenerationDate(Date generationDate)
    {
        this.generationDate = generationDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRemindPasswordToken)) {
            return false;
        }

        UserRemindPasswordToken that = (UserRemindPasswordToken) o;

        return !(userId != null ? !userId.equals(that.userId) : that.userId != null);
    }

    @Override
    public int hashCode()
    {
        return userId != null ? userId.hashCode() : 0;
    }

// -------------------------- OTHER METHODS --------------------------

    public void setUser(User user)
    {
        this.user = user;
        this.userId = user == null ? null : user.getId();
    }
}
