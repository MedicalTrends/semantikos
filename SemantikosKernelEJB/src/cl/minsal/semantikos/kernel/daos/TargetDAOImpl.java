package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.List;

/**
 * @author Diego Soto
 */
@Stateless
public class TargetDAOImpl implements TargetDAO {

    /**
     * El logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(TargetDAOImpl.class);

    @EJB
    private TargetFactory targetFactory;

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

    @Override
    public Target getTargetByID(long idTarget) {

        Target target;
        ConnectionBD connect = new ConnectionBD();
        String sqlQuery = "{call semantikos.get_target_by_id(?)}";
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sqlQuery)) {

            /* Se invoca la consulta para recuperar las relaciones */
            call.setLong(1, idTarget);
            call.execute();

            /* Cada Fila del ResultSet trae una relación */
            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                String jsonResult = rs.getString(1);
                target = targetFactory.createTargetFromJSON(jsonResult);
            }else {
                String errorMsg = "Un error imposible acaba de ocurrir";
                logger.error(errorMsg);
                throw new EJBException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            String errorMsg = "Erro al invocar get_relationship_definitions_by_category(" + idTarget + ")";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        return target;
    }
}
