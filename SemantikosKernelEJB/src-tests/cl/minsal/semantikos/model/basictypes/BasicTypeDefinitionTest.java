package cl.minsal.semantikos.model.basictypes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BasicTypeDefinitionTest {

    @org.junit.Test
    public void testCreateInstance() throws Exception {
        createTeratogenicoBT();
    }

    @Test
    public void testContains() throws Exception {
        BasicTypeDefinition<String> teratogenicoBT = createTeratogenicoBT();

        assertTrue(teratogenicoBT.contains("A"));
        assertTrue(teratogenicoBT.contains("A/X"));
    }

    private BasicTypeDefinition<String> createTeratogenicoBT() {
        BasicTypeDefinition<String> btd = new BasicTypeDefinition<>("Simple String", "Dominio de Riesgo Teratogénico");

        btd.addToDomain("A");
        btd.addToDomain("A/X");
        btd.addToDomain("B");
        btd.addToDomain("B/C");
        btd.addToDomain("B/D");
        btd.addToDomain("C");
        btd.addToDomain("C/D");
        btd.addToDomain("D");
        btd.addToDomain("X");

        return btd;
    }
}