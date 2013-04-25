package pl.itcrowd.base.user.view;

import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.framework.business.EntitySelected;
import pl.itcrowd.base.framework.view.AbstractListView;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.user.business.UserList;

import javax.annotation.Nonnull;
import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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
    @Nonnull
    public UserList getEntityList()
    {
        return (UserList) super.getEntityList();
    }
}
