package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import java.sql.*;

/**
 * Created by des01c7 on 16-08-16.
 */
public class TargetDAOImpl implements TargetDAO {

    /**
     * El logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(TargetDAOImpl.class);


    @Override
    public long createTarget(float floatValue, Timestamp dateValue, String stringValue, boolean booleanValue, int intValue, long idAuxiliary, long idExtern, long idConceptSCT, long idConceptSMTK, long targetType) {
        long target;

        ConnectionBD connect = new ConnectionBD();

        String sql = "{call semantikos.create_target(?,?,?,?,?,?,?,?,?,?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setFloat(1, floatValue);
            call.setTimestamp(2, dateValue);
            call.setString(3,stringValue);
            call.setBoolean(4,booleanValue);
            call.setInt(5,intValue);
            call.setFloat(6,idAuxiliary);
            call.setFloat(7,idExtern);
            call.setFloat(8,idConceptSCT);
            call.setFloat(9,idConceptSMTK);
            call.setFloat(10,targetType);

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                target = rs.getLong(1);
                if(target==-1){
                    String errorMsg = "El target no fue creado";
                    logger.error(errorMsg);
                    throw new EJBException(errorMsg);
                }
            } else {
                String errorMsg = "El target no fue creado";
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            throw new EJBException(e);
        }


        return target;
    }
}
