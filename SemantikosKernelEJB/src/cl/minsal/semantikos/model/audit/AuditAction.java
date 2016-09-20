package cl.minsal.semantikos.model.audit;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 9/20/16.
 */
public abstract class AuditAction {

    /** El tipo de acción que se registra */
    private AuditActionType auditActionType;

    /** La fecha en que tomo lugar la acción auditable */
    private Timestamp actionDate;

    /** El usuario que realizó la acción */
    private User user;

    /** La entidad que fue el sujeto mismo de la acción: concepto, relación (atributo o SCT), descripción o categoría */
    private AuditableEntity auditableEntity;

    public AuditAction(AuditActionType auditActionType, Timestamp actionDate, User user, AuditableEntity auditableEntity) {
        this.auditActionType = auditActionType;
        this.actionDate = actionDate;
        this.user = user;
        this.auditableEntity = auditableEntity;
    }

    public AuditActionType getAuditActionType() {
        return auditActionType;
    }

    public void setAuditActionType(AuditActionType auditActionType) {
        this.auditActionType = auditActionType;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuditableEntity getAuditableEntity() {
        return auditableEntity;
    }

    public void setAuditableEntity(AuditableEntity auditableEntity) {
        this.auditableEntity = auditableEntity;
    }

}
