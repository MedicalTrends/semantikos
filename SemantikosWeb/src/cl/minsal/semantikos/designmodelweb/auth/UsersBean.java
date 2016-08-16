package cl.minsal.semantikos.designmodelweb.auth;

import cl.minsal.semantikos.kernel.auth.UserManager;
import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    static private final Logger logger = LoggerFactory.getLogger(UsersBean.class);

    @EJB
    UserManager userManager;

    IUser selectedUser;

    public IUser getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(IUser selectedUser) {
        this.selectedUser = selectedUser;
    }


    public List<IUser> getAllUsers(){

        logger.info("buscando usuarios");

        return userManager.getAllUsers();


    }


    public void newUser(){
        selectedUser = new User();
    }
}
