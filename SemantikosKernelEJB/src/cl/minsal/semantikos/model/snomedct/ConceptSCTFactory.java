package cl.minsal.semantikos.model.snomedct;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.sql.Timestamp;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * @author Andrés Farías
 */
@Singleton
public class ConceptSCTFactory {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(ConceptSCTFactory.class);

    public ConceptSCT createFromJSON(@NotNull String jsonExpression){

        ConceptSCT conceptSCT;
        ObjectMapper mapper = new ObjectMapper();
        try {
            conceptSCT = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), ConceptSCT.class);
        } catch (IOException e) {
            String errorMsg = "Error al parsear un JSON hacia un ConceptSCT";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        return conceptSCT;
    }
}

