package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.basictypes.BasicTypeValue;

/**
 * Created by root on 08-07-16.
 */
public class Relationship {

    /** ID en la base de datos */
    private long idRelationship;

    /** El concepto origen de esta relación */
    private ConceptSMTK sourceConcept;

    /** La definición de esta relación */
    private RelationshipDefinition relationshipDefinition;

    /** El tipo destino de esta relación */
    private Target target;


    public Relationship() {
    }

    /**
     * Este es el constructor mínimo con el cual se crean las Relaciones
     *
     * @param relationshipDefinition Definición de la relación
     */
    public Relationship(RelationshipDefinition relationshipDefinition) {
        this.relationshipDefinition = relationshipDefinition;
        if(relationshipDefinition.getTargetDefinition().isBasicType()) {
            BasicTypeValue<String> basicTypeValue = new BasicTypeValue<String>();
            this.setTarget(basicTypeValue);
        }
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
