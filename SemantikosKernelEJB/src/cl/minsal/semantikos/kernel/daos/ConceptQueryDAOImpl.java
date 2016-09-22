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
             CallableStatement call = connection.prepareCall("{call semantikos.get_all_concepts(?,?,?)}")) {

            String sql = "select * from semantikos.smtk_concept c" +
                    "where " +
                    "c.mod ";
            CallableStatement call2 = connection.prepareCall(sql);


            call.setBoolean(1, query.getModeled());
            call.setInt(2, query.getPageNumber());
            call.setInt(3, query.getPageSize());
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



    private String makeSQLfromQuery(ConceptQuery query){







        return null;
    }



}
