package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.DaoTools;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import cl.minsal.semantikos.model.basictypes.CloseInterval;
import cl.minsal.semantikos.model.basictypes.Interval;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by andres on 7/22/16.
 */
@Stateless
public class TargetTypeDAOImpl implements TargetTypeDAO {


    @Override
    public BasicTypeDefinition findByID(long idBasicType) {

        BasicTypeDefinition basicTypeDefinition = new BasicTypeDefinition();

        ConnectionBD connect = new ConnectionBD();
        String GET_BASIC_TYPE_BY_ID = "{call semantikos.get_basic_type_definition_by_id(?)}";
        String GET_BASIC_DOMAIN_BY_ID = "{call semantikos.get_basic_domain_definition_by_id(?)}";

        try (Connection connection = connect.getConnection();

             CallableStatement call = connection.prepareCall(GET_BASIC_TYPE_BY_ID);
             CallableStatement call2 = connection.prepareCall(GET_BASIC_DOMAIN_BY_ID)) {

             /* Se invoca la consulta para recuperar el basic type definition */

            call.setLong(1, idBasicType);
            call.execute();

            ResultSet rs = call.getResultSet();

            while (rs.next()) {

                long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Integer lower_boundary = DaoTools.getInteger(rs,"lower_boundary");
                Integer upper_boundary = DaoTools.getInteger(rs,"upper_boundary");

                basicTypeDefinition = new BasicTypeDefinition(id, name, description);

                System.out.println("lower_boundary="+lower_boundary);

                if (lower_boundary!=null || upper_boundary!=null)
                    basicTypeDefinition.setInterval(new CloseInterval(lower_boundary, upper_boundary));

                    /* Se invoca la consulta para recuperar el basic type domain */
                call2.setLong(1, idBasicType);
                call2.execute();

                ResultSet rs2 = call2.getResultSet();

                while (rs2.next()) {

                    Float floatValue = DaoTools.getFloat(rs2,"float_value");
                    String stringValue = DaoTools.getString(rs2,"string_value");
                    Long intValue = DaoTools.getLong(rs2,"int_value");
                    Boolean booleanValue = DaoTools.getBoolean(rs2,"boolean_value");
                    Date dateValue = DaoTools.getDate(rs2,"date_value");

                    if (floatValue!=null)
                        basicTypeDefinition.addToDomain(floatValue);
                    if (dateValue!=null)
                        basicTypeDefinition.addToDomain(dateValue);
                    if (stringValue!=null)
                        basicTypeDefinition.addToDomain(stringValue);
                    if (booleanValue!=null)
                        basicTypeDefinition.addToDomain(booleanValue);
                    if (intValue != null)
                        basicTypeDefinition.addToDomain(intValue);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return basicTypeDefinition;

    }
}
