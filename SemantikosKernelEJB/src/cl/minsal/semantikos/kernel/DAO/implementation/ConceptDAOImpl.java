package cl.minsal.semantikos.kernel.DAO.implementation;

import cl.minsal.semantikos.kernel.DAO.interfaces.CategoryDAO;
import cl.minsal.semantikos.kernel.DAO.interfaces.ConceptDAO;
import cl.minsal.semantikos.kernel.DAO.interfaces.DescriptionDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.State;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

        Query q;

        List<Object[]> resultList=null;
        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();


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




                q.setParameter(1,pat);
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
/*
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

*/


        return concepts;

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
