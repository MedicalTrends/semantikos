package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.ConceptSMTK;

/**
 * @author Andrés Farías
 */
public class Relationship {

    /** ID en la base de datos */
    private long idRelationship;

    /** El concepto origen de esta relación */
    private ConceptSMTK sourceConcept;

    /** La definición de esta relación */
    private RelationshipDefinition relationshipDefinition;

    /** El elemento destino de esta relación */
    private Target target;

    /**
     * Este es el constructor mínimo con el cual se crean las Relaciones
     *
     * @param relationshipDefinition Definición de la relación
     */
    public Relationship(RelationshipDefinition relationshipDefinition) {
        this.relationshipDefinition = relationshipDefinition;
    }

    /**
     * Igual al constructor mínimo, pero permite inicializar con el ID.
     *
     * @param id               El identificador único.
     */
    public Relationship(long id, RelationshipDefinition relationshipDefinition) {
        this(relationshipDefinition);
        this.idRelationship = id;
    }

    public long getIdRelationship() {
        return idRelationship;
    }

    public void setIdRelationship(long idRelationship) {
        this.idRelationship = idRelationship;
    }

    public ConceptSMTK getSourceConcept() {
        return sourceConcept;
    }

    public void setSourceConcept(ConceptSMTK sourceConcept) {
        this.sourceConcept = sourceConcept;
    }

    public RelationshipDefinition getRelationshipDefinition() {
        return relationshipDefinition;
    }

    public void setRelationshipDefinition(RelationshipDefinition relationshipDefinition) {
        this.relationshipDefinition = relationshipDefinition;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
}
