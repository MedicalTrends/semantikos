package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.Multiplicity;
import org.junit.Test;

public class RelationshipDefinitionTest {

    @Test
    public void testHelperTableRelationship() throws Exception {

        Multiplicity multiplicity = new Multiplicity(1, 1);

        //TODO: To finish.
        TargetDefinition targetDefinition = null;
        RelationshipDefinition relationshipDefinition = new RelationshipDefinition("Codigo ATC", "Código ATC asociado a un medicamento.", multiplicity, targetDefinition);
    }
}