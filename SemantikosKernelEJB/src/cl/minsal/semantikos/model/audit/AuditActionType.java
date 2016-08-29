package cl.minsal.semantikos.model.audit;

/**
 * @author Andrés Farías on 8/23/16.
 */
public enum AuditActionType {

    CONCEPT_CREATION(1, "Creación de Concepto", false),
    CONCEPT_PUBLICATION(2, "Publicación de Concepto", true),
    CONCEPT_FAVOURITE_DESCRIPTION_CHANGE(3, "Cambio en descripción preferida de un Concepto", true),
    CONCEPT_DESCRIPTION_BINDING(4, "Descripción agregada a concepto", true),
    CONCEPT_DESCRIPTION_UNBINDING(5, "Descripción eliminada a concepto", true),
    CONCEPT_CATEGORY_CHANGE(6, "Cambio de categoría de Concepto", true),
    CONCEPT_ATTRIBUTE_CHANGE(7, "Cambio de atributo de un concepto", true),
    CONCEPT_RELATIONSHIP_CREATION(8, "Nueva relación de un concepto", false), //TODO: DAO, Manager, Hooks
    CONCEPT_RELATIONSHIP_REMOVAL(9, "Eliminación de relación de un concepto", true), //TODO: DAO, Manager, Hooks
    CONCEPT_RELATIONSHIP_CROSSMAP_CREATION(10, "Nuevo CrossMap de un concepto", false), //TODO: DAO, Manager, Hooks
    CONCEPT_RELATIONSHIP_CROSSMAP_ELIMINATION(11, "Eliminación de CrossMap de un concepto", false), //TODO: DAO, Manager, Hooks
    CONCEPT_RELATIONSHIP_EXTERNAL_TERMINOLOGY_CREATION(12, "Nueva referencia a terminología externa en Concepto", false), //TODO: DAO, Manager, Hooks
    CONCEPT_RELATIONSHIP_EXTERNAL_TERMINOLOGY_ELIMINATION(13, "Eliminación de referencia a terminología externa en Concepto", false), //TODO: DAO, Manager, Hooks
    CONCEPT_RELATIONSHIP_REFSET_CREATION(14, "Nueva referencia a RefSet en Concepto", false), //TODO: DAO, Manager, Hooks
    CONCEPT_RELATIONSHIP_REFSET_ELIMINATION(15, "Referencia a RefSet eliminada en Concepto", false); //TODO: DAO, Manager, Hooks

    /** Identificador único de la base de datos */
    private long id;

    /** Nombre o descripción del cambio */
    private String name;

    /** Indica si la naturaleza del cambio es de edición o adición */
    private boolean change;

    AuditActionType(long id, String name, boolean change) {
        this.id = id;
        this.name = name;
        this.change = change;
    }

    public long getId() {
        return id;
    }

    public boolean isChange() {
        return change;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Este método es responsable de retornar el AuditActionType asociado al ID <code>idAuditActionType</code>.
     *
     * @param idAuditActionType El identificador del AuditActionType.
     *
     * @return El objeto que representa la acción de auditoría.
     */
    public static AuditActionType valueOf(long idAuditActionType) {
        for (AuditActionType auditActionType : values()) {
            if (auditActionType.getId() == idAuditActionType) {
                return auditActionType;
            }
        }

        throw new IllegalArgumentException("No hay un tipo de acción con ID=" + idAuditActionType);
    }
}
