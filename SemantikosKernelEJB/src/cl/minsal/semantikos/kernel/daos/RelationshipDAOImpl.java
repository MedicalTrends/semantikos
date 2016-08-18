package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipFactory;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;
import cl.minsal.semantikos.model.snomedct.ConceptSCTFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Diego Soto / Gustavo Punucura
 */
@Stateless
public class RelationshipDAOImpl implements RelationshipDAO {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(RelationshipDAOImpl.class);

    @EJB
    private ConceptSCTFactory conceptSCTFactory;

    @EJB
    private RelationshipFactory relationshipFactory;

    @Override
    public void persist(Relationship relationship) {

        ConnectionBD connect = new ConnectionBD();
        String sql = "{call semantikos.get_relationships_with_concept_sct(?)}";
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setLong(1, relationship.getSourceConcept().getId());
            call.setLong(2, relationship.getTarget().getId());
            call.setLong(3, relationship.getRelationshipDefinition().getId());
            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                relationship.setId(rs.getLong(1));
            } else {
                String errorMsg = "La relacion no fue creada. Esta es una situación imposible. Contactar a Desarrollo";
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<Relationship> getRelationshipsToCSTConcept(ConceptSCT destinyConceptSCT) {

        long idDestinyConceptSCT = destinyConceptSCT.getId();

        ConnectionBD connect = new ConnectionBD();
        String sql = "{call semantikos.get_relationships_to_concept_sct(?)}";
        String resultJSON;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setLong(1, idDestinyConceptSCT);
            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                resultJSON = rs.getString(1);
            } else {
                String errorMsg = "La relacion no fue creada. Esta es una situación imposible. Contactar a Desarrollo";
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            throw new EJBException(e);
        }

        return relationshipFactory.createRelationshipsFromJSON(resultJSON);
    }

    @Override
    public Relationship getRelationshipByID(long idRelationship) {

        ConnectionBD connect = new ConnectionBD();
        String sql = "{call semantikos.get_relationships_by_id(?)}";
        String resultJSON;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setLong(1, idRelationship);
            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                resultJSON = rs.getString(1);
            } else {
                String errorMsg = "La relacion no fue creada. Esta es una situación imposible. Contactar a Desarrollo";
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            throw new EJBException(e);
        }

        return relationshipFactory.createFromJSON(resultJSON);
    }

}

class RelationshipDTO {
}

