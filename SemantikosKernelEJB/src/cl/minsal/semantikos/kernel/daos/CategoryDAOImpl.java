package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
//import org.hibernate.persister.internal.PersisterClassResolverInitiator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 01-07-16.
 */

@Stateless
public class CategoryDAOImpl implements CategoryDAO {

    private static final String SQL_GET_FULL_CATEGORY = "{call semantikos.get_full_category_by_id(?)}";

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    /** El DAO para recuperar atributos de la categoría */
    @EJB
    private RelationshipDefinitionDAO relationshipDefinitionDAO;

    @Override
    public Category getCategoryById(long idCategory) {


        Category category = new Category();
        ConnectionBD connect = new ConnectionBD();
        String GET_CATEGORY_BY_ID = "{call semantikos.get_category_by_id(?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(GET_CATEGORY_BY_ID)) {

            call.setLong(1, idCategory);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                category.setIdCategory(rs.getLong("idcategory"));
                category.setName(rs.getString("namecategory"));
                category.setNameAbreviated(rs.getString("nameabbreviated"));
                category.setRestriction(rs.getBoolean("restriction"));
                category.setValid(rs.getBoolean("active"));
                category.setRelationshipDefinitions(getCategoryMetaData(idCategory));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  category;

    }

    @Override
    public List<Category> getAllCategories() {

        Query q = em.createNativeQuery("select * from semantikos.get_all_categories()");
        List<Object[]> resultList = q.getResultList();

        List<Category> respuesta = new ArrayList<Category>();

        for (Object[] result : resultList) {

            Category category = new Category();


            category.setIdCategory(((BigInteger) result[0]).longValue());
            category.setName((String) result[1]);
            category.setNameAbreviated((String) result[2]);
            category.setRestriction((boolean) result[3]);
            category.setValid((boolean) result[4]);
            respuesta.add(category);
        }

        return respuesta;

    }

    @Override
    public List<RelationshipDefinition> getCategoryMetaData(long idCategory) {
        return relationshipDefinitionDAO.getRelationshipDefinitionsByCategory(idCategory);
    }

}

