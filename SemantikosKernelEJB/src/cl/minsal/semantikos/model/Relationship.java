package cl.minsal.semantikos.model;

import javax.persistence.*;

/**
 * Created by root on 08-07-16.
 */
public class Relationship {

    /** ID en la base de datos */
    private long idRelationships;

    /** El concepto origen de esta relación */
    private ConceptSMTK sourceConcept;

    /** La definición de esta relación */
    private RelationshipDefinition relationshipDefinition;

    /** El tipo destino de esta relación */
    private Target target;

    public long getIdRelationships() {
        return idRelationships;
    }

    public void setIdRelationships(long idRelationships) {
        this.idRelationships = idRelationships;
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
