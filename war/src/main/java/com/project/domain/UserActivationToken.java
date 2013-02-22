package com.project.domain;

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
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "USER_ACTIVATION_TOKEN")
public class UserActivationToken implements Serializable {
// ------------------------------ FIELDS ------------------------------

    @NotNull
    @Length(min = 1, max = 255)
    @Column(name = "TOKEN", length = 255, nullable = false)
    private String token;

    @OneToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ForeignKey(name = "FK___USER_ACTIVATION_TOKEN___USER")
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @SuppressWarnings("UnusedDeclaration")
    @Id
    @Column(name = "USER_ID", insertable = false, updatable = false)
    private Long userId;

// --------------------- GETTER / SETTER METHODS ---------------------

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

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivationToken)) {
            return false;
        }

        UserActivationToken that = (UserActivationToken) o;

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