package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.relationships.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Un CrossMap es una relación atributo de un Concepto.
 *
 * @author Andrés Farías
 */
public class CrossMap extends Relationship implements Target {

    public CrossMap(ConceptSMTK sourceConcept, Target target, RelationshipDefinition relationshipDefinition) {
        super(sourceConcept, target, relationshipDefinition,new ArrayList<RelationshipAttribute>());
    }

    public CrossMap(@NotNull long id, @NotNull ConceptSMTK sourceConcept, @NotNull Target target, @NotNull RelationshipDefinition relationshipDefinition, Timestamp validityUntil) {
        super(id, sourceConcept, target, relationshipDefinition, validityUntil,new ArrayList<RelationshipAttribute>());
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
