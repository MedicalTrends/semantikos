package cl.minsal.semantikos.model.audit;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class ConceptAuditAction extends AuditAction {

    public ConceptAuditAction(ConceptSMTK subjectConcept, AuditActionType auditActionType, Timestamp actionDate, User user, AuditableEntity auditableEntity) {
        super(auditActionType, actionDate, user, auditableEntity, subjectConcept);
    }

    public ConceptSMTK getSubjectConcept() {
        return (ConceptSMTK) getBaseEntity();
    }
}