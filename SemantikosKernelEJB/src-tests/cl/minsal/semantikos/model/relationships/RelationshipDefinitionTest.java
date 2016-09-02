package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.CategoryFactory;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Multiplicity;
import cl.minsal.semantikos.model.MultiplicityFactory;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableFactory;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RelationshipDefinitionTest {

    /**
     * Se prueba crear un atributo de categoría de tipo tabla auxiliar.
     *
     * @throws Exception
     */
    @Test
    public void testHelperTableRelationship() throws Exception {

        Multiplicity multiplicity = new MultiplicityFactory().createMultiplicityByUML("1-1");
        HelperTableFactory helperTableFactory = HelperTableFactory.getInstance();
        TargetDefinition targetDefinition = helperTableFactory.getHelperTableATC();

        RelationshipDefinition relationshipDefinition = new RelationshipDefinition("Código ATC", "Código ATC asociado a un medicamento.", multiplicity, targetDefinition);

        /* Ahora tratar de crear una relación de este tipo */
        ConceptSMTK conceptSMTK = new ConceptSMTK(CategoryFactory.getNullCategory());
        HelperTable helperTableATC = HelperTableFactory.getInstance().getHelperTableATC();
        HelperTableRecord target = new HelperTableRecord(helperTableATC, (long) 1);
        Relationship relationship = new Relationship(conceptSMTK, target, relationshipDefinition);

        assertTrue(relationship.isConsistent());
    }
}