package cl.minsal.semantikos.kernel.daos;


import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import cl.minsal.semantikos.model.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */

@Stateless
public class RelationshipDefinitionDAOImpl implements RelationshipDefinitionDAO {

    private static final String SQL_GET_RELATIONSHIP_DEFINITIONS = "{call semantikos.get_full_category_by_id(?)}";

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    @EJB
    CategoryDAO categoryDAO;
    @EJB
    DescriptionDAO descriptionDAO;

    @Override
    public List<RelationshipDefinition> getRelationshipDefinitionsByCategory(int idCategory) {

        ConnectionBD connect = new ConnectionBD();

        ObjectMapper mapper = new ObjectMapper();

        Category category = new Category();


        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(SQL_GET_RELATIONSHIP_DEFINITIONS)) {

            call.setInt(1, idCategory);
            call.execute();
            ResultSet rs = call.getResultSet();

            while (rs.next()) {

                long id = Long.valueOf(rs.getString("id"));
                String name= String.valueOf(rs.getString("name"));
                String description = String.valueOf(rs.getString("name"));



                switch ()



                String resultJSON = rs.getString(1);
                category = mapper.readValue(StringUtils.underScoreToCamelCaseJSON(resultJSON), Category.class);

                RelationshipDefinition relationshipDefinition = new RelationshipDefinition(id, name, description, targetDefinition, Multiplicity multiplicity)
            }

            rs.close();

        } catch (SQLException e) {
            // TODO: Llevar a un log.
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return category;
    }
}
