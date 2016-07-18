package cl.minsal.semantikos.kernel.daos;


import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.State;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<ConceptSMTK> getConceptBy(String[] Pattern, String[] Category, int pageNumber, int pageSize) {


        long id, idCategory,state;
        boolean check, consult, completelyDefined, published;

        Category objectCategory = null;


        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();

        ConnectionBD connect= new ConnectionBD();


        CallableStatement call;

        try {

            if (Pattern != null) {

                Array ArrayPattern = connect.getConnection().createArrayOf("text", Pattern);

                if (Category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_pattern_and_categories(?,?,?,?)}");
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
                    call = connect.getConnection().prepareCall("{call semantikos.find_concept_by_page_size(?,?)}");
                    call.setInt(1,pageNumber);
                    call.setInt(2,pageSize);
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
                State st = new State();
                st.setName(String.valueOf(state));
                List<Description> descriptions= descriptionDAO.getDescriptionByConceptID(id);
                concepts.add(new ConceptSMTK(id,id,objectCategory, check, consult, st, completelyDefined, published, descriptions));


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        connect.closeConnection();


        return concepts;

/* ******************************Código con JPA*************************
        Query q;

        List<Object[]> resultList=null;
        if (Pattern != null) {


            if (Category != null) {

                q = em.createNativeQuery("select * from semantikos.find_concept_by(?,?,?,?)");

                q.setParameter(1, Category);
                q.setParameter(2, Pattern);
                q.setParameter(3,pageNumber);
                q.setParameter(4,pageSize);

            } else {

                q = em.createNativeQuery("select * from semantikos.find_concept_by_pattern(?,?,?)");


                ArrayList<String> p = new ArrayList<>();
                Object[] op= new Object[Pattern.length];
                Collection<String> pat= new ArrayList<>();

                for (int i = 0; i < Pattern.length; i++) {
                    p.add(Pattern[i]);
                    pat.add(Pattern[i]);
                    op[i]=Pattern[i];
                }




                q.setParameter(1, p);
                q.setParameter(2,pageNumber);
                q.setParameter(3,pageSize);

            }

        } else {

            if (Category != null) {
                System.out.println("Category");

                q = em.createNativeQuery("select * from semantikos.find_concept_by_categories(?,?,?)");

                Integer[] cat= new Integer[Category.length];
                for (int i = 0; i < Category.length; i++) {
                    cat[i]=Integer.parseInt(Category[i]);
                }
                IntegerArray[] cat2;
                q.setParameter(1, cat);
                q.setParameter(2,pageNumber);
                q.setParameter(3,pageSize);

            } else {


                q=em.createNativeQuery("select * from semantikos.get_concept_by_page_size(?,?)");

                q.setParameter(1,pageNumber);
                q.setParameter(2,pageSize);



            }
        }

        resultList = q.getResultList();

        for (Object[] result:resultList) {

            id = ((BigInteger)result[0]).longValue();
            idCategory = ((BigInteger)result[1]).longValue();
            if (objectCategory != null) {
                if (objectCategory.getIdCategory() != idCategory) {
                    objectCategory = categoryDAO.getCategoryById(idCategory);
                }
            } else {
                objectCategory = categoryDAO.getCategoryById(idCategory);
            }

            check = (boolean)result[3];
            consult = (boolean)result[4];
            state = ((BigInteger)result[2]).intValue();
            completelyDefined = (boolean)result[5];
            published = (boolean)result[6];
            State st = new State();
            List<Description> descriptions= descriptionDAO.getDescriptionByConceptID(id);
            concepts.add(new ConceptSMTK(id,id,objectCategory, check, consult, st, completelyDefined, published, descriptions));


        }

         ****************************** Fin Código con JPA***********/
    }

    @Override
    public int getAllConceptCount(String[] Pattern, String[] category) {


        ConnectionBD connect = new ConnectionBD();
        CallableStatement call;
        int count=0;

        try {

            if (Pattern != null) {

                Array ArrayPattern = connect.getConnection().createArrayOf("text", Pattern);

                if (category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_pattern_and_categories(?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);

                    call.setArray(1, ArrayCategories);
                    call.setArray(2, ArrayPattern);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_pattern(?)}");
                    call.setArray(1, ArrayPattern);
                }

            } else {

                if (category != null) {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_count_by_categories(?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);
                    call.setArray(1, ArrayCategories);

                } else {
                    call = connect.getConnection().prepareCall("{call semantikos.count_concept_by_page_size()}");
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
