package com.project.user.view;

import com.project.domain.RoleEnum;
import com.project.domain.User;
import com.project.framework.business.EntityHome;
import com.project.framework.view.AbstractDetailsView;
import com.project.user.business.UserHome;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * User: Rafal Gielczowski
 * Date: 2/25/13 Time: 11:37 AM
 */
@Named
@ViewScoped
public class UserDetailsView extends AbstractDetailsView<User> implements Serializable {

    private UserHome userHome;

    @SuppressWarnings("UnusedDeclaration")
    public UserDetailsView()
    {
    }

    @Inject
    public UserDetailsView(Logger logger, Messages messages, UserHome userHome)
    {
        super(logger, messages);
        this.userHome = userHome;
    }

    /**
     * Method invoked from s:viewAction
     * will fetch user instance from DB if user exists.
     * ID is set in f:param directly in UserHome
     */
    public void initView()
    {
        getHome().getInstance();
    }

    public List<RoleEnum> getAvailableRoles()
    {
        return Arrays.asList(RoleEnum.values());
    }


    // ---------- GETTERS / SETTERS ------------- //

    public Long getUserId()
    {
        return (Long) getHome().getId();
    }

    public void setUserId(Long userId)
    {
        this.getHome().setId(userId);
    }

    public User getUser()
    {
        return userHome.getInstance();
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
