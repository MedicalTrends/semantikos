package cl.minsal.semantikos.kernel.daos;


import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */

@Stateless
public class RelationshipDefinitionDAOImpl implements RelationshipDefinitionDAO {

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    @EJB
    CategoryDAO categoryDAO;

    @EJB
    DescriptionDAO descriptionDAO;

    @EJB
    private TargetTypeDAO targetTypeDAO;

    @Override
    public List<RelationshipDefinition> getRelationshipDefinitionsByCategory(int idCategory) throws ParseException {

        ArrayList<RelationshipDefinition> attributes = new ArrayList<RelationshipDefinition>();

        /* Se invoca la consulta para recuperar las relaciones */
        Query nativeQuery = this.em.createNativeQuery("SELECT semantikos.get_relationship_definitions_by_category(?)");

        nativeQuery.setParameter(1,idCategory);

        List<Object[]> relationships = nativeQuery.getResultList();

        for (Object[] relationship : relationships) {

            long idRelationship = ((BigInteger) relationship[0]).longValue();
            String name = (String) relationship[1];
            String description = (String) relationship[2];

            /* Limites de la multiplicidad */
            int lowerBoundary = Integer.parseInt((String) relationship[3]);
            int upperBoundary = Integer.parseInt((String) relationship[4]);
            Multiplicity multiplicity = new Multiplicity(lowerBoundary, upperBoundary);

            /* Se recupera el target definition */
            TargetDefinition targetDefinition = getTargetDefinition((String)relationship[6], (String)relationship[7], (String)relationship[8], (String)relationship[9], (String)relationship[10]);

            /* Se crea el objeto */
            RelationshipDefinition relationshipDefinition = new RelationshipDefinition(idRelationship, name, description, targetDefinition, multiplicity);

            attributes.add(relationshipDefinition);
        }

        return attributes;

    }

    private TargetDefinition getTargetDefinition(String idCategory, String idAccesoryTable, String idExternTable, String idBasicType, String isSCTType) throws ParseException {

        /* Se testea si es un tipo básico */
        BasicTypeDefinition basicTypeDefinition = null;

        if (idBasicType != null) {
            long id = new BigInteger(idBasicType).longValue();
            basicTypeDefinition = targetTypeDAO.findByID(id);
        }


        return basicTypeDefinition;
    }
}
