package cl.minsal.semantikos.model.relationships;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

public class TargetFactoryTest {

    @Test
    public void testCreateTargetFromJSONString() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "{\"id\":1,\"float_value\":null,\"date_value\":null,\"string_value\":\"strig\",\"boolean_value\":null,\"int_value\":null,\"id_auxiliary\":null,\"id_extern\":null,\"id_concept_sct\":null,\"id_concept_stk\":null,\"id_target_type\":null}";
        TargetDTO targetDTO = mapper.readValue(underScoreToCamelCaseJSON(jsonResult), TargetDTO.class);

        System.out.println(targetDTO);
    }

    @Test
    public void testCreateTargetFromJSONValidity() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "{\"id\":5,\"float_value\":null,\"date_value\":\"2011-05-16T15:36:38\",\"string_value\":null,\"boolean_value\":null,\"int_value\":null,\"id_auxiliary\":null,\"id_extern\":null,\"id_concept_sct\":null,\"id_concept_stk\":null,\"id_target_type\":null}";
        TargetDTO targetDTO = mapper.readValue(underScoreToCamelCaseJSON(jsonResult), TargetDTO.class);

        System.out.println(targetDTO);
    }

    @Test
    public void testCreateTargetFromJSONFloat() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "{\"id\":7,\"float_value\":20.1,\"date_value\":null,\"string_value\":null,\"boolean_value\":null,\"int_value\":null,\"id_auxiliary\":null,\"id_extern\":null,\"id_concept_sct\":null,\"id_concept_stk\":null,\"id_target_type\":null}";
        TargetDTO targetDTO = mapper.readValue(underScoreToCamelCaseJSON(jsonResult), TargetDTO.class);

        System.out.println(targetDTO);
    }
}