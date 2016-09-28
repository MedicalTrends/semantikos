package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.browser.ConceptQueryFilter;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
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


        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();

        ConnectionBD connect = new ConnectionBD();


        //TODO: hacer funcion en pg
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.find_concept_by_query(?,?,?,?,?,?,?,?,?,?,?,?,?,?) " )){

            /*
               1 categories _int4,
               2 pattern text,
               3 auxiliary_targets [][],
               4 auxiliary_tables _int4,
               5 auxiliary_reldefs _int4,
               6 modeled bool,
               7 review bool,
               8 consult bool,
               9 tag_id int4,
               10 creation_date_from date,
               11 creation_date_to date,
               12 orden text,
               13 page int4,
               14 page_size int4
            */


            call.setArray(1, getArrayCategories(query, connection));
            call.setString(2,query.getQuery() );
            call.setArray(3, getArrayAuxTargets(query, connection));
            call.setArray(4, getArrayAuxTables(query, connection));
            call.setArray(5, getArrayAuxRefdefs(query, connection));
            call.setBoolean(6, query.getModeled());
            call.setBoolean(7, query.getToBeReviewed());
            call.setBoolean(8, query.getToBeConsulted());
            call.setLong(9, query.getTag().getId());
            call.setDate(10, new Date(query.getCreationDateSince().getTime()));
            call.setDate(11, new Date(query.getCreationDateTo().getTime()));
            call.setString(12, query.getOrder());
            call.setInt(13, query.getPageNumber());
            call.setInt(14, query.getPageSize());
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

        return concepts;
    }

    private Array getArrayAuxTargets(ConceptQuery query, Connection connection) throws SQLException {


        ArrayList<Integer> ids = new ArrayList<Integer>();


        int maxTargetsSize = 0;
        int filters = 0;


        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable()) {
                filters++;

                if (filter.getTargets().size()>maxTargetsSize)
                    maxTargetsSize = filter.getTargets().size();
            }
        }


        int[][] auxtargets = new int[filters][maxTargetsSize];

        int i = 0;
        for (ConceptQueryFilter filter:query.getFilters()) {

            if (filter.getDefinition().getTargetDefinition().isHelperTable()) {
                for(int j = 0; j < filter.getTargets().size(); j++){
                    auxtargets[i][j] = (int) ( ( (HelperTableRecord) (filter.getTargets().get(j)) ).getId() );
                }

                i++;

            }
        }
        return connection.createArrayOf("integer", auxtargets);
    }


    private Array getArrayAuxRefdefs(ConceptQuery query, Connection connection) throws SQLException {

        int filters = 0;

        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable()) {
                filters++;
            }
        }

        Integer[] auxtables = new Integer[filters];

        int i = 0;
        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable()) {
                auxtables[i] =  (int)   filter.getDefinition().getId()   ;
                i++;
            }
        }
        return connection.createArrayOf("integer", auxtables);
    }

    private Array getArrayAuxTables(ConceptQuery query, Connection connection) throws SQLException {

        int filters = 0;

        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable()) {
                filters++;
            }
        }

        Integer[] auxtables = new Integer[filters];

        int i = 0;
        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable()) {
                auxtables[i] =  (int) (  ((HelperTable)filter.getDefinition().getTargetDefinition()).getId()   );
                i++;
            }
        }
        return connection.createArrayOf("integer", auxtables);
    }


    private Array getArrayCategories(ConceptQuery query, Connection connection) throws SQLException {
        Long[] categorias = new Long[query.getCategories().size()];
        for(int i = 0; i<= query.getCategories().size();i++){
            categorias[i]=query.getCategories().get(i).getId();
        }
        return connection.createArrayOf("integer", categorias);
    }



}
