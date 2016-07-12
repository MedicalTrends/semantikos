package cl.minsal.semantikos.designmodelweb.auth;

import cl.minsal.semantikos.designmodelweb.Constants;
import cl.minsal.semantikos.kernel.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Francisco Mendez on 19-05-2016.
 */
public class AuthenticationFilter implements Filter{


    static private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (  req.getRequestURI().contains(Constants.LOGIN_PAGE) || req.getRequestURI().contains(Constants.ERRORS_FOLDER) || hasPermission(req)) {
            chain.doFilter(request, response);
        } else if (!isLoggedIn(req)) { //perdio la sesion
            res.sendRedirect(req.getContextPath() +"/" + Constants.VIEWS_FOLDER+ "/" + Constants.LOGIN_PAGE + "?viewExpired=true&originalURI="+req.getRequestURI());
        } else if(!hasPermission(req)) {//no tiene permiso para acceder a la pagina solicitada
            res.sendRedirect(req.getContextPath() +"/" + Constants.VIEWS_FOLDER+ "/" + Constants.ERRORS_FOLDER+ "/" + Constants.AUTH_ERROR_PAGE );
        } else {//otros casos que nunca deberian darse
            res.sendRedirect(req.getContextPath() + "/" + Constants.VIEWS_FOLDER+ "/"  + Constants.LOGIN_PAGE );
        }

    }

    private boolean isLoggedIn(HttpServletRequest req) {
        return req.getSession().getAttribute(AuthenticationBean.AUTH_KEY) != null;
    }

    private boolean hasPermission(HttpServletRequest req) {
        if ( !isLoggedIn(req) )
            return false;

        AuthenticationBean auth = (AuthenticationBean) req.getSession().getAttribute("authenticationBean");
        User u = auth.getLoggedUser();

        if ( u == null )
            return false;

        return checkPermission(u,req.getRequestURI());
    }

    private boolean checkPermission(User u, String requestURI) {
        return true;
    } // TODO: Ver permisos segun rol?


    public void destroy() { }


    public void init(FilterConfig arg0) throws ServletException { }


}


