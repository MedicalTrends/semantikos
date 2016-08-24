package cl.minsal.semantikos.model.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

public class ConceptAuditActionFactoryTest {

    @Test
    public void testCreateAuditActionsFromJSON01() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ConceptAuditActionDTO[] auditActionDTOs;
        String jsonExpression = "[{\"id_concept\":1,\"id_action_type\":5,\"date\":\"2011-07-16T15:36:38\",\"id_user\":1}, \n" +
                " {\"id_concept\":1,\"id_action_type\":4,\"date\":\"2011-06-16T15:36:38\",\"id_user\":1}, \n" +
                " {\"id_concept\":1,\"id_action_type\":2,\"date\":\"2011-05-16T15:36:38\",\"id_user\":1}, \n" +
                " {\"id_concept\":1,\"id_action_type\":3,\"date\":\"2011-01-16T15:36:38\",\"id_user\":1}]";
        auditActionDTOs = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), ConceptAuditActionDTO[].class);

        System.out.println(auditActionDTOs);
    }
}