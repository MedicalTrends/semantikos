package cl.minsal.semantikos.kernel.auth;

import cl.minsal.semantikos.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
@Stateless(name = "AuthenticationManagerEJB")
public class AuthenticationManager {

    static private final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);


    @EJB(name = "DummyAuthenticationEJB")
    DummyAuthenticationBean dummyAuthenticationBean;


    @EJB(name = "JbossSecurutyDomainAuthenticationEJB")
    JbossSecurutyDomainAuthenticationBean jbossSecurutyDomainAuthenticationBean;

    public boolean authenticate(String username, String password, HttpServletRequest request) throws AuthenticationException{

        return getAuthenticationMethod().authenticate(username,password,request);
    }

    public User getUserDetails(String username){
        return getAuthenticationMethod().getUser(username);
    }



    //TODO retornar dianamicamente dependiendo de alguna deteccion del ambiente?
    private AuthenticationMethod getAuthenticationMethod(){

        //return dummyAuthenticationBean;
        return jbossSecurutyDomainAuthenticationBean;
    }



    public void setUserPassword(String username, String password) throws PasswordChangeException {
        getAuthenticationMethod().setUserPassword(username,password);
    }
}
