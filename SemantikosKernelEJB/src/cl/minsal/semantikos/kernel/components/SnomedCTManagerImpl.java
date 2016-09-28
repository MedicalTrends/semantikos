package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.TagSMTK;
import cl.minsal.semantikos.model.snomedct.*;

import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Andrés Farías on 9/26/16.
 */
@Stateless
public class SnomedCTManagerImpl implements SnomedCTManager {

    @Override
    public SnapshotProcessingResult processSnapshot(SnomedCTSnapshot snomedCTSnapshot) {
        return new SnapshotProcessingResult();
    }

    @Override
    public List<RelationshipSCT> getRelationshipsFrom(long idConceptSCT) {
        return emptyList();
    }

    @Override
    public List<ConceptSCT> findConceptsBy(String pattern) {

        List<ConceptSCT> concepts = new ArrayList<>();
        ConnectionBD connect = new ConnectionBD();
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.find_cst_by_pattern(?)}")) {

            call.setString(1, pattern);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                ConceptSCT recoveredConcept = createConceptSCTFromResultSet(rs);
                concepts.add(recoveredConcept);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return concepts;
    }

    private ConceptSCT createConceptSCTFromResultSet(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        Timestamp effectiveTime = resultSet.getTimestamp("effectivetime");
        boolean active = resultSet.getBoolean("active");
        long moduleID = resultSet.getLong("moduleid");
        long definitionStatusID = resultSet.getLong("definitionstatusid");

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

        DescriptionSCT.DescriptionSCTType type = typeID == 1 ? DescriptionSCT.DescriptionSCTType.FSN : DescriptionSCT.DescriptionSCTType.SYNONYM;
        return new DescriptionSCT(id, type, term, effectiveTime, active, moduleID, conceptID, languageCode, caseSignificanceID, true);
    }
}
