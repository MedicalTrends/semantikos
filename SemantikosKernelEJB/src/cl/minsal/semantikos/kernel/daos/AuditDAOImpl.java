package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.audit.ConceptAuditAction;
import cl.minsal.semantikos.model.audit.ConceptAuditActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andrés Farías on 8/23/16.
 */
@Stateless
public class AuditDAOImpl implements AuditDAO {

    /** Logger para la clase */
    private static final Logger logger = LoggerFactory.getLogger(AuditDAOImpl.class);

    @EJB
    private ConceptAuditActionFactory conceptAuditActionFactory;

    @Override
    public List<ConceptAuditAction> getConceptAuditActions(long idConcept, int numberOfChanges, boolean changes) {
        ConnectionBD connect = new ConnectionBD();
        String sqlQuery = "{call semantikos.get_concept_audit_actions(?,?,?)}";

        List<ConceptAuditAction> auditActions;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sqlQuery)) {

            /* Se invoca la consulta para recuperar las relaciones */
            call.setLong(1, idConcept);
            call.setInt(2, numberOfChanges);
            call.setBoolean(3, changes);
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                String jsonResult = rs.getString(1);
                auditActions = conceptAuditActionFactory.createAuditActionsFromJSON(jsonResult);
            } else {
                String errorMsg = "Un error imposible ocurrio al pasar JSON a BasicTypeDefinition";
                logger.error(errorMsg);
                throw new EJBException(errorMsg);
            }
            rs.close();

        } catch (SQLException e) {
            String errorMsg = "No se pudo parsear el JSON a BasicTypeDefinition.";
            logger.error(errorMsg);
            throw new EJBException(errorMsg, e);
        }

        return auditActions;
    }
}
