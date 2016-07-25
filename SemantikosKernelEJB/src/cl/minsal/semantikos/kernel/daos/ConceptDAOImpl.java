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
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */

@Stateless
public class ConceptDAOImpl implements ConceptDAO {


    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    @EJB
    CategoryDAO categoryDAO;
    @EJB
    DescriptionDAO descriptionDAO;


    @Override
    public List<ConceptSMTK> getConceptByPatternCategory(String[] Pattern, String[] Category, int pageNumber, int pageSize, Long[] states) {


        long id, conceptId, idCategory,state;
        boolean check, consult, completelyDefined, published;

        Category objectCategory = null;


        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();

        ConnectionBD connect= new ConnectionBD();


        CallableStatement call;

        try {
            Array ArrayStates = connect.getConnection().createArrayOf("bigint",  states);

            if (Pattern != null) {


                Array ArrayPattern = connect.getConnection().createArrayOf("text", Pattern);

                if (Category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_pattern_and_categories(?,?,?,?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", Category);

                    call.setArray(1, ArrayCategories);
                    call.setArray(2, ArrayPattern);
                    call.setInt(3,pageNumber);
                    call.setInt(4,pageSize);
                    call.setArray(5,ArrayStates);
                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_pattern(?,?,?,?)}");
                    call.setArray(1, ArrayPattern);
                    call.setInt(2,pageNumber);
                    call.setInt(3,pageSize);
                    call.setArray(4,ArrayStates);
                }

            } else {

                if (Category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_categories(?,?,?,?)}");

                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", Category);

                    call.setArray(1, ArrayCategories);
                    call.setInt(2,pageNumber);
                    call.setInt(3,pageSize);
                    call.setArray(4,ArrayStates);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_page_size(?,?,?)}");
                    call.setInt(1,pageNumber);
                    call.setInt(2,pageSize);
                    call.setArray(3,ArrayStates);
                }
            }



            call.execute();

            ResultSet rs = call.getResultSet();

            concepts = new ArrayList<>();

            while (rs.next()) {
                id = Long.valueOf(rs.getString("id"));

                idCategory = Long.valueOf(rs.getString("id_category"));

                if (objectCategory != null) {
                    if (objectCategory.getIdCategory() != idCategory) {
                        objectCategory = categoryDAO.getCategoryById(idCategory);
                    }
                } else {
                    objectCategory = categoryDAO.getCategoryById(idCategory);
                }

                check = Boolean.parseBoolean(rs.getString("is_to_be_reviewed"));
                consult =  Boolean.parseBoolean(rs.getString("is_to_be_consultated"));
                state = Long.valueOf(rs.getString("id_state_concept"));
                completelyDefined =  Boolean.parseBoolean(rs.getString("is_fully_defined"));
                published =  Boolean.parseBoolean(rs.getString("is_published"));
                conceptId= Long.valueOf(rs.getString("conceptid"));
                State st = new State();
                st.setName(String.valueOf(state));
                List<Description> descriptions= descriptionDAO.getDescriptionByConceptID(id);
                concepts.add(new ConceptSMTK(id,conceptId,objectCategory, check, consult, st, completelyDefined, published, descriptions));


            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        connect.closeConnection();
        return concepts;
    }

    @Override
    public List<ConceptSMTK> getConceptByPatternOrConceptIDAndCategory(String PatternOrID, String[] Category, int pageNumber, int pageSize, Long[] states) {
        long id,conceptId, idCategory,state;
        boolean check, consult, completelyDefined, published;

        Category objectCategory = null;


        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();

        ConnectionBD connect= new ConnectionBD();


        CallableStatement call;


        try {

            Array ArrayStates = connect.getConnection().createArrayOf("bigint",  states);

            if (Category != null) {
                call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_conceptid_categories(?,?,?,?,?)}");
                Array ArrayCategories = connect.getConnection().createArrayOf("integer", Category);


                call.setString(1, PatternOrID);
                call.setArray(2, ArrayCategories);
                call.setInt(3,pageNumber);
                call.setInt(4,pageSize);
                call.setArray(5, ArrayStates);
            } else {
                call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_concept_id(?,?,?,?)}");
                call.setString(1, PatternOrID);
                call.setInt(2,pageNumber);
                call.setInt(3,pageSize);
                call.setArray(4, ArrayStates);
            }


            call.execute();

            ResultSet rs = call.getResultSet();

            concepts = new ArrayList<>();

            while (rs.next()) {
                id = Long.valueOf(rs.getString("id"));
                conceptId= Long.valueOf(rs.getString("conceptid"));
                idCategory = Long.valueOf(rs.getString("id_category"));

                if (objectCategory != null) {
                    if (objectCategory.getIdCategory() != idCategory) {
                        objectCategory = categoryDAO.getCategoryById(idCategory);
                    }
                } else {
                    objectCategory = categoryDAO.getCategoryById(idCategory);
                }

                check = Boolean.parseBoolean(rs.getString("is_to_be_reviewed"));
                consult =  Boolean.parseBoolean(rs.getString("is_to_be_consultated"));
                state = Long.valueOf(rs.getString("id_state_concept"));
                completelyDefined =  Boolean.parseBoolean(rs.getString("is_fully_defined"));
                published =  Boolean.parseBoolean(rs.getString("is_published"));
                State st = new State();
                st.setName(String.valueOf(state));
                List<Description> descriptions= descriptionDAO.getDescriptionByConceptID(id);
                concepts.add(new ConceptSMTK(id,conceptId,objectCategory, check, consult, st, completelyDefined, published, descriptions));


            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        connect.closeConnection();


        return concepts;
    }

    @Override
    public int getAllConceptCount(String[] Pattern, String[] category, Long[] states) {


        ConnectionBD connect = new ConnectionBD();
        CallableStatement call;
        int count=0;

        try {
            Array ArrayStates = connect.getConnection().createArrayOf("bigint",  states);

            if (Pattern != null) {

                Array ArrayPattern = connect.getConnection().createArrayOf("text", Pattern);

                if (category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_pattern_and_categories(?,?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);

                    call.setArray(1, ArrayCategories);
                    call.setArray(2, ArrayPattern);
                    call.setArray(3,ArrayStates);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_pattern(?,?)}");
                    call.setArray(1, ArrayPattern);
                    call.setArray(2,ArrayStates);
                }

            } else {

                if (category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_count_by_categories(?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);
                    call.setArray(1, ArrayCategories);
                    call.setArray(2,ArrayStates);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_page_size(?)}");
                    call.setArray(1,ArrayStates);
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

        return count;
    }

    @Override
    public int getCountFindConceptID(String Pattern, String[] category,Long[] states) {
        ConnectionBD connect = new ConnectionBD();
        CallableStatement call=null;
        int count=0;

        try {

            Array ArrayStates = connect.getConnection().createArrayOf("bigint",  states);

            if (Pattern != null) {

                if (category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_conceptid_categories(?,?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);
                    call.setString(1, Pattern);
                    call.setArray(2, ArrayCategories);
                    call.setArray(3,ArrayStates);


                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_concept_id(?,?)}");
                    call.setString(1, Pattern);
                    call.setArray(2,ArrayStates);
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

        return count;
    }


}
