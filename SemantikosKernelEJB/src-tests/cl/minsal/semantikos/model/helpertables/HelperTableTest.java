package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.model.Multiplicity;
import cl.minsal.semantikos.model.MultiplicityFactory;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelperTableTest {

    @Test
    public void testIsBasicType() throws Exception {
        
        HelperTable helperTable = HelperTableFactory.getInstance().getHelperTableATC();
        assertFalse(helperTable.isBasicType());
    }
    
    @Test
    public void testConstructor() throws Exception {
        
        HelperTable helperTable = HelperTableFactory.getInstance().getHelperTableATC();

        Multiplicity multiplicity = MultiplicityFactory.ONE_TO_MANY;
        new RelationshipDefinition("ATC", "Para ATC", multiplicity, helperTable);
    }
    
    
}