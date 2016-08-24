package cl.minsal.semantikos.model.relationships;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

public class TargetDefinitionFactoryTest {

    @Test
    public void testCreateFromJSONHelperTable() throws Exception {
        String json = "{\"id\":9,\"name\":\"Mapear a DCI\",\"id_category\":null,\"id_helper_table_name\":2,\"id_target_type\":null,\"id_basic_type\":null,\"sct_type\":false}";

        ObjectMapper mapper = new ObjectMapper();
        TargetDefinitionDTO targetDefinitionDTO
                = mapper.readValue(underScoreToCamelCaseJSON(json), TargetDefinitionDTO.class);

        System.out.println(targetDefinitionDTO.toString());

    }

    @Test
    public void testCreateFromJSONCategory() throws Exception {
        String json = "{\"id\":2,\"name\":\"Sustancias\",\"id_category\":105590001,\"id_helper_table_name\":null,\"id_target_type\":2,\"id_basic_type\":null,\"sct_type\":false}";

        ObjectMapper mapper = new ObjectMapper();
        TargetDefinitionDTO targetDefinitionDTO
                = mapper.readValue(underScoreToCamelCaseJSON(json), TargetDefinitionDTO.class);

        System.out.println(targetDefinitionDTO.toString());

    }

    @Test
    public void testCreateFromJSONBasicType() throws Exception {
        String json = "{\"id\":5,\"name\":\"Volumen\",\"id_category\":null,\"id_helper_table_name\":null,\"id_target_type\":null,\"id_basic_type\":3,\"sct_type\":false}";

        ObjectMapper mapper = new ObjectMapper();
        TargetDefinitionDTO targetDefinitionDTO
                = mapper.readValue(underScoreToCamelCaseJSON(json), TargetDefinitionDTO.class);

        System.out.println(targetDefinitionDTO.toString());

    }
    @Test
    public void testCreateFromJSONSCT() throws Exception {
        String json = "{\"id\":10,\"name\":\"Mapeo a\",\"id_category\":null,\"id_helper_table_name\":null,\"id_target_type\":null,\"id_basic_type\":null,\"sct_type\":true}";

        ObjectMapper mapper = new ObjectMapper();
        TargetDefinitionDTO targetDefinitionDTO
                = mapper.readValue(underScoreToCamelCaseJSON(json), TargetDefinitionDTO.class);

        System.out.println(targetDefinitionDTO.toString());

    }
}