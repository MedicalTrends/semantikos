package cl.minsal.semantikos.kernel.auth;

import cl.minsal.semantikos.model.IUser;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
public abstract class AuthenticationMethod {

    public abstract boolean authenticate(String username, String password, HttpServletRequest request) throws AuthenticationException;
    public abstract IUser getUserDetails(String username);
    public abstract boolean hasRole(String username,String role);
    public abstract boolean isInGroup(String username,String group);
    public abstract List<String> getUsersInGroup(String group);

    public abstract void setUserPassword(String username, String password) throws PasswordChangeException;

}
