package cl.minsal.semantikos.kernel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by des01c7 on 29-06-16.
 */
public class ConnectionBD {


    /*
    private String driver = "org.postgresql.Driver";
    private String ruta = "jdbc:postgresql://192.168.0.188:5432/semantikos";
    private String user = "postgres";
    private String password = "postgres";
    */


    private Connection connection;

    static private final Logger LOGGER = LoggerFactory.getLogger(ConnectionBD.class);

    public ConnectionBD() {


        javax.naming.InitialContext ctx = null;
        javax.sql.DataSource ds = null;

        try {
            ctx = new javax.naming.InitialContext();
            ds = (javax.sql.DataSource) ctx.lookup("java:jboss/PostgresDS");
            connection = ds.getConnection();
        } catch (NamingException e) {
            LOGGER.error("Error al buscar Datasource en Jboss",e);
        } catch (SQLException e) {
            LOGGER.error("Error al conectarse a BD",e);
        }
    }




    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
