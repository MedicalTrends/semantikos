package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.RelationshipDefinition;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.hibernate.persister.internal.PersisterClassResolverInitiator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
    public Category getCategoryById(long idCategory) throws ParseException {

        // TODO: Cambiar esto a SQL JDBC
        Query nativeQuery = em.createNativeQuery("SELECT * FROM semantikos.get_category_by_id(?)");
        nativeQuery.setParameter(1,idCategory);

        List<Object[]> resultList = nativeQuery.getResultList();
        Category category=null;

        for (Object[] result:resultList) {
            category = new Category();


            category.setIdCategory( ((BigInteger)result[0]).longValue());
            category.setName((String) result[1]);
            category.setNameAbreviated((String) result[2]);
            category.setRestriction((boolean) result[3]);
            category.setValid((boolean) result[4]);

            category.setRelationshipDefinitions(getCategoryMetaData((int) idCategory));

            return  category;

        }
        category.setRelationshipDefinitions(getCategoryMetaData((int) idCategory));

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
    public List<RelationshipDefinition> getCategoryMetaData(int idCategory) throws ParseException {
        return relationshipDefinitionDAO.getRelationshipDefinitionsByCategory(idCategory);
    }

}

