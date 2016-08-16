package cl.minsal.semantikos.kernel.auth;

import cl.minsal.semantikos.model.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
@Stateless(name = "AuthenticationManagerEJB")
public class AuthenticationManagerBean{

    static private final Logger logger = LoggerFactory.getLogger(AuthenticationManagerBean.class);


    @EJB(name = "DummyAuthenticationEJB")
    DummyAuthenticationBean dummyAuthenticationBean;

    public boolean authenticate(String username, String password){

        return getAuthenticationMethod().authenticate(username,password);
    }

    public IUser getUserDetails(String username){
        return getAuthenticationMethod().getUserDetails(username);
    }

    public boolean hasRole(String username, String role) {
        return getAuthenticationMethod().hasRole(username, role);
    }

    public boolean isInGroup(String username, String group) {
        return  getAuthenticationMethod().isInGroup(username, group);
    }

    public List<String> getUsersInGroup(String group) {
        return getAuthenticationMethod().getUsersInGroup(group);
    }


    //TODO retornar dianamicamente dependiendo de alguna deteccion del ambiente?
    private AuthenticationMethod getAuthenticationMethod(){
        return dummyAuthenticationBean;
    }
}
