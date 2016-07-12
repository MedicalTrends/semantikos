package cl.minsal.semantikos.kernel;

import cl.minsal.semantikos.model.AttributeCategory;
import cl.minsal.semantikos.model.Category;

import javax.ejb.Stateless;
import java.util.ArrayList;

/**
 * Created by stk-des01 on 27-05-16.
 */
@Stateless
public class CategoryManagerImpl implements CategoryManagerInterface {


    String driver = "org.postgresql.Driver";
    String ruta = "jdbc:postgresql://192.168.0.221:5432/postgres";
    String user = "postgres";
    String password = "1q2w3e";

    @Override
    public ArrayList<AttributeCategory> findDescriptionByIDConcept(int id) {


        ArrayList<AttributeCategory> Attributes = new ArrayList<AttributeCategory>();


/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call get_conf_rel(?)}");

            call.setInt(1, id);
            call.execute();

            ResultSet rs = call.getResultSet();

            String idA;
            String name;
            int multiplicity;
            String description;
            boolean required;


            while (rs.next()) {
                idA = rs.getString("reltype");
                name = rs.getString("name");
                multiplicity = Integer.parseInt(rs.getString("multiplicidad"));
                description = rs.getString("idLugar");
                required = false;
                Attributes.add(new AttributeCategory(idA, name, multiplicity, description, required));

            }
            conne.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/

        return Attributes;
    }

    @Override
    public ArrayList<AttributeCategory> getAllDescription() {


        ArrayList<AttributeCategory> Attributes = new ArrayList<AttributeCategory>();


/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call get_conf_rel_all()}");
            call.execute();

            ResultSet rs = call.getResultSet();

            String idA;
            String name;
            int multiplicity;
            String description;
            boolean required;


            while (rs.next()) {
                idA = rs.getString("reltype");
                name = rs.getString("name");
                multiplicity = Integer.parseInt(rs.getString("multiplicidad"));
                description = rs.getString("idLugar");
                required = false;
                Attributes.add(new AttributeCategory(idA, name, multiplicity, description, required));
            }
            conne.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/

        return Attributes;
    }

    @Override
    public ArrayList<Category> getAllCategory() {

        ArrayList<Category> categories = new ArrayList<Category>();


/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call get_categories_all()}");
            call.execute();

            ResultSet rs = call.getResultSet();

            int idA;
            String name;
            Boolean isValid;


            while (rs.next()) {
                idA = Integer.parseInt(rs.getString("idcategoria"));
                name = rs.getString("namecategoria");
                isValid = rs.getString("isvalid").equalsIgnoreCase("true")? true: false;
                categories.add(new Category(idA, name, isValid));
            }
            conne.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/

        return categories;
    }

    @Override
    public int addCategory(Category category) {

/*
        try {

            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call crea_categoria(?)}");

            call.setString(1,category.getName());
            call.execute();

            ResultSet rs = call.getResultSet();

            int idCategory=0;

            while (rs.next()) {
                idCategory = Integer.parseInt(rs.getString(1));
            }

            for (int i = 0; i < category.getAttributeCategories().size(); i++) {
                addAttribute(category.getAttributeCategories().get(i),idCategory);
            }


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/
        return 0;
    }

    @Override
    public void addAttribute(AttributeCategory attributeCategory, int idCategory) {
/*
        try {

            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);

            if(attributeCategory.getId()==null){
                attributeCategory.setId(addTypeRelationship(attributeCategory.getName(),Integer.parseInt(attributeCategory.getType()),attributeCategory.getIdCategoryDes(),attributeCategory.getMultiplicity())+"");
            }

            CallableStatement call = conne.prepareCall("{call crea_attribute(?,?,?)}");

            call.setInt(1,idCategory);
            call.setInt(2,Integer.parseInt(attributeCategory.getId()));
            call.setInt(3,Integer.parseInt(attributeCategory.isOrder()));

            call.execute();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/
    }

    @Override
    public int addTypeRelationship(String name, int typeRelation, int idCategoryDes, int multiplicity) {

        int idTypeRelationShip = 0;

/*
        try {

            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);


            CallableStatement call = conne.prepareCall("{call crea_relation_type(?,?,?,?)}");

            call.setString(1,name);
            call.setInt(2,typeRelation);
            if(idCategoryDes!=0)
                call.setInt(3,idCategoryDes);
            else
                call.setNull(3,Types.INTEGER);
            call.setInt(4,multiplicity);

            call.execute();
            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                idTypeRelationShip = Integer.parseInt(rs.getString(1));
            }

            conne.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/

        return idTypeRelationShip;
    }


}
