package cl.minsal.semantikos.kernel.daos;


import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<RelationshipDefinition> getRelationshipDefinitionsByCategory(long idCategory) {

        ArrayList<RelationshipDefinition> attributes = new ArrayList<RelationshipDefinition>();

        ConnectionBD connect = new ConnectionBD();
        String GET_RELATIONSHIP_DEFINITIONS_BY_ID_CATEGORY = "{call semantikos.get_relationship_definitions_by_category(?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(GET_RELATIONSHIP_DEFINITIONS_BY_ID_CATEGORY)) {

            /* Se invoca la consulta para recuperar las relaciones */

            call.setLong(1, idCategory);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {

                long idRelationship = rs.getLong("id_relationship_definition");
                String name = rs.getString("name");
                String description = rs.getString("description");

                /* Limites de la multiplicidad */
                int lowerBoundary = rs.getInt("multiplicity_from");
                int upperBoundary = rs.getInt("multiplicity_to");
                Multiplicity multiplicity = new Multiplicity(lowerBoundary, upperBoundary);

                /* Se recupera el target definition */
                TargetDefinition targetDefinition = getTargetDefinition(rs.getString("id_category"), rs.getString("id_accesory_table_name"), rs.getString("id_extern_table_name"), rs.getString("id_basic_type"), rs.getString("is_sct_type"));

                 /* Se crea el objeto */
                RelationshipDefinition relationshipDefinition = new RelationshipDefinition(idRelationship, name, description, targetDefinition, multiplicity);
                attributes.add(relationshipDefinition);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attributes;

    }

    private TargetDefinition getTargetDefinition(String idCategory, String idAccesoryTable, String idExternTable, String idBasicType, String isSCTType) {

        /* Se testea si es un tipo básico */
        BasicTypeDefinition basicTypeDefinition ;
        Category smtkTypeDefinition ;

        if (idBasicType != null) {
            long id = new BigInteger(idBasicType).longValue();
            basicTypeDefinition = targetTypeDAO.findByID(id);
            return basicTypeDefinition;
        }
        if(idCategory!=null){
            long id = new BigInteger(idCategory).longValue();
            smtkTypeDefinition = categoryDAO.getCategoryById(id);
            return smtkTypeDefinition;
        }

        return null;


    }
}
