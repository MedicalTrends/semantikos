package cl.minsal.semantikos.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConceptSMTKTest {

    @Test
    public void testHasFavouriteDescription() throws Exception {

        ConceptSMTK conceptSMTK = new ConceptSMTK();

        assertTrue(conceptSMTK.getDescriptions().isEmpty());
        assertFalse(conceptSMTK.hasFavouriteDescription());
    }

    @Test
    public void testHasFavouriteDescription02() throws Exception {

        ConceptSMTK conceptSMTK = new ConceptSMTK();
        Description description = new Description("Infarto Agudo al Miocardio", DescriptionTypeFactory.getInstance().getFavoriteDescriptionType());
        conceptSMTK.addDescription(description);

        assertEquals(1, conceptSMTK.getDescriptions().size());
        assertTrue(conceptSMTK.hasFavouriteDescription());
    }
}