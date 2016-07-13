package cl.minsal.semantikos.kernel.DAO.implementation;

import cl.minsal.semantikos.kernel.DAO.interfaces.CategoryDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public List<Category> getAllCategories() {

        /*
        *
        * List<Category> categories= new ArrayList<>();
        conectionBD connect = new conectionBD();


        try {

            CallableStatement call = connect.getConnection().prepareCall("{call semantikos.get_all_categories()}");
            call.execute();

            ResultSet rs = call.getResultSet();

            int id;
            String name,nameA;
            Boolean restriction,valid;


            while (rs.next()) {
                id = Integer.parseInt(rs.getString("idcategory"));
                name = rs.getString("namecategory");
                nameA= rs.getString("nameabbreviated");
                restriction = rs.getString("restriction").equalsIgnoreCase("true")? true: false;
                valid=rs.getString("active").equalsIgnoreCase("true")? true: false;

                categories.add(new Category(id,name,nameA,restriction,valid));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        connect.closeConnection();

        return categories;
        *
        * */

        return null;
    }

}
