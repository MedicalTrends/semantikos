package cl.minsal.semantikos.kernel.daos;

import org.junit.Test;

import static org.junit.Assert.*;

public class RelationshipDefinitionDAOImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void testGetTargetDefinition() throws Exception {

        RelationshipDefinitionDAOImpl relDAO = new RelationshipDefinitionDAOImpl();
        relDAO.getTargetDefinition(null, null, null, null, null);

    }
}