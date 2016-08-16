package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by des01c7 on 16-08-16.
 */
public class RelationshipAttributeDAOImpl implements RelationshipAttributeDAO {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(RelationshipDAOImpl.class);

    @Override
    public long createRelationshipAttribute(long idRelationshipAttributeDefinition, long idRelationship, long idDestiny) {
        long idRelationShipAttribute;

        ConnectionBD connect = new ConnectionBD();

        String sql = "{call semantikos.create_relationship_attribute(?,?,?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql)) {

            call.setLong(1, idRelationshipAttributeDefinition);
            call.setLong(2,idRelationship);
            call.setLong(3,idDestiny);
            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                idRelationShipAttribute = rs.getLong(1);
                if(idRelationShipAttribute==-1){
                    String errorMsg = "La relacion no fue creada";
                    logger.error(errorMsg);
                    throw new IllegalArgumentException(errorMsg);
                }
            } else {
                String errorMsg = "La relacion no fue creada";
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            rs.close();
        } catch (SQLException e) {
            throw new EJBException(e);
        }

        return idRelationShipAttribute;
    }
}
