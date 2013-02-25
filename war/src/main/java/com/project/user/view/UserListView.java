package com.project.user.view;

import com.project.domain.User;
import com.project.framework.business.EntitySelected;
import com.project.framework.view.AbstractListView;
import com.project.user.business.UserHome;
import com.project.user.business.UserList;
import org.jboss.seam.international.status.Messages;

import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * User: Rafal Gielczowski
 * Date: 2/22/13 Time: 3:41 PM
 */
@Named
@ViewScoped
public class UserListView extends AbstractListView<User> implements Serializable {

    @SuppressWarnings("UnusedDeclaration")
    public UserListView()
    {
    }

    @Inject
    public UserListView(UserHome userHome, UserList userList, @EntitySelected Event<User> entitySelectedEvent, Messages messages)
    {
        super(userHome, userList, entitySelectedEvent, messages);
    }

    @Override
    public UserList getEntityList()
    {
        return (UserList) super.getEntityList();
    }
}
