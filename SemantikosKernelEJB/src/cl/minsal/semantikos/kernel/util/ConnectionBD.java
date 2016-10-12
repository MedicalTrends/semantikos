package cl.minsal.semantikos.kernel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by des01c7 on 29-06-16.
 */
public class ConnectionBD {
    private String driver = "org.postgresql.Driver";
    private String ruta = "jdbc:postgresql://192.168.0.242:5432/semantikos";
    private String user = "postgres";
    private String password = "postgres";
    private Connection connection;

    public ConnectionBD() {
        try{
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(ruta, user, password);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
