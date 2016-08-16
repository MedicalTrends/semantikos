package cl.minsal.semantikos.kernel.auth;

import cl.minsal.semantikos.kernel.daos.AuthDAO;
import cl.minsal.semantikos.model.IUser;

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

    public List<IUser> getAllUsers() {

        return authDAO.getAllUsers();

    }
}
