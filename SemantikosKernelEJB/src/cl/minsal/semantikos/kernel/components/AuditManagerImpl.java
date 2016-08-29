package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.AuditDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.audit.AuditActionType;
import cl.minsal.semantikos.model.audit.ConceptAuditAction;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static cl.minsal.semantikos.model.audit.AuditActionType.CONCEPT_CREATION;

/**
 * @author Andrés Farías
 */
@Stateless
public class AuditManagerImpl implements AuditManagerInterface {

    @EJB
    private AuditDAO auditDAO;

    @Override
    public void recordNewConcept(ConceptSMTK conceptSMTK, IUser user) {
        Timestamp actionDate = new Timestamp(System.currentTimeMillis());
        ConceptAuditAction conceptAuditAction = new ConceptAuditAction(conceptSMTK, CONCEPT_CREATION, actionDate, user, conceptSMTK);
        auditDAO.recordAuditAction(conceptAuditAction);
    }

    @Override
    public void recordUpdateConcept(ConceptSMTK conceptSMTK, IUser user) {
        // TODO: Implement this.
    }

    @Override
    public void recordDescriptionMovement(ConceptSMTK sourceConcept, ConceptSMTK targetConcept, Description description) {
        // TODO: Implement this.
    }

    @Override
    public List<AuditActionType> getAllAuditActionTypes() {
        return Arrays.asList(AuditActionType.values());
    }

    @Override
    public List<ConceptAuditAction> getConceptAuditActions(ConceptSMTK conceptSMTK, int numberOfChanges, boolean changes) {
        return auditDAO.getConceptAuditActions(conceptSMTK.getId(), numberOfChanges, changes);
    }
}
