package cl.minsal.semantikos.model.relationships;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.ejb.EJBException;
import java.io.IOException;
import java.util.Arrays;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;
import static org.junit.Assert.*;

public class RelationshipFactoryTest {

    @Test
    public void testCreateFromJSON() throws Exception {

    }

    @Test
    public void testCreateRelationshipsFromJSON() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RelationshipDTO[] relationships;
        String jsonExpression = "[{\"id\":2,\"id_source_concept\":1,\"id_target\":2,\"id_relationship_definition\":1,\"validity_until\":null}, \n" +
                " {\"id\":3,\"id_source_concept\":1,\"id_target\":3,\"id_relationship_definition\":1,\"validity_until\":null}]";
        relationships = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipDTO[].class);
        System.out.println(Arrays.toString(relationships));
    }
}