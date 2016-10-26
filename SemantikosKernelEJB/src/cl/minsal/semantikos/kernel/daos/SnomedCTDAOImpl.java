package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;
import cl.minsal.semantikos.model.snomedct.DescriptionSCT;
import cl.minsal.semantikos.model.snomedct.DescriptionSCTType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cl.minsal.semantikos.model.snomedct.DescriptionSCTType.FSN;
import static cl.minsal.semantikos.model.snomedct.DescriptionSCTType.SYNONYM;

/**
 * @author Andrés Farías on 10/25/16.
 */
@Stateless
public class SnomedCTDAOImpl implements SnomedCTDAO {

    private static final Logger logger = LoggerFactory.getLogger(SnomedCTDAOImpl.class);

    @Override
    public List<ConceptSCT> findConceptsBy(String pattern) {
        List<ConceptSCT> concepts = new ArrayList<>();

        ConnectionBD connect = new ConnectionBD();
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.find_sct_by_pattern(?)}")) {

            call.setString(1, pattern);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                ConceptSCT recoveredConcept = createConceptSCTFromResultSet(rs);
                concepts.add(recoveredConcept);
            }
            rs.close();

        } catch (SQLException e) {
            String errorMsg = "Error al buscar Snomed CT";
            logger.error(errorMsg);
            throw new EJBException(errorMsg, e);
        }

        return concepts;
    }

    @Override
    public ConceptSCT getConceptByID(long conceptID) {

        ConnectionBD connect = new ConnectionBD();
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.get_cst_by_concept_id(?)}")) {

            call.setLong(1, conceptID);
            call.execute();

            ResultSet rs = call.getResultSet();
            ConceptSCT conceptSCTFromResultSet;
            if (rs.next()) {
                conceptSCTFromResultSet = createConceptSCTFromResultSet(rs);
            } else {
                throw new EJBException("No se encontró un concepto con ID=" + conceptID);
            }
            rs.close();

            return conceptSCTFromResultSet;
        } catch (SQLException e) {
            String errorMsg = "Error al buscar Snomed CT por CONCEPT_ID: " + conceptID;
            logger.error(errorMsg);
            throw new EJBException(errorMsg, e);
        }
    }

    private ConceptSCT createConceptSCTFromResultSet(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        Timestamp effectiveTime = resultSet.getTimestamp("effectiveTime");
        boolean active = resultSet.getBoolean("active");
        long moduleID = resultSet.getLong("moduleId");
        long definitionStatusID = resultSet.getLong("definitionStatusId");

        ConceptSCT conceptSCT = new ConceptSCT(id, effectiveTime, active, moduleID, definitionStatusID);

        /* Se recuperan las descripciones del concepto */
        List<DescriptionSCT> descriptions = getDescriptionsSCTByConcept(id);
        conceptSCT.setDescriptions(descriptions);

        return conceptSCT;
    }

    private List<DescriptionSCT> getDescriptionsSCTByConcept(long id) {
        List<DescriptionSCT> descriptionSCTs = new ArrayList<>();
        ConnectionBD connect = new ConnectionBD();
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.get_descriptions_sct_by_id(?)}")) {

            call.setLong(1, id);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                DescriptionSCT recoveredConcept = createDescriptionSCTFromResultSet(rs);
                descriptionSCTs.add(recoveredConcept);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return descriptionSCTs;
    }

    private DescriptionSCT createDescriptionSCTFromResultSet(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        Timestamp effectiveTime = resultSet.getTimestamp("effectivetime");
        boolean active = resultSet.getBoolean("active");
        long moduleID = resultSet.getLong("moduleId");
        long conceptID = resultSet.getLong("conceptId");
        String languageCode = resultSet.getString("languageCode");
        long typeID = resultSet.getLong("typeId");
        String term = resultSet.getString("term");
        long caseSignificanceID = resultSet.getLong("caseSignificanceId");

        /**
         * Identifies whether the description is an FSN, Synonym or other description type.
         * This field is set to a child of 900000000000446008 | Description type | in the Metadata hierarchy.
         */
        DescriptionSCTType type = typeID == 1 ? FSN : SYNONYM;

        return new DescriptionSCT(id, type, effectiveTime, active, moduleID, conceptID, languageCode, term, caseSignificanceID);
    }

}
