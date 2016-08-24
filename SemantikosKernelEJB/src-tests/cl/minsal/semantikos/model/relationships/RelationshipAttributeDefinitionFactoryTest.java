package cl.minsal.semantikos.model.relationships;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;
import static org.junit.Assert.*;

public class RelationshipAttributeDefinitionFactoryTest {

    @Test
    public void testCreateFromEmptyJSON() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonExpression = "";
        RelationshipAttributeDefinitionDTO[] relationshipAttributeDefinition = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipAttributeDefinitionDTO[].class);

        System.out.println(Arrays.toString(relationshipAttributeDefinition));
    }
    @Test
    public void testCreateFromJSON() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonExpression = "[{\"id\":1,\"name\":\"sdasd\",\"lower_boundary\":1,\"upper_boundary\":1,\"id_target_definition\":1}]";
        RelationshipAttributeDefinitionDTO[] relationshipAttributeDefinition = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipAttributeDefinitionDTO[].class);

        System.out.println(Arrays.toString(relationshipAttributeDefinition));
    }
}