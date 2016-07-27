package cl.minsal.semantikos.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultiplicityFactoryTest {

    private MultiplicityFactory multiplicityFactory = new MultiplicityFactory();

    @Test
    public void testCreateMultiplicityByUML01() throws Exception {

        Multiplicity multiplicityByUML = multiplicityFactory.createMultiplicityByUML("1-1");

        assertEquals(1, multiplicityByUML.getUpperBoundary());
        assertEquals(1, multiplicityByUML.getLowerBoundary());
    }

    @Test
    public void testCreateMultiplicityByUML02() throws Exception {

        Multiplicity multiplicityByUML = multiplicityFactory.createMultiplicityByUML("1-N");

        assertEquals(1, multiplicityByUML.getLowerBoundary());
        assertEquals(0, multiplicityByUML.getUpperBoundary());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMultiplicityByUML03() throws Exception {
        multiplicityFactory.createMultiplicityByUML("1N");
    }

    @Test
    public void testInterpretLimit01() throws Exception {
        int limit = multiplicityFactory.interpretLimit("1");

        assertEquals(1, limit);
    }

    @Test
    public void testInterpretLimit02() throws Exception {
        int limit = multiplicityFactory.interpretLimit("N");

        assertEquals(0, limit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInterpretLimit03() throws Exception {
        multiplicityFactory.interpretLimit("2a");
    }
}