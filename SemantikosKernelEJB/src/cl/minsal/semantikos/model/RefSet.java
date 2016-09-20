package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.audit.AuditableEntity;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 9/7/16.
 */
public class RefSet extends PersistentEntity implements AuditableEntity {

    /** Nombre del RefSet. Nombre corto y descriptivo de su contenido, para identificación por humanos */
    private String name;

    /** La institución a la cual pertenece el RefSet */
    private Institution institution;

    /** Fecha hasta la cuál es vigente el RefSet */
    private Timestamp validityUntil;

    /** Fecha de Creación */
    private Timestamp creationDate;

    public RefSet(String name, Institution institution, Timestamp creationDate) {
        this.institution = institution;
        this.name = name;
        this.creationDate = creationDate;
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

    public String getName() {
        return name;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }
}
