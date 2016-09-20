package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.audit.AuditableEntity;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 9/7/16.
 */
public class RefSet extends PersistentEntity implements AuditableEntity {

    /** La institución a la cual pertenece el RefSet */
    private Institution institution;

    /** Fecha hasta la cuál es vigente el RefSet */
    private Timestamp validityUntil;

    public RefSet(Institution institution) {
        this.institution = institution;
    }

    public Institution getInstitution() {
        return institution;
    }

    public Timestamp getValidityUntil() {
        return validityUntil;
    }

    public void setValidityUntil(Timestamp validityUntil) {
        this.validityUntil = validityUntil;
    }
}
