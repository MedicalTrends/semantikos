package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.crossmaps.Crossmap;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Andrés Farías on 11/3/16.
 */
public class DirectCrossmap extends Crossmap {
    public DirectCrossmap(ConceptSMTK sourceConcept, Target target, RelationshipDefinition relationshipDefinition) {
        super(sourceConcept, target, relationshipDefinition);
    }

    public DirectCrossmap(@NotNull long id, @NotNull ConceptSMTK sourceConcept, @NotNull Target target, @NotNull RelationshipDefinition relationshipDefinition, Timestamp validityUntil) {
        super(id, sourceConcept, target, relationshipDefinition, validityUntil);
    }
}
