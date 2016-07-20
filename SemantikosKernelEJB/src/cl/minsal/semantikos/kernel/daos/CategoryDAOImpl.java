package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 01-07-16.
 */

@Stateless
public class CategoryDAOImpl implements CategoryDAO {


    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    @Override
    public Category getCategoryById(long id) {


        Query q = em.createNativeQuery("select * from semantikos.get_category_by_id(?)");
        q.setParameter(1,id);


        List<Object[]> resultList = q.getResultList();
        Category category=null;

        for (Object[] result:resultList) {
            category = new Category();


            category.setIdCategory( ((BigInteger)result[0]).longValue());
            category.setName((String) result[1]);
            category.setNameAbreviated((String) result[2]);
            category.setRestriction((boolean) result[3]);
            category.setValid((boolean) result[4]);

        }


        return category;
    }

    @Override
    public Category getFullCategoryById(long id) {

        ConnectionBD connect = new ConnectionBD();

        ObjectMapper mapper = new ObjectMapper();

        Category category= new Category();

        try {

            CallableStatement call = connect.getConnection().prepareCall("{call semantikos.get_full_category_by_id(?)}");
            call.setInt(1,(int)id);

            call.execute();

            //ResultSetMetaData metaData = call.getMetaData();
            ResultSet rs = call.getResultSet();

            //System.out.println(metaData.toString());

            while (rs.next()) {
                String resultJSON = rs.getString(1);

                //mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                category = mapper.readValue(StringUtils.underScoreToCamelCaseJSON(resultJSON) , Category.class);
            }


            //String result = call.getString(0);

            //System.out.println(result);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connect.closeConnection();

        return category;
    }

    @Override
    public List<Category> getAllCategories() {

        Query q = em.createNativeQuery("select * from semantikos.get_all_categories()");
        List<Object[]> resultList = q.getResultList();

        List<Category> respuesta = new ArrayList<Category>();

        for (Object[] result:resultList) {

            Category category = new Category();


            category.setIdCategory( ((BigInteger)result[0]).longValue());
            category.setName((String) result[1]);
            category.setNameAbreviated((String) result[2]);
            category.setRestriction((boolean) result[3]);
            category.setValid((boolean) result[4]);
            respuesta.add(category);
        }

        return respuesta;
    }

}

