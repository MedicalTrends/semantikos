package cl.minsal.semantikos.model.relationships;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * @author Andrés Farías
 */
public class RelationshipAttributeDefinitionFactory {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(RelationshipAttributeDefinitionFactory.class);

    public List<RelationshipAttributeDefinition> createFromJSON(String jsonExpression) {

        ObjectMapper mapper = new ObjectMapper();
        RelationshipAttributeDefinition[] relationshipAttributeDefinition;
        try {
            //TODO: Hacer la prueba, el test
            relationshipAttributeDefinition = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipAttributeDefinition[].class);
        } catch (IOException e) {
            String errorMsg = "Error al parsear un JSON.";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        return Arrays.asList(relationshipAttributeDefinition);
    }

    /*
                    long idRelationshipAttribute = resultSet.getLong("id_relationship_attribute_definition");
                String nameAttribute = resultSet.getString("name");
                String descriptionAttribute = rs.getString("description");

                    /* Limites de la multiplicidad
    int lowerBoundaryAttribute = resultSet.getInt("lower_boundary");
    int upperBoundaryAttribute = resultSet.getInt("upper_boundary");
    Multiplicity multiplicityAttribute = new Multiplicity(lowerBoundaryAttribute, upperBoundaryAttribute);

    /* Se recupera el target definition
    TargetDefinition targetDefinitionAttribute = getTargetDefinition(resultSet.getString("id_category"), resultSet.getString("id_accesory_table_name"), resultSet.getString("id_extern_table_name"), resultSet.getString("id_basic_type"), resultSet.getString("is_sct_type"));

    /* Se crea el objeto
    RelationshipAttributeDefinition relationshipAttributeDefinition = new RelationshipDefinition(idRelationship, name, description, targetDefinition, multiplicity);
    relationshipDefinition.getRelationshipAttributeDefinitions().add(relationshipAttributeDefinition);

    */
}
