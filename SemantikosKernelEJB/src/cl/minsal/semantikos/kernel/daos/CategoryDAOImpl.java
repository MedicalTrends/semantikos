package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.TagSMTK;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Diego Soto
 */

@Singleton
public class CategoryDAOImpl implements CategoryDAO {

    static final Logger logger = LoggerFactory.getLogger(CategoryDAOImpl.class);

    /** El DAO para recuperar atributos de la categoría */
    @EJB
    private RelationshipDefinitionDAO relationshipDefinitionDAO;

    @EJB
    private TagSMTKDAO tagSMTKDAO;

    // TODO: Agregar EhCache.

    @Override
    public Category getCategoryById(long idCategory) {
        Category category;
        ConnectionBD connect = new ConnectionBD();
        String GET_CATEGORY_BY_ID = "{call semantikos.get_category_by_id(?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(GET_CATEGORY_BY_ID)) {

            call.setLong(1, idCategory);
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                category = createCategoryFromResultSet(rs);
                category.setRelationshipDefinitions(getCategoryMetaData(category.getId()));
            } else {
                throw new EJBException("Error en la llamada");
            }
            rs.close();
        } catch (SQLException e) {
            String errorMsg = "error en getCategoryById = " + idCategory;
            logger.error(errorMsg, idCategory, e);
            throw new EJBException(errorMsg, e);
        }

        return category;
    }

    @Override
    public List<Category> getAllCategories() {

        ConnectionBD connect = new ConnectionBD();
        List<Category> categories = new ArrayList<>();
        ;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("SELECT * FROM semantikos.get_all_categories()")) {
            call.execute();

            ResultSet resultSet = call.getResultSet();
            while (resultSet.next()) {
                Category categoryFromResultSet = createCategoryFromResultSet(resultSet);
                categoryFromResultSet.setRelationshipDefinitions(getCategoryMetaData(categoryFromResultSet.getId()));

                categories.add(categoryFromResultSet);
            }
        } catch (SQLException e) {
            throw new EJBException(e);
        }

        return categories;
    }

    private Category createCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        long idCategory = resultSet.getLong("idcategory");
        String nameCategory = resultSet.getString("namecategory");
        String nameAbbreviated = resultSet.getString("nameabbreviated");
        boolean restriction = resultSet.getBoolean("restriction");
        boolean active = resultSet.getBoolean("active");
        String color = resultSet.getString("nameabbreviated");

        long idTagSMTK = resultSet.getLong("tag");
        TagSMTK tagSMTKByID = tagSMTKDAO.findTagSMTKByID(idTagSMTK);

        return new Category(idCategory, nameCategory, nameAbbreviated, restriction, active, color, tagSMTKByID);
    }

    @Override
    public List<RelationshipDefinition> getCategoryMetaData(long idCategory) {
        return relationshipDefinitionDAO.getRelationshipDefinitionsByCategory(idCategory);
    }

    @Override
    public void persist(Category category) {
        ConnectionBD connect = new ConnectionBD();
        String CREATE_CATEGORY = "{call semantikos.create_category(?, ?, ?, ?, ?, ?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(CREATE_CATEGORY)) {

            call.setString(1, category.getName());
            call.setString(2, category.getNameAbbreviated());
            call.setBoolean(3, category.isRestriction());
            call.setLong(4, category.getTagSemantikos().getId());
            call.setBoolean(5, category.isValid());
            call.setString(6, category.getColor());
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                category.setId(rs.getLong(1));
            }
            rs.close();

        } catch (SQLException e) {
            logger.error("Error al crear la categoría:" + category, e);

        }
    }
}

