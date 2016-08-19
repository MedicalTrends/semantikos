package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.kernel.daos.TargetDefinitionDAO;
import cl.minsal.semantikos.model.Multiplicity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * @author Andrés Farías
 */
@Singleton
public class RelationshipAttributeDefinitionFactory {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(RelationshipAttributeDefinitionFactory.class);

    @EJB
    private TargetDefinitionDAO targetDefinitionDAO;

    public List<RelationshipAttributeDefinition> createFromJSON(String jsonExpression) {

        ObjectMapper mapper = new ObjectMapper();
        RelationshipAttributeDefinitionDTO[] relationshipAttributeDefinitionDTOs;
        try {
            relationshipAttributeDefinitionDTOs = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipAttributeDefinitionDTO[].class);
        } catch (IOException e) {
            String errorMsg = "Error al parsear un JSON.";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        return createFromDTO(relationshipAttributeDefinitionDTOs);
    }

    /**
     * Este método es responsable de crear una entidad de negocio completa a partir del DTO.
     *
     * @param relationshipAttributeDefinitionDTOs El DTO que contiene los identificadores de las entidades
     *                                            relacionadas.
     *
     * @return Una lista con los atributos creados a partir del DTO.
     */
    private List<RelationshipAttributeDefinition> createFromDTO(RelationshipAttributeDefinitionDTO[] relationshipAttributeDefinitionDTOs) {
        List<RelationshipAttributeDefinition> attributes = new ArrayList<>();
        for (RelationshipAttributeDefinitionDTO attributeDTO : relationshipAttributeDefinitionDTOs) {

            /* Se recuperan las entidades relacionadas */
            TargetDefinition targetDefinition = targetDefinitionDAO.getTargetDefinitionById(attributeDTO.getIdTargetDefinition());
            Multiplicity multiplicity = new Multiplicity(attributeDTO.getLowerBoundary(), attributeDTO.getUpperBoundary());
            RelationshipAttributeDefinition attribute = new RelationshipAttributeDefinition(attributeDTO.getId(), targetDefinition, attributeDTO.getName(), multiplicity);

            attributes.add(attribute);
        }

        return attributes;
    }

}

class RelationshipAttributeDefinitionDTO {

    private long id;
    private long idTargetDefinition;
    private String name;
    private int lowerBoundary;
    private int upperBoundary;

    public RelationshipAttributeDefinitionDTO() {
    }

    public long getIdTargetDefinition() {
        return idTargetDefinition;
    }

    public void setIdTargetDefinition(long idTargetDefinition) {
        this.idTargetDefinition = idTargetDefinition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLowerBoundary() {
        return lowerBoundary;
    }

    public void setLowerBoundary(int lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }

    public int getUpperBoundary() {
        return upperBoundary;
    }

    public void setUpperBoundary(int upperBoundary) {
        this.upperBoundary = upperBoundary;
    }
}
