package com.project.user.view;

import com.project.domain.User;
import com.project.user.business.UserHome;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * User: Rafal Gielczowski
 * Date: 2/22/13 Time: 3:41 PM
 */

@Named
@ViewScoped
public class UserListView implements Serializable {

    @Inject
    private UserHome userHome;

    public List<User> getUsersList(){
        return null; //TODO user management
    }

}
