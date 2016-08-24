package cl.minsal.semantikos.model.basictypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

public class BasicTypeDefinitionFactoryTest {

    @Test
    public void testCreateFromJSON() throws Exception {

        String json = "{\"id\":1,\"name\":\"Lista Excluyente\",\"description\":\"Lista Excluyente\",\"id_type\":1,\"type_name\":\"string\",\"domain\":[\"A\", \"A/X\", \"B\", \"B/C\", \"B/D\", \"C\", \"C/D\", \"D\", \"X\"]}";

        ObjectMapper mapper = new ObjectMapper();
        BasicTypeDefinitionDTO basicTypeDefinitionDTO
                = mapper.readValue(underScoreToCamelCaseJSON(json), BasicTypeDefinitionDTO.class);

        System.out.println(basicTypeDefinitionDTO.toString());
    }
}