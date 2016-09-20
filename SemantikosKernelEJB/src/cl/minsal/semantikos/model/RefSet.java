package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.audit.AuditableEntity;

/**
 * @author Andrés Farías on 9/7/16.
 */
public class RefSet extends PersistentEntity implements AuditableEntity {

    /** La institución a la cual pertenece el RefSet */
    private Institution institution;

    public RefSet(Institution institution) {
        this.institution = institution;
    }

    public Institution getInstitution() {
        return institution;
    }
}
