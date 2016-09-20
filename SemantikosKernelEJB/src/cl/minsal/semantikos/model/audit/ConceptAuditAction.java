package cl.minsal.semantikos.model.audit;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class ConceptAuditAction extends AuditAction {

    /** El concepto en el que se realizó la acción */
    private ConceptSMTK subjectConcept;


    public ConceptAuditAction(ConceptSMTK subjectConcept, AuditActionType auditActionType, Timestamp actionDate, User user, AuditableEntity auditableEntity) {
        super(auditActionType, actionDate, user, auditableEntity);

        this.subjectConcept = subjectConcept;
    }

    public ConceptSMTK getSubjectConcept() {
        return subjectConcept;
    }

    public void setSubjectConcept(ConceptSMTK subjectConcept) {
        this.subjectConcept = subjectConcept;
    }

}
