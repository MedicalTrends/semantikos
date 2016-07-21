package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.kernel.daos.CategoryDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.RelationshipDefinition;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stk-des01 on 27-05-16.
 */
@Stateless
public class CategoryManagerImpl implements CategoryManagerInterface {

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    private EntityManager entityManager;

    String user = "postgres";
    String password = "1q2w3e";

    @EJB
    private CategoryDAO categoryDAO;

    @Override
    public List<RelationshipDefinition> getCategoryMetaData(int id) {
        ArrayList<RelationshipDefinition> Attributes = new ArrayList<RelationshipDefinition>();

        Query nativeQuery = this.entityManager.createNativeQuery("SELECT get_conf_rel_all()");
        List<Object[]> relationships = nativeQuery.getResultList();

        long idRelationship;
        String name;
        int multiplicity;
        String description;
        boolean required;

        for (Object[] relationship : relationships) {
            idRelationship = ((BigInteger) relationship[0]).longValue();
            name = (String) relationship[1];
            multiplicity = Integer.parseInt((String) relationship[2]);

            /* Se crea el objeto */
            //Attributes.add(new AttributeCategory(idRelationship, name, multiplicity, description, required));
        }

        return Attributes;
    }


    @Override
    public ArrayList<RelationshipDefinition> getAllDescription() {
        return null;
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
    public void addAttribute(RelationshipDefinition attributeCategory, int idCategory) {

    }

    /*
        @Override
        public void addAttribute(AttributeCategory attributeCategory, int idCategory) {

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

        }
    */
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

    @Override
    public Category getCategoryById(int id) {
        /*
        CategoryDAOImpl categoryDAO= new CategoryDAOImpl();
        return categoryDAO.getCategoryById(id);
        */
        //return categoryDAO.getCategoryById(id);
        return new Category();
    }

    @Override
    public Category getFullCategoryById(int id) {

        return categoryDAO.getFullCategoryById(id);
        //return new Category();
        //return null;
    }

    @Override
    public List<Category> getCategories() {
        return categoryDAO.getAllCategories();
    }


    /*
        @Override
        public ArrayList<RelationShipDefinition> findDescriptionByIDConcept(int id) {


            ArrayList<RelationShipDefinition> Attributes = new ArrayList<RelationShipDefinition>();

            //entityManager.createNamedQuery();


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


            return Attributes;
        }
    */

}
