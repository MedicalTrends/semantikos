package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.kernel.daos.BasicTypeDAO;
import cl.minsal.semantikos.kernel.daos.ConceptSCTDAO;
import cl.minsal.semantikos.kernel.daos.RelationshipDAO;
import cl.minsal.semantikos.kernel.daos.RelationshipDefinitionDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * @author Andrés Farías
 */
@Singleton
public class RelationshipFactory {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(RelationshipFactory.class);

    @EJB
    private ConceptManagerInterface conceptDAO;

    @EJB
    private ConceptSCTDAO conceptSCTDAO;

    @EJB
    private BasicTypeDAO basicTypeDAO;

    @EJB
    private RelationshipDAO relationshipDAO;

    @EJB
    private RelationshipDefinitionDAO relationshipDefinitionDAO;

    public Relationship createFromJSON(String jsonExpression) throws EJBException {

        /* Transformar el JSON a DTO primero */
        RelationshipDTO relationshipDTO = parseRelationshipFromJSON(jsonExpression);

        //FIXME
    return null;
    }

    private RelationshipDTO parseRelationshipFromJSON(String jsonExpression) {
        ObjectMapper mapper = new ObjectMapper();
        RelationshipDTO relationshipDTO;
        try {
            relationshipDTO = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipDTO.class);
        } catch (IOException e) {
            String errorMsg = "Error al transformar de JSON a RelationshipDTO.";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }
        return relationshipDTO;
    }

    /**
     * Este método es responsable de parsear una expresion JSON a una lista de Relaciones.
     *
     * @param jsonExpression La expresion JSON del tipo:
     *
     * @return Una lista de relaciones creadas a partir de la expresión.
     */
    public List<Relationship> createRelationshipsFromJSON(String jsonExpression) {

        /* Se parsea la expresion JSON */
        ObjectMapper mapper = new ObjectMapper();
        RelationshipDTO[] relationshipDTOs;
        try {
            relationshipDTOs = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), RelationshipDTO[].class);
        } catch (IOException e) {
            String errorMsg = "Error when parsing a JSON a Relationships";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        /* Y se recuperan las entidades relacionadas */
        for (RelationshipDTO relationshipDTO : relationshipDTOs) {
            Relationship relationship = createRelationshipFromDTO(relationshipDTO);
        }

        /* Se retorna como una lista */
        Relationship relationships = null;
        return Arrays.asList(relationships);
    }

    /**
     * Este método es responsable de crear la entidad completa a partir del DTO.
     *
     * @param relationshipDTO El DTO a partir del cual se crea la relación.
     *
     * @return Una relación fresca creada a partir del DTO.
     */
    private Relationship createRelationshipFromDTO(RelationshipDTO relationshipDTO) {

        /* Se crea la relación a partir de esos datos */
        ConceptSMTK sourceConceptSMTK = conceptDAO.getConceptByID(relationshipDTO.idSourceConcept);
        RelationshipDefinition relationshipDefinition = relationshipDefinitionDAO.getRelationshipDefinitionByID(relationshipDTO.idRelationshipDefinition);

        Target target = null;
        if (relationshipDefinition.getTargetDefinition().isBasicType()) {
            target = basicTypeDAO.getBasicTypeValueByID(relationshipDTO.idTarget);
        } else if (relationshipDefinition.getTargetDefinition().isHelperTable()) {

        } else if (relationshipDefinition.getTargetDefinition().isSMTKType()) {
        } else if (relationshipDefinition.getTargetDefinition().isSnomedCTType()) {
        } else if (relationshipDefinition.getTargetDefinition().isCrossMapType()){}

            //FIXME: Esto esta malo. Reparar.


        //TODO: revisar si estoo esta bien
        return new Relationship(relationshipDTO.getId(), sourceConceptSMTK, target, relationshipDefinition, relationshipDTO.validityUntil);
    }

}

class RelationshipDTO {

    protected long id;
    protected long idSourceConcept;
    protected long idTarget;
    protected long idRelationshipDefinition;
    protected Timestamp validityUntil;

    public RelationshipDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSourceConcept() {
        return idSourceConcept;
    }

    public void setIdSourceConcept(long idSourceConcept) {
        this.idSourceConcept = idSourceConcept;
    }

    public long getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(long idTarget) {
        this.idTarget = idTarget;
    }

    public long getIdRelationshipDefinition() {
        return idRelationshipDefinition;
    }

    public void setIdRelationshipDefinition(long idRelationshipDefinition) {
        this.idRelationshipDefinition = idRelationshipDefinition;
    }

    public Timestamp getValidityUntil() {
        return validityUntil;
    }

    public void setValidityUntil(Timestamp validityUntil) {
        this.validityUntil = validityUntil;
    }


}
