package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * Esta clase representa una relación hacia un concepto Snomed-CT.
 *
 * @author Andrés Farías
 * @created 9/27/16.
 */
public class SnomedCTRelationship extends Relationship {

    public SnomedCTRelationship(ConceptSMTK sourceConcept, ConceptSCT conceptSCT, RelationshipDefinition relationshipDefinition, List<RelationshipAttribute> relationshipAttributes) {
        super(sourceConcept, conceptSCT, relationshipDefinition, relationshipAttributes);
    }

    public SnomedCTRelationship(@NotNull long id, @NotNull ConceptSMTK sourceConcept, @NotNull ConceptSCT conceptSCT, @NotNull RelationshipDefinition relationshipDefinition, Timestamp validityUntil, List<RelationshipAttribute> relationshipAttributes) {
        super(id, sourceConcept, conceptSCT, relationshipDefinition, validityUntil, relationshipAttributes);
    }

    public boolean isDefinitional(){
        String name = this.getRelationshipDefinition().getName();
        return name.equalsIgnoreCase("ES_UN") || name.equalsIgnoreCase("ES_UN_MAPEO_DE");
    }

    @Override
    public ConceptSCT getTarget() {
        return (ConceptSCT) super.getTarget();
    }
}
