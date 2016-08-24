package cl.minsal.semantikos.kernel.auth;

import cl.minsal.semantikos.kernel.daos.AuthDAO;
import cl.minsal.semantikos.model.Profile;
import cl.minsal.semantikos.model.User;

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

    public List<User> getAllUsers() {

        return authDAO.getAllUsers();

    }

    public User getUser(long idUser) {
        return authDAO.getUserById(idUser);
    }

    public void updateUser(User user) {

        authDAO.updateUser(user);
    }

    public List<Profile> getAllProfiles() {

        return authDAO.getAllProfiles();
    }


}
