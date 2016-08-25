package cl.minsal.semantikos.kernel.auth;

import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by BluePrints Developer on 18-05-2016.
 */
@Stateless(name = "DummyAuthenticationEJB")
public class DummyAuthenticationBean extends AuthenticationMethod{

    static private final Logger logger = LoggerFactory.getLogger(DummyAuthenticationBean.class);

    public  void authenticate(String username, String password, HttpServletRequest request) throws AuthenticationException{
        if("bpadmin".equals(username) && "bpadmin".equals(password))
            return ;

        throw new AuthenticationException("Usuario y/o Contraseña incorrecta");

    }

    public IUser getUserDetails(String username) {

        if("bpadmin".equals(username) )
            return makeDummyAdminUser();

        return null;
    }

    private IUser makeDummyAdminUser() {
        User user = new User();

        user.setUsername("bpadmin");
        user.setEmail("admin@blueprintsit.cl");
        user.setName("Usuario Dummy Admin");
        //user.getRoles().add("admins");
        //user.getGroups().add("admins");

        return user;
    }

    public boolean hasRole(String username, String role) {
        return false;
    }

    public boolean isInGroup(String username, String group) {
        return false;
    }

    public List<String> getUsersInGroup(String group) {
        return null;
    }

    @Override
    public void setUserPassword(String username, String password) throws PasswordChangeException {

    }


}
