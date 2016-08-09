package cl.minsal.semantikos.kernel.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

public class DescriptionDAOImplTest {

    @Test
    public void testJSON2DescriptionTypes() throws Exception {
        String jsonContent = "[{\"id\":1,\"name\":\"FSN\",\"description\":\"Full Specified Name\"}, \n" +
                " {\"id\":2,\"name\":\"preferido\",\"description\":\"Descripción Preferida\"}, \n" +
                " {\"id\":3,\"name\":\"sinónimo\",\"description\":\"Sinónimo\"}, \n" +
                " {\"id\":4,\"name\":\"abreviado\",\"description\":\"Abreviado\"}, \n" +
                " {\"id\":5,\"name\":\"general\",\"description\":\"General\"}, \n" +
                " {\"id\":6,\"name\":\"ambiguo\",\"description\":\"Ambiguo\"}, \n" +
                " {\"id\":7,\"name\":\"mal escrito\",\"description\":\"Mal Escrito\"}]";

        ObjectMapper mapper = new ObjectMapper();
        DescriptionTypeDTO[] descriptionType = mapper.readValue(underScoreToCamelCaseJSON(jsonContent), DescriptionTypeDTO[].class);
        System.out.println(Arrays.toString(descriptionType));
    }
}