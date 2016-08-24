package cl.minsal.semantikos.model.relationships;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;
import static org.junit.Assert.*;

public class RelationshipDefinitionFactoryTest {

    @Test
    public void testCreateFromJSON() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonExpression = "[{\"id\":1,\"idTargetDefinition\":1,\"name\":\"Riesgo Teratogénico\",\"description\":\"Riesgo Teratogénico\",\"lowerBoundary\":1,\"upperBoundary\":1}]\n" +
                "get_relationship_definitions_by_category\n" +
                "\"[{\"id\":1,\"id_target_definition\":1,\"name\":\"Riesgo Teratogénico\",\"description\":\"Riesgo Teratogénico\",\"lower_boundary\":1,\"upper_boundary\":1}]";
        RelationshipDefinitionDTO[] relationshipDefinitionDTOs = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipDefinitionDTO[].class);

        System.out.println(relationshipDefinitionDTOs);
    }
}