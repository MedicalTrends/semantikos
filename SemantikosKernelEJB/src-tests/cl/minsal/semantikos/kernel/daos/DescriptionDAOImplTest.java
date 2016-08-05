package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.DescriptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;
import static org.junit.Assert.*;

public class DescriptionDAOImplTest {

    @Test
    public void testJSON2DescriptionTypes() throws Exception {
        String jsonContent = "[{\"id\":1,\"name\":\"FSN\",\"description\":\"Full Specified Name\"}, \n" +
                " {\"id\":2,\"name\":\"preferido\",\"description\":\"Descripción Preferida\"}, \n" +
                " {\"id\":3,\"name\":\"sinónimo\",\"description\":\"Sinónimo\"}, \n" +
                " {\"id\":4,\"name\":\"abreviado\",\"description\":\"Abreviado\"}]";

        ObjectMapper mapper = new ObjectMapper();
        DescriptionTypeDTO[] descriptionType = mapper.readValue(underScoreToCamelCaseJSON(jsonContent), DescriptionTypeDTO[].class);
        System.out.println(descriptionType);
    }
}