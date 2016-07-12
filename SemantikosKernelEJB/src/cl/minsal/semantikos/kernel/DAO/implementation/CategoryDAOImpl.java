package cl.minsal.semantikos.kernel.DAO.implementation;

import cl.minsal.semantikos.kernel.DAO.interfaces.CategoryDAO;
import cl.minsal.semantikos.kernel.domain.Category;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by des01c7 on 01-07-16.
 */
public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public Category getCategoryById(int id) {

        ConnectionBD connect = new ConnectionBD();

        ObjectMapper mapper = new ObjectMapper();

        Category category= new Category();

        try {

            CallableStatement call = connect.getConnection().prepareCall("{call semantikos.get_category_by_id(?)}");
            call.setInt(1,id);

            call.execute();

            //ResultSetMetaData metaData = call.getMetaData();
            ResultSet rs = call.getResultSet();

            //System.out.println(metaData.toString());

            while (rs.next()) {
                String resultJSON = rs.getString(1);

                //mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                category = mapper.readValue(StringUtils.lowerCaseToCamelCaseJSON(resultJSON) , Category.class);
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

}
