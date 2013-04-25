package pl.itcrowd.base.user.view;

import com.google.common.collect.Lists;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.domain.RoleEnum;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.framework.view.AbstractDetailsView;
import pl.itcrowd.base.user.business.UserHome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserDetailsView extends AbstractDetailsView<User> implements Serializable {

    private UserHome userHome;

    @SuppressWarnings("UnusedDeclaration")
    public UserDetailsView()
    {
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public UserDetailsView(Logger logger, Messages messages, UserHome userHome)
    {
        super(logger, messages);
        this.userHome = userHome;
    }

    /**
     * Initializer method, should be invoked before any actions on this bean.
     * Method fetch user instance from DB if user exists.
     * ID should be set trough this.setUserId(id).
     */
    public void initView()
    {
        getHome().getInstance();
        if (!getHome().isIdDefined()) {
            throw new AuthorizationException("User ID not specified.");
        }
    }

    // ---------- GETTERS / SETTERS ------------- //

    public List<RoleEnum> getAvailableRoles()
    {
        return Lists.newArrayList(RoleEnum.values());
    }

    @Nonnull
    public User getUser()
    {
        return userHome.getInstance();
    }

    @Nullable
    public Long getUserId()
    {
        return (Long) getHome().getId();
    }

    public void setUserId(@Nullable Long userId)
    {
        this.getHome().setId(userId);
    }

    @Override
    protected EntityHome<User> getHome()
    {
        return userHome;
    }

    @Override
    protected String getOutcomeSuccess()
    {
        return "userList";
    }
}
