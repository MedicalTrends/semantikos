package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.RefSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrés Farías on 9/20/16.
 */
@Stateless
public class RefSetDAOImpl implements RefSetDAO {

    private static final Logger logger = LoggerFactory.getLogger(RefSetDAO.class);

    @Override
    public void persist(RefSet refSet) {

        ConnectionBD connect = new ConnectionBD();
        String CREATE_CATEGORY = "{call semantikos.create_refset(?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(CREATE_CATEGORY)) {

            call.setLong(1, refSet.getInstitution().getId());
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
        String UPDATE_REFSET = "{call semantikos.update_refset(?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(UPDATE_REFSET)) {

            call.setLong(1, refSet.getId());
            call.setLong(2, refSet.getInstitution().getId());
            call.execute();
        } catch (SQLException e) {
            logger.error("Error al crear el RefSet:" + refSet, e);
        }
    }

    @Override
    public void bind(Description description, RefSet refSet) {
        ConnectionBD connect = new ConnectionBD();
        String UPDATE_REFSET = "{call semantikos.bind_description_to_refset(?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(UPDATE_REFSET)) {

            call.setLong(1, refSet.getId());
            call.setLong(2, description.getId());
            call.execute();
        } catch (SQLException e) {
            logger.error("Error al asociar el RefSet:" + refSet + " a la descripción " + description, e);
        }
    }
}
