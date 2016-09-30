package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.browser.ConceptQueryFilter;
import cl.minsal.semantikos.model.browser.ConceptQueryParameter;
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
             CallableStatement call = connection.prepareCall("{call semantikos.find_concept_by_query(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" )){

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

            if(query.getModeled()==null)
                call.setNull(6, Types.BOOLEAN );
            else
                call.setBoolean(6, query.getModeled());

            if(query.getToBeReviewed()==null)
                call.setNull(7, Types.BOOLEAN );
            else
                call.setBoolean(7, query.getToBeReviewed());


            if(query.getToBeConsulted()==null)
                call.setNull(8, Types.BOOLEAN );
            else
                call.setBoolean(8, query.getToBeConsulted());

            if(query.getTag()==null)
                call.setNull(9, Types.INTEGER );
            else
                call.setLong(9, query.getTag().getId());

            if (query.getCreationDateSince()==null)
                call.setNull(10, Types.DATE );
            else
                call.setDate(10, new Date(query.getCreationDateSince().getTime()));

            if(query.getCreationDateTo()==null)
                call.setNull(11, Types.DATE );
            else
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

    @Override
    public List<ConceptSMTK> executeQuery(ConceptQuery query) {

        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();

        ConnectionBD connect = new ConnectionBD();

        //TODO: hacer funcion en pg
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.get_concept_by_query(?,?,?,?,?,?,?,?,?,?,?,?,?)}" )){

            /*
                1. p_id_category integer, --static
                2. p_pattern text, --static
                3. p_modeled boolean, --static
                4. p_review boolean, --static
                5. p_consult boolean, --static
                6. p_tag_id integer, --static
                7. p_basic_type_values text[], --dynamic
                8. p_helper_table_records integer[], --dynamic
                9. p_creation_date_from date, --dynamic
                10. p_creation_date_to date, --dynamic
                11. p_orden text, --static
                12. p_page integer, --static
                13. p_page_size integer --static
            */
            bindParameter(1, call, connect.getConnection(), query.getCategoryValues());
            bindParameter(2, call, connect.getConnection(), query.getQuery());
            bindParameter(3, call, connect.getConnection(), query.getModeled());
            bindParameter(4, call, connect.getConnection(), query.getToBeReviewed());
            bindParameter(5, call, connect.getConnection(), query.getToBeConsulted());
            bindParameter(6, call, connect.getConnection(), query.getTag());
            bindParameter(7, call, connect.getConnection(), query.getBasicTypeValues());
            bindParameter(8, call, connect.getConnection(), query.getHelperTableValues());
            bindParameter(9, call, connect.getConnection(), query.getCreationDateSince());
            bindParameter(10, call, connect.getConnection(), query.getCreationDateTo());
            bindParameter(11, call, connect.getConnection(), query.getOrder());
            bindParameter(12, call, connect.getConnection(), query.getPageNumber());
            bindParameter(13, call, connect.getConnection(), query.getPageSize());
            //bindParameter();

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
            if (filter.getDefinition().getTargetDefinition().isHelperTable() && filter.getTargets().size()>0) {
                filters++;

                       if (filter.getTargets().size()>maxTargetsSize)
                            maxTargetsSize = filter.getTargets().size();
            }
        }

        if(filters == 0 || maxTargetsSize == 0)
            return  connection.createArrayOf("integer", new Integer[0]);

        int[][] auxtargets = new int[filters][maxTargetsSize];

        int i = 0;
        for (ConceptQueryFilter filter:query.getFilters()) {

            if (filter.getDefinition().getTargetDefinition().isHelperTable() && filter.getTargets().size()>0) {
                for(int j = 0; j < filter.getTargets().size(); j++){
                    auxtargets[i][j] = (int) ( ( (HelperTableRecord) (filter.getTargets().get(j)) ).getId() );
                }

                i++;

            }
        }


        return connection.createArrayOf("integer", auxtargets);
    }


    private Array getArrayAuxRefdefs(ConceptQuery query, Connection connection) throws SQLException {

        int filters = getValidFilterNumber(query);

        Integer[] auxtables = new Integer[filters];

        int i = 0;
        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable() && filter.getTargets().size()>0) {
                auxtables[i] =  (int)   filter.getDefinition().getId()   ;
                i++;
            }
        }
        return connection.createArrayOf("integer", auxtables);
    }

    private int getValidFilterNumber(ConceptQuery query) {
        int filters = 0;

        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable() && filter.getTargets().size()>0) {
                filters++;
            }
        }
        return filters;
    }

    private Array getArrayAuxTables(ConceptQuery query, Connection connection) throws SQLException {

        int filters = getValidFilterNumber(query);

        Integer[] auxtables = new Integer[filters];

        int i = 0;
        for (ConceptQueryFilter filter:query.getFilters()) {
            if (filter.getDefinition().getTargetDefinition().isHelperTable() && filter.getTargets().size()>0) {
                auxtables[i] =  (int) (  ((HelperTable)filter.getDefinition().getTargetDefinition()).getId()   );
                i++;
            }
        }
        return connection.createArrayOf("integer", auxtables);
    }


    private Array getArrayCategories(ConceptQuery query, Connection connection) throws SQLException {
        Long[] categorias = new Long[query.getCategories().size()];
        for(int i = 0; i < query.getCategories().size();i++){
            categorias[i]=query.getCategories().get(i).getId();
        }
        return connection.createArrayOf("integer", categorias);
    }


    private void bindParameter(int paramNumber, CallableStatement call, Connection connection, ConceptQueryParameter param) throws SQLException {


        System.out.println(object.getClass().getName());

        if(param.getValue() == null)
            if(param.getType().)

        if(param.getValue() instanceof String[]){
            if(param.getValue() == null)
                call.setNull(paramNumber, Types.ARRAY);
            else
                call.setArray(paramNumber, connection.createArrayOf("text", (String[])object));
        }

        if(object instanceof Long[]){
            if(object == null)
                call.setNull(paramNumber, Types.ARRAY);
            else
                call.setArray(paramNumber, connection.createArrayOf("bigint", (Long[])object));
        }

        if(object instanceof String){
            if(object == null)
                call.setNull(paramNumber, Types.VARCHAR);
            else
                call.setString(paramNumber, object.toString());
        }

        if(object instanceof Boolean){
            if(object == null)
                call.setNull(paramNumber, Types.BOOLEAN);
            else
                call.setBoolean(paramNumber, (Boolean)object);
        }


        if(object instanceof Date){
            if(object == null)
                call.setNull(paramNumber, Types.TIMESTAMP);
            else
                call.setTimestamp(paramNumber, (Timestamp)object);
        }

        if(object instanceof Tag){
            if(object == null)
                call.setNull(paramNumber, Types.BIGINT );
            else
                call.setLong(paramNumber, ((Tag) object).getId());
        }

    }
}
