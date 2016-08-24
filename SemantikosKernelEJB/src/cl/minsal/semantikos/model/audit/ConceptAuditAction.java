package cl.minsal.semantikos.model.audit;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class ConceptAuditAction {

    /** El concepto en el que se realizó la acción */
    private ConceptSMTK subjectConcept;

    /** The kind of change happened */
    private AuditActionType auditActionType;

    /** La fecha en que tomo lugar la acción auditable */
    private Timestamp actionDate;

    /** El usuario que realizó la acción */
    private IUser user;

    /** La entidad que fue el sujeto mismo de la acción: concepto, relación (atributo o SCT), descripción o categoría */
    private AuditableEntity auditableEntity;

    public ConceptAuditAction(ConceptSMTK subjectConcept, AuditActionType auditActionType, Timestamp actionDate, IUser user, AuditableEntity auditableEntity) {
        this.subjectConcept = subjectConcept;
        this.auditActionType = auditActionType;
        this.actionDate = actionDate;
        this.user = user;
        this.auditableEntity = auditableEntity;
    }
}
