package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetType;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Un CrossMap es una relación atributo de un Concepto.
 *
 * @author Andrés Farías
 */
public class CrossMap extends Relationship implements Target {

    public CrossMap(ConceptSMTK sourceConcept, Target target, RelationshipDefinition relationshipDefinition) {
        super(sourceConcept, target, relationshipDefinition);
    }

    public CrossMap(@NotNull long id, @NotNull ConceptSMTK sourceConcept, @NotNull Target target, @NotNull RelationshipDefinition relationshipDefinition, Timestamp validityUntil) {
        super(id, sourceConcept, target, relationshipDefinition, validityUntil);
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.CrossMap;
    }
}
