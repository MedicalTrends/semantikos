package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.Multiplicity;
import cl.minsal.semantikos.model.MultiplicityFactory;
import cl.minsal.semantikos.model.helpertables.HelperTableFactory;
import org.junit.Test;

public class RelationshipDefinitionTest {

    /**
     * Se prueba crear un atributo de categoria de tipo tabla auxiliar.
     *
     * @throws Exception
     */
    @Test
    public void testHelperTableRelationship() throws Exception {

        Multiplicity multiplicity = new MultiplicityFactory().createMultiplicityByUML("1-1");
        HelperTableFactory helperTableFactory = HelperTableFactory.getInstance();
        TargetDefinition targetDefinition = helperTableFactory.getHelperTableATC();

        RelationshipDefinition relationshipDefinition = new RelationshipDefinition("Codigo ATC", "Código ATC asociado a un medicamento.", multiplicity, targetDefinition);

    }
}