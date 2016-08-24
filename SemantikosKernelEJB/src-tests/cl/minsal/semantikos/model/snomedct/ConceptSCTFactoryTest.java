package cl.minsal.semantikos.model.snomedct;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;
import static org.junit.Assert.*;

public class ConceptSCTFactoryTest {

    @Test
    public void testCreateFromJSON() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonExpression = "{\"id\":1,\"effectiveTime\":null,\"active\":true,\"moduleId\":1,\"definitionStatusId\":1}";
        ConceptSCT conceptSCT = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), ConceptSCT.class);
        System.out.println(conceptSCT.toString());
    }
}