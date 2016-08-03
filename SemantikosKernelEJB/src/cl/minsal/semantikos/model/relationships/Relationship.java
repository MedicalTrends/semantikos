package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import java.sql.Timestamp;

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

    /** La relación es Vigente (valida) hasta la fecha... */
    private Timestamp validityUntil;

    /**
     * Este es el constructor mínimo con el cual se crean las Relaciones
     *
     * @param relationshipDefinition Definición de la relación
     */
    public Relationship(RelationshipDefinition relationshipDefinition) {
        this.relationshipDefinition = relationshipDefinition;

        if(relationshipDefinition.getTargetDefinition().isBasicType())
            this.target = new BasicTypeValue<>();
        //if(relationshipDefinition.getTargetDefinition().isHelperTable())
            //this.target = new HelperTableRecord();
        if(relationshipDefinition.getTargetDefinition().isSMTKType())
            this.target = new ConceptSMTK();
    }

    /**
     * Igual al constructor mínimo, pero permite inicializar con el ID.
     *
     * @param id El identificador único.
     */
    public Relationship(long id, ConceptSMTK sourceConcept, RelationshipDefinition relationshipDefinition, Target target, Timestamp validityUntil) {
        this(relationshipDefinition);
        this.idRelationship = id;
        this.sourceConcept = sourceConcept;
        this.relationshipDefinition = relationshipDefinition;
        this.target = target;
        this.validityUntil = validityUntil;
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

    public Timestamp getValidityUntil() {
        return validityUntil;
    }

    public void setValidityUntil(Timestamp validityUntil) {
        this.validityUntil = validityUntil;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     * Este método es responsable de determinar si la relación tiene un valor consistente con su definición.
     *
     * @return <code>true</code> si la relación es consistente con su definición o <code>false</code> si no.
     */
    public boolean isConsistent() {

        if (this.relationshipDefinition.getTargetDefinition().isBasicType()){
            return (this.target instanceof BasicTypeValue);
        }

        if (this.relationshipDefinition.getTargetDefinition().isHelperTable()){
            return (this.target instanceof HelperTableRecord);
        }

        return false;
    }
}
