package cl.minsal.semantikos.kernel.daos;


import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.State;
import cl.minsal.semantikos.model.relationships.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */

@Stateless
public class ConceptDAOImpl implements ConceptDAO {

    /** El logger de esta clase */
    private static final Logger logger = LoggerFactory.getLogger(ConceptDAOImpl.class);

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    private EntityManager em;

    @EJB
    private CategoryDAO categoryDAO;

    @EJB
    private DescriptionDAO descriptionDAO;

    /** El DAO para manejar relaciones del concepto */
    @EJB
    private RelationshipDAO relationshipDAO;


    /**
     * Este método es responsable de crear un concepto SMTK a partir de un resultset.
     *
     * @param resultSet El resultset a partir del cual se obtienen los conceptos.
     *
     * @return La lista de conceptos contenidos en el ResultSet.
     *
     * @throws SQLException Se arroja si hay un problema SQL.
     */
    private ConceptSMTK createConceptSMTKFromResultSet(ResultSet resultSet) throws SQLException {

        long id;
        long idCategory;
        Category objectCategory;
        boolean check;
        boolean consult;
        long state;
        boolean completelyDefined;
        boolean published;
        String conceptId;

        id = Long.valueOf(resultSet.getString("id"));
        idCategory = Long.valueOf(resultSet.getString("id_category"));
        objectCategory = categoryDAO.getCategoryById(idCategory);
        check = Boolean.parseBoolean(resultSet.getString("is_to_be_reviewed"));
        consult = Boolean.parseBoolean(resultSet.getString("is_to_be_consultated"));
        state = Long.valueOf(resultSet.getString("id_state_concept"));
        completelyDefined = Boolean.parseBoolean(resultSet.getString("is_fully_defined"));
        published = Boolean.parseBoolean(resultSet.getString("is_published"));
        conceptId = resultSet.getString("conceptid");
        State st = new State();
        st.setName(String.valueOf(state));
        List<Description> descriptions = descriptionDAO.getDescriptionsByConceptID(id);

        return new ConceptSMTK(id, conceptId, objectCategory, check, consult, st, completelyDefined, published, descriptions.toArray(new Description[descriptions.size()]));
    }

    @Override
    public List<ConceptSMTK> getAllConcepts(Long[] states, int pageSize, int pageNumber) {

        List<ConceptSMTK> concepts = new ArrayList<>();
        ConnectionBD connect = new ConnectionBD();
        CallableStatement call;

        try (Connection connection = connect.getConnection();) {

            call = connection.prepareCall("{call semantikos.get_all_concepts(?,?,?)}");
            call.setInt(1, pageNumber);
            call.setInt(2, pageSize);
            call.setArray(3, connection.createArrayOf("bigint", states));
            call.execute();

            ResultSet rs = call.getResultSet();

            while (rs.next()) {
                ConceptSMTK recoveredConcept = createConceptSMTKFromResultSet(rs);
                concepts.add(recoveredConcept);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return concepts;
    }

    @Override
    public List<ConceptSMTK> getConceptByCategory(Long[] categories, Long[] states, int pageSize, int pageNumber) {

        List<ConceptSMTK> concepts = new ArrayList<>();
        ConnectionBD connect = new ConnectionBD();
        CallableStatement call;

        try (Connection connection = connect.getConnection();) {

            call = connection.prepareCall("{call semantikos.find_concept_by_categories(?,?,?,?)}");

            call.setArray(1, connect.getConnection().createArrayOf("integer", categories));
            call.setInt(2, pageNumber);
            call.setInt(3, pageSize);
            call.setArray(4, connection.createArrayOf("bigint", states));

            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                ConceptSMTK e = this.createConceptSMTKFromResultSet(rs);
                concepts.add(e);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return concepts;
    }

    @Override
    public List<ConceptSMTK> getConceptByPatternCategory(String[] pattern, Long[] categories, Long[] states, int pageSize, int pageNumber) {

        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();
        ConnectionBD connect = new ConnectionBD();

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.find_concept_by_pattern_and_categories(?,?,?,?,?)}")) {

            Array ArrayCategories = connection.createArrayOf("integer", categories);

            call.setArray(1, ArrayCategories);
            call.setArray(2, connection.createArrayOf("text", pattern));
            call.setInt(3, pageNumber);
            call.setInt(4, pageSize);
            call.setArray(5, connection.createArrayOf("bigint", states));
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                ConceptSMTK conceptSMTK = createConceptSMTKFromResultSet(rs);
                concepts.add(conceptSMTK);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return concepts;
    }

    @Override
    public List<ConceptSMTK> getConceptByPatternOrConceptIDAndCategory(String PatternOrID, Long[] Category, int pageNumber, int pageSize, Long[] states) {
        long id, conceptId, idCategory, state;
        boolean check, consult, completelyDefined, published;

        Category objectCategory = null;


        List<ConceptSMTK> concepts = new ArrayList<ConceptSMTK>();

        ConnectionBD connect = new ConnectionBD();


        CallableStatement call;

        // TODO: TryWithResources
        try (Connection connection = connect.getConnection();) {

            Array ArrayStates = connection.createArrayOf("bigint", states);
            if (Category.length > 0) {
                call = connection.prepareCall("{call semantikos.find_concept_by_conceptid_categories(?,?,?,?,?)}");
                Array ArrayCategories = connection.createArrayOf("integer", Category);

                call.setString(1, PatternOrID);
                call.setArray(2, ArrayCategories);
                call.setInt(3, pageNumber);
                call.setInt(4, pageSize);
                call.setArray(5, ArrayStates);
            } else {
                call = connection.prepareCall("{call semantikos.find_concept_by_concept_id(?,?,?,?)}");
                call.setString(1, PatternOrID);
                call.setInt(2, pageNumber);
                call.setInt(3, pageSize);
                call.setArray(4, ArrayStates);
            }
            call.execute();

            ResultSet rs = call.getResultSet();
            concepts = new ArrayList<>();
            while (rs.next()) {
                concepts.add(createConceptSMTKFromResultSet(rs));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return concepts;
    }

    @Override
    public int getAllConceptCount(String[] Pattern, Long[] category, Long[] states) {


        ConnectionBD connect = new ConnectionBD();
        CallableStatement call;
        int count = 0;

        try (Connection connection = connect.getConnection();) {
            Array ArrayStates = connection.createArrayOf("bigint", states);

            if (Pattern != null) {

                Array ArrayPattern = connection.createArrayOf("text", Pattern);

                if (category.length > 0) {
                    call = connection.prepareCall("{call semantikos.count_concept_by_pattern_and_categories(?,?,?)}");
                    Array ArrayCategories = connection.createArrayOf("integer", category);

                    call.setArray(1, ArrayCategories);
                    call.setArray(2, ArrayPattern);
                    call.setArray(3, ArrayStates);

                } else {
                    call = connection.prepareCall("{call semantikos.count_concept_by_pattern(?,?)}");
                    call.setArray(1, ArrayPattern);
                    call.setArray(2, ArrayStates);
                }

            } else {

                if (category.length > 0) {
                    call = connection.prepareCall("{call semantikos.count_concept_count_by_categories(?,?)}");
                    Array ArrayCategories = connect.getConnection().createArrayOf("integer", category);
                    call.setArray(1, ArrayCategories);
                    call.setArray(2, ArrayStates);

                } else {
                    call = connection.prepareCall("{call semantikos.count_concept_by_page_size(?)}");
                    call.setArray(1, ArrayStates);
                }
            }

            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return count;
    }

    @Override
    public int getCountFindConceptID(String Pattern, Long[] category, Long[] states) {
        ConnectionBD connect = new ConnectionBD();
        int count = 0;


        String COUNT_WITH_CATEGORIES = "{call semantikos.count_concept_by_conceptid_categories(?,?,?)}";
        String COUNT_WITHOUT_CATEGORIES = "{call semantikos.count_concept_by_concept_id(?,?)}";
        String COUNT_CALL = (category.length > 0) ? COUNT_WITH_CATEGORIES : COUNT_WITHOUT_CATEGORIES;

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(COUNT_CALL)) {

            call.setString(1, Pattern);
            call.setArray(2, connection.createArrayOf("bigint", states));
            if (category.length > 0) {
                call.setArray(3, connect.getConnection().createArrayOf("integer", category));
            }

            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public ConceptSMTK getConceptByCONCEPT_ID(String conceptID) {
        ConnectionBD connect = new ConnectionBD();

        String sql = "{call semantikos.get_concept_by_conceptid(?)}";
        ConceptSMTK conceptSMTK;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setString(1, conceptID);
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                conceptSMTK = createConceptSMTKFromResultSet(rs);
            } else {
                String errorMsg = "No existe un concepto con CONCEPT_ID=" + conceptID;
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            throw new EJBException(e);
        }

        return conceptSMTK;
    }

    @Override
    public ConceptSMTK getConceptByID(long id) {
        ConnectionBD connect = new ConnectionBD();

        String sql = "{call semantikos.get_concept_by_id(?)}";
        ConceptSMTK conceptSMTK;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setLong(1, id);
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                conceptSMTK = createConceptSMTKFromResultSet(rs);
            } else {
                String errorMsg = "No existe un concepto con CONCEPT_ID=" + id;
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            throw new EJBException(e);
        }

        return conceptSMTK;
    }

    @Override
    //TODO: Revisar transaccionalidad de estas tres acciones
    public void persist(ConceptSMTK conceptSMTK) {

        /* Primero se persisten los atributos básicos del concepto */
        persistConceptBasicInfo(conceptSMTK);

        /* Luego se persisten sus descripciones */

        for (Description description : conceptSMTK.getDescriptions()) {
            descriptionDAO.persist(description, conceptSMTK);
        }


        /* Y finalmente se persisten sus relaciones */
        /*for (Relationship relationship : conceptSMTK.getRelationships()) {
            relationshipDAO.persist(relationship);
        }*/
    }

    private void persistConceptBasicInfo(ConceptSMTK conceptSMTK) {
        ConnectionBD connect = new ConnectionBD();
        long id;
        String sql = "{call semantikos.create_concept(?,?,?,?,?,?,?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setString(1, conceptSMTK.getConceptID());
            call.setLong(2,conceptSMTK.getCategory().getIdCategory());
            call.setBoolean(3, conceptSMTK.isToBeReviewed());
            call.setBoolean(4, conceptSMTK.isToBeConsulted());
            call.setLong(5, conceptSMTK.getState().getId());
            call.setBoolean(6, conceptSMTK.isFullyDefined());
            call.setBoolean(7, conceptSMTK.isPublished());
            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                /* Se recupera el ID del concepto persistido */
                id = rs.getLong(1);
                conceptSMTK.setId(id);
            } else {
                String errorMsg = "El concepto no fue creado por una razon desconocida. Alertar al area de desarrollo sobre esto";
                logger.error(errorMsg);
                throw new EJBException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            String errorMsg = "El concepto " + conceptSMTK.toString() + " no fue persistido.";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }
    }
}
