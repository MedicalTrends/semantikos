package cl.minsal.semantikos.view.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrés Farías on 10/5/16.
 */
@Stateless
public class SemantikosWebImpl implements SemantikosWeb {

    private static final Logger logger = LoggerFactory.getLogger(SemantikosWebImpl.class);

    @Override
    public long getCompositeOf(RelationshipDefinition relationshipDefinition) {

        ConnectionBD connect = new ConnectionBD();
        // TODO: Implementar esta función SQL.
        String sql = "{call semantikos.get_composite_by_relationship_definition(?)}";
        long idComposite;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setLong(1, relationshipDefinition.getId());
            call.execute();
            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                idComposite = rs.getLong(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            String errorMsg = "Error al recuperar descripciones de la BDD.";
            logger.error(errorMsg, e);
            throw new EJBException(e);
        }

        return idComposite;
    }
}
