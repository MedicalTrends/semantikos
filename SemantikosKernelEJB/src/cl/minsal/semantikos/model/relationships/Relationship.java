package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import static cl.minsal.semantikos.kernel.daos.ConceptDAO.NON_PERSISTED_ID;

/**
 * @author Andrés Farías
 */
public class Relationship {

    /** Identificador único de la base de datos */
    private long id;

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
     * @param sourceConcept          El concepto origen de la relación.
     * @param target                 El valor de la relación (destino)
     * @param relationshipDefinition Definición de la relación.
     */
    public Relationship(ConceptSMTK sourceConcept, Target target, RelationshipDefinition relationshipDefinition) {

        /* No está persistido originalmente */
        this.id = NON_PERSISTED_ID;

        this.sourceConcept = sourceConcept;
        this.target = target;
        this.relationshipDefinition = relationshipDefinition;
    }

    /**
     * Igual al constructor mínimo, pero permite inicializar con el ID.
     *
     * @param id                     Identificador único de la relación.
     * @param sourceConcept          El concepto origen de la relación.
     * @param target                 El valor de la relación (destino)
     * @param relationshipDefinition Definición de la relación.
     * @param validityUntil          Fecha de vigencia hasta. Normalmente nulo si está vigente.
     */
    public Relationship(@NotNull long id, @NotNull ConceptSMTK sourceConcept, @NotNull Target target,
                        @NotNull RelationshipDefinition relationshipDefinition, Timestamp validityUntil) {
        this(sourceConcept, target, relationshipDefinition);

        /* Basic ID validation */
        if (id < 0) {
            throw new IllegalArgumentException("El ID de una relación no puede ser negativo o cero.");
        }
        this.id = id;
        this.validityUntil = validityUntil;
    }

    //FIXME
    public Relationship(RelationshipDefinition relationshipDefinition) {

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
        return isConsistent(this.relationshipDefinition, this.target);
    }

    /**
     * Este método es responsable de determinar si la relación tiene un valor consistente con su definición.
     *
     * @param relationshipDefinition La definición de la relación.
     * @param target                 El valor destino de la relación.
     *
     * @return <code>true</code> si la relación es consistente con su definición o <code>false</code> si no.
     */
    private static boolean isConsistent(RelationshipDefinition relationshipDefinition, Target target) {
        if (relationshipDefinition.getTargetDefinition().isBasicType()) {
            return (target instanceof BasicTypeValue);
        }

        return relationshipDefinition.getTargetDefinition().isHelperTable() && (target instanceof HelperTableRecord);
    }

    /**
     * Este método es responsable de determinar si la relación está bien definida, es decir, si posee un valor de
     * target y que éste es consistente con el Target Definition.
     *
     * @return <code>true</code> si está bien definida y <code>false</code> sino.
     */
    public boolean isWellDefined() {
        return target != null && this.isConsistent();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
