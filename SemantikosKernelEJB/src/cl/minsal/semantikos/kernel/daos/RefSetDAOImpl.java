package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.RefSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.sql.*;

/**
 * @author Andrés Farías on 9/20/16.
 */
@Stateless
public class RefSetDAOImpl implements RefSetDAO {

    private static final Logger logger = LoggerFactory.getLogger(RefSetDAO.class);

    @Override
    public void persist(RefSet refSet) {

        ConnectionBD connect = new ConnectionBD();
        String CREATE_REFSET = "{call semantikos.create_refset(?,?,?,?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(CREATE_REFSET)) {

            call.setString(1, refSet.getName());
            call.setLong(2, refSet.getInstitution().getId());
            call.setTimestamp(3, refSet.getCreationDate());
            call.setTimestamp(4, refSet.getValidityUntil());
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                refSet.setId(rs.getLong(1));
            }
            rs.close();

        } catch (SQLException e) {
            logger.error("Error al crear el RefSet:" + refSet, e);
        }
    }

    @Override
    public void update(RefSet refSet) {
        ConnectionBD connect = new ConnectionBD();
        String UPDATE_REFSET = "{call semantikos.update_refset(?,?,?,?,?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(UPDATE_REFSET)) {

            call.setLong(1, refSet.getId());
            call.setString(2, refSet.getName());
            call.setLong(3, refSet.getInstitution().getId());
            call.setTimestamp(4, refSet.getCreationDate());
            call.setTimestamp(5, refSet.getValidityUntil());
            call.execute();
        } catch (SQLException e) {
            logger.error("Error al crear el RefSet:" + refSet, e);
        }
    }

    @Override
    public void bind(Description description, RefSet refSet) {
        ConnectionBD connect = new ConnectionBD();
        String UPDATE_REFSET = "{call semantikos.bind_description_to_refset(?,?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(UPDATE_REFSET)) {

            call.setLong(1, refSet.getId());
            call.setLong(2, description.getConceptSMTK().getId());
            call.execute();
        } catch (SQLException e) {
            logger.error("Error al asociar el RefSet:" + refSet + " a la descripción " + description, e);
        }
    }

    @Override
    public void unbind(Description description, RefSet refSet) {
        ConnectionBD connect = new ConnectionBD();
        String UPDATE_REFSET = "{call semantikos.unbind_description_from_refset(?,?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(UPDATE_REFSET)) {

            call.setLong(1, refSet.getId());
            call.setLong(2, description.getConceptSMTK().getId());
            call.execute();
        } catch (SQLException e) {
            logger.error("Error al des-asociar el RefSet:" + refSet + " a la descripción " + description, e);
        }
    }
}
