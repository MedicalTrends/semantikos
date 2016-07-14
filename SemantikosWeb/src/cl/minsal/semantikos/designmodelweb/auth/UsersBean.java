package cl.minsal.semantikos.designmodelweb.auth;

import cl.minsal.semantikos.kernel.auth.UserManager;
import cl.minsal.semantikos.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by BluePrints Developer on 14-07-2016.
 */

@ManagedBean(name = "users")
@ViewScoped
public class UsersBean {


    @EJB
    UserManager userManager;

    User selectedUser;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }


    public List<User> getAllUsers(){
        return userManager.getAllUsers();


    }


    public void newUser(){
        selectedUser = new User();
    }
}
