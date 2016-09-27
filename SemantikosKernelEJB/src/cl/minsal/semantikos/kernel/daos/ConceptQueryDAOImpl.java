package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 22-09-2016.
 */
@Stateless
public class ConceptQueryDAOImpl implements ConceptQueryDAO {

    private static final Logger logger = LoggerFactory.getLogger(ConceptQueryDAOImpl.class);

    @EJB
    ConceptManager conceptManager;

    @Override
    public List<ConceptSMTK> callQuery(ConceptQuery query) {


        String sqlquery = "";

        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();

        ConnectionBD connect = new ConnectionBD();


        //TODO: hacer funcion en pg
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.find_concept_by_query(?,?,?,?,?,?,?,?,?,?,?) " )){

            /*
            1 categories _int4,
            2 pattern text,
            3 page int4,
            4 page_size int4,
            5 modeled bool,
            6 review bool,
            7 consult bool,
            8 tag_id int4,
            9 creation_date_from date,
            10 creation_date_to date,
            11 orden text
            */


            call.setArray(1, getArrayCategories(query, connection));
            call.setString(2,query.getQuery() );
            call.setInt(3, query.getPageNumber());
            call.setInt(4, query.getPageSize());
            call.setBoolean(5, query.getModeled());
            call.setBoolean(6, query.getToBeReviewed());
            call.setBoolean(7, query.getToBeConsulted());
            call.setInt(8, query.getTagId());
            call.setDate(9, new Date(query.getCreationDateSince().getTime()));
            call.setDate(10, new Date(query.getCreationDateTo().getTime()));
            call.setString(11, query.getOrder());
            call.execute();

            ResultSet rs = call.getResultSet();

            while (rs.next()) {



                ConceptSMTK recoveredConcept = conceptManager.getConceptByID( rs.getLong(1));
                concepts.add(recoveredConcept);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.delete_concept(?)}")) {

            call.setLong(1,1);
            call.execute();
        } catch (SQLException e) {
            String errorMessage = "No se pudo llamar query: " + query.toString();
            logger.error(errorMessage, e);
            throw new EJBException(errorMessage, e);
        }

        return null;
    }

    private Array getArrayCategories(ConceptQuery query, Connection connection) throws SQLException {
        Long[] categorias = new Long[query.getCategories().size()];
        for(int i = 0; i<= query.getCategories().size();i++){
            categorias[i]=query.getCategories().get(i).getId();
        }
        return connection.createArrayOf("integer", categorias);
    }


    private String makeSQLfromQuery(ConceptQuery query){







        return null;
    }



}
