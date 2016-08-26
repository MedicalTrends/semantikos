package cl.minsal.semantikos.kernel.auth;

import cl.minsal.semantikos.kernel.daos.AuthDAO;
import cl.minsal.semantikos.model.Profile;
import cl.minsal.semantikos.model.User;
import org.omg.CORBA.UserException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by BluePrints Developer on 14-07-2016.
 */

@Stateless
public class UserManager {


    @EJB
    AuthDAO authDAO;

    @EJB
    AuthenticationManagerBean authenticationManagerBean;

    public List<User> getAllUsers() {

        return authDAO.getAllUsers();

    }

    public User getUser(long idUser) {
        return authDAO.getUserById(idUser);
    }

    public void updateUser(User user) {

        authDAO.updateUser(user);
    }

    public void createUser(User user) throws AuthDAO.UserExistsException {


        authDAO.createUser(user);

    }

    public List<Profile> getAllProfiles() {

        return authDAO.getAllProfiles();
    }


}
