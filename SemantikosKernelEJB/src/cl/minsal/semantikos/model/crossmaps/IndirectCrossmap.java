package cl.minsal.semantikos.model.crossmaps;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;

/**
 * @author Andrés Farías on 11/3/16.
 */
public class IndirectCrossmap extends Crossmap {

    public IndirectCrossmap(ConceptSMTK sourceConcept, Target target, RelationshipDefinition relationshipDefinition) {
        super(sourceConcept, target, relationshipDefinition);
    }
}
