package cl.minsal.semantikos.kernel.DAO.implementation;

import cl.minsal.semantikos.kernel.DAO.interfaces.ConceptDAO;
import cl.minsal.semantikos.model.ConceptSMTK;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */
public class ConceptDAOImpl implements ConceptDAO {
    @Override
    public List<ConceptSMTK> getConceptBy(String[] Pattern, String[] Category, int pageNumber, int pageSize) {

        /*
        int id, idCategory,state;
        boolean check, consult, completelyDefined, published;

        Category objectCategory = null;
        DAOCategory DAOcategory = new DAOCategoryImpl();
        DAODescription daoDescription = new DAODescriptionImpl();
        conectionBD connect = new conectionBD();

        List<Concept> concepts = new ArrayList<>();
        CallableStatement call;

        try {

            if (Pattern != null) {

                Array ArrayPattern = connect.getConnection().createArrayOf("text", Pattern);

                if (Category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by(?,?,?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", Category);

                    call.setArray(1, ArrayCategories);
                    call.setArray(2, ArrayPattern);
                    call.setInt(3,pageNumber);
                    call.setInt(4,pageSize);
                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_pattern(?,?,?)}");
                    call.setArray(1, ArrayPattern);
                    call.setInt(2,pageNumber);
                    call.setInt(3,pageSize);
                }

            } else {

                if (Category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_categories(?,?,?)}");

                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", Category);

                    call.setArray(1, ArrayCategories);
                    call.setInt(2,pageNumber);
                    call.setInt(3,pageSize);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.get_concept_by_page_size(?,?)}");
                    call.setInt(1,pageNumber);
                    call.setInt(2,pageSize);
                }
            }



            call.execute();

            ResultSet rs = call.getResultSet();

            concepts = new ArrayList<>();

            while (rs.next()) {
                id = Integer.parseInt(rs.getString("id"));
                idCategory = Integer.parseInt(rs.getString("idcategoria"));
                if (objectCategory != null) {
                    if (objectCategory.getId() != idCategory) {
                        objectCategory = DAOcategory.getCategoryBy(idCategory);
                    }
                } else {
                    objectCategory = DAOcategory.getCategoryBy(idCategory);
                }

                check = rs.getString("revisar").equalsIgnoreCase("true") ? true : false;
                consult = rs.getString("consultar").equalsIgnoreCase("true") ? true : false;
                state = Integer.parseInt(rs.getString("idestadoconcepto"));
                completelyDefined = rs.getString("completamentedefinido").equalsIgnoreCase("true") ? true : false;
                published = rs.getString("publicado").equalsIgnoreCase("true") ? true : false;
                concepts.add(new Concept(id, check, consult, state, completelyDefined, published, objectCategory));
                concepts.get(concepts.size() - 1).setDescriptionList(daoDescription.getDescriptionBy(id));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        connect.closeConnection();


        return concepts;*/

        return null;
    }

    @Override
    public int getAllConceptCount(String[] Pattern, String[] category) {

        /*
        conectionBD connect = new conectionBD();
        CallableStatement call;
        int count=0;

        try {

            if (Pattern != null) {

                Array ArrayPattern = connect.getConnection().createArrayOf("text", Pattern);

                if (category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_count_by(?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);

                    call.setArray(1, ArrayCategories);
                    call.setArray(2, ArrayPattern);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_count_by_pattern(?)}");
                    call.setArray(1, ArrayPattern);
                }

            } else {

                if (category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_count_by_categories(?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);
                    call.setArray(1, ArrayCategories);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.get_concept_count()}");
                }
            }

            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        connect.closeConnection();

        return count;*/
        return 0;
    }
}
