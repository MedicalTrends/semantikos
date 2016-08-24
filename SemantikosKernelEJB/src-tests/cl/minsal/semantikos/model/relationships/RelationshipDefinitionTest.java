package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.Multiplicity;
import cl.minsal.semantikos.model.MultiplicityFactory;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableFactory;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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

        /* Ahora tratar de crear una relación de este tipo */
        Relationship relationship = null; //FIXME: new Relationship(relationshipDefinition);
        HelperTable helperTableATC = HelperTableFactory.getInstance().getHelperTableATC();
        relationship.setTarget(new HelperTableRecord(helperTableATC, (long)1));

        assertTrue(relationship.isConsistent());
    }
}