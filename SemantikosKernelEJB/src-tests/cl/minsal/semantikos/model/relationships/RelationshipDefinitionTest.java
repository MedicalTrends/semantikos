package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.CategoryFactory;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Multiplicity;
import cl.minsal.semantikos.model.MultiplicityFactory;
import cl.minsal.semantikos.model.helpertables.HelperTableFactory;
import org.junit.Test;

public class RelationshipDefinitionTest {

    /**
     * Se prueba crear un atributo de categoría de tipo tabla auxiliar.
     *
     * @throws Exception
     */
    @Test
    public void testHelperTableRelationship() throws Exception {

        Multiplicity multiplicity = new MultiplicityFactory().createMultiplicityByUML("1-1");


        /* Ahora tratar de crear una relación de este tipo */
        ConceptSMTK conceptSMTK = new ConceptSMTK(CategoryFactory.getNullCategory());

    }
}