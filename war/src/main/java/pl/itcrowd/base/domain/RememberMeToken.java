package pl.itcrowd.base.domain;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "REMEMBER_ME_TOKEN")
public class RememberMeToken implements Serializable {

    @Id
    @GeneratedValue(generator = "REMEMBER_ME_TOKEN_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "REMEMBER_ME_TOKEN_ID_SEQUENCE", sequenceName = "REMEMBER_ME_TOKEN_ID_SEQUENCE", allocationSize = 1, initialValue = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    @ForeignKey(name = "FK___REMEMBER_ME_TOKEN___USER")
    private User user;

    @NotNull
    @Column(name = "TOKEN", length = 255, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_USE", nullable = false)
    private Date lastUse;

    @Column(name = "REMOTE_ADDR", nullable = true, length = 63)
    private String remoteAddr;

    @Column(name = "USER_AGENT_HASH", nullable = true, length = 255)
    private String userAgentHash;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getRemoteAddr()
    {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr)
    {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgentHash()
    {
        return userAgentHash;
    }

    public void setUserAgentHash(String userAgentHash)
    {
        this.userAgentHash = userAgentHash;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getLastUse()
    {
        return lastUse;
    }

    public void setLastUse(Date lastUse)
    {
        this.lastUse = lastUse;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RememberMeToken)) {
            return false;
        }

        RememberMeToken token = (RememberMeToken) o;

        return !(id != null ? !id.equals(token.id) : token.id != null);
    }

    @Override
    public int hashCode()
    {
        return id != null ? id.hashCode() : 0;
    }
}