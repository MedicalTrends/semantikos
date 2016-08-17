package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.relationships.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Diego Soto / Gustavo Punucura
 */
@Stateless
public class RelationshipDAOImpl implements RelationshipDAO {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(RelationshipDAOImpl.class);

    @Override
    public void persist(Relationship relationship) {

        ConnectionBD connect = new ConnectionBD();
        String sql = "{call semantikos.create_relationship(?,?,?)}";
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setLong(1, relationship.getSourceConcept().getId());
            call.setLong(2,relationship.getTarget().getId());
            call.setLong(3,relationship.getRelationshipDefinition().getId());
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
}
