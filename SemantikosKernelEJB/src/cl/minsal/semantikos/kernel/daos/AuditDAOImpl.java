package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.audit.AuditActionType;
import cl.minsal.semantikos.model.audit.AuditableEntity;
import cl.minsal.semantikos.model.audit.ConceptAuditAction;
import cl.minsal.semantikos.model.audit.ConceptAuditActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.*;
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

    @Override
    public void recordAuditAction(ConceptAuditAction conceptAuditAction) {

        logger.debug("Registrando información de Auditoría: " + conceptAuditAction);
        ConnectionBD connect = new ConnectionBD();
        /*
         * param 1: La fecha en que se realiza (Timestamp).
         * param 2: El usuario que realiza la acción (id_user).
         * param 3: concepto en el que se realiza la acción.
         * param 4: El tipo de acción que realiza
         * param 5: La entidad en la que se realizó la acción..
         */
        String sqlQuery = "{call semantikos.create_concept_audit_actions(?,?,?,?,?)}";
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sqlQuery)) {

            /* Se invoca la consulta para recuperar las relaciones */
            Timestamp actionDate = conceptAuditAction.getActionDate();
            User user = conceptAuditAction.getUser();
            ConceptSMTK subjectConcept = conceptAuditAction.getSubjectConcept();
            AuditActionType auditActionType = conceptAuditAction.getAuditActionType();
            AuditableEntity auditableEntity = conceptAuditAction.getAuditableEntity();

            call.setTimestamp(1, actionDate);
            call.setLong(2, user.getIdUser());
            call.setLong(3, subjectConcept.getId());
            call.setLong(4, auditActionType.getId());
            call.setLong(5, auditableEntity.getId());
            call.execute();

        } catch (SQLException e) {
            String errorMsg = "Error al registrar en el log.";
            logger.error(errorMsg);
            throw new EJBException(errorMsg, e);
        }
    }
}
