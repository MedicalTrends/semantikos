package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;
import cl.minsal.semantikos.model.TagFactory;
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
 * @author Gustavo Punucura
 */
@Stateless
public class TagDAOImpl implements TagDAO {

    /** El logger de esta clase */
    private static final Logger logger = LoggerFactory.getLogger(ConceptDAOImpl.class);

    @EJB
    private TagFactory tagFactory;

    @Override
    public void persist(Tag tag) {
        ConnectionBD connect = new ConnectionBD();

        long idTag;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.create_tag(?,?,?,?)}")) {

            call.setString(1, tag.getName());
            call.setString(2, tag.getColorBackground());
            call.setString(3, tag.getColorLetter());
            call.setLong(4, tag.getParentTag().getId());
            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                idTag = rs.getLong(1);
            } else {
                String errorMsg = "Error al persistir el tag " + tag;
                logger.error(errorMsg);
                throw new EJBException(errorMsg);
            }

            rs.close();

        } catch (SQLException e) {
            String errorMsg = "Error al persistir el tag " + tag;
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        /* Se establece el ID de la entidad */
        tag.setId(idTag);
    }

    @Override
    public void update(Tag tag) {
        ConnectionBD connect = new ConnectionBD();

        long idTag;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.create_tag(?,?,?,?)}")) {

            call.setLong(1, tag.getId());
            call.setString(2, tag.getName());
            call.setString(3, tag.getColorBackground());
            call.setString(4, tag.getColorLetter());
            call.setLong(5, tag.getParentTag().getId());
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                idTag = rs.getLong(1);
                if (idTag == 0) {
                    throw new EJBException("No se realizó la actualización del tag " + tag);
                }
            }
            rs.close();

        } catch (SQLException e) {
            String errorMsg = "Error al actualizar el tag " + tag;
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }
    }

    @Override
    public void remove(Tag tag) {
        ConnectionBD connect = new ConnectionBD();

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.remove_tag(?)}")) {

            call.setLong(1, tag.getId());
            call.execute();
        } catch (SQLException e) {
            String errorMsg = "Error al persistir el tag " + tag;
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }
    }

    @Override
    public List<Tag> findTagsBy(String namePattern) {
        ConnectionBD connect = new ConnectionBD();

        String json;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.find_tag_by_pattern(?)}")) {

            call.setString(1, namePattern);
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                json = rs.getString(1);
            } else {
                String errorMsg = "Error imposible!";
                logger.error(errorMsg);
                throw new EJBException(errorMsg);
            }
            rs.close();

        } catch (SQLException e) {
            String errorMsg = "Error al buscar tag por patrón: " + namePattern;
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        return tagFactory.createTagsFromJSON(json);
    }

    @Override
    public void linkTagToTag(Tag tagPattern, Tag tagChild) {

    }

    @Override
    public List<Tag> getAllTags() {
        return null;
    }

    @Override
    public List<Tag> getChildrenOf(long idParent) {
        ConnectionBD connect = new ConnectionBD();

        String json;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.find_tags_by_parent(?)}")) {

            call.setLong(1, idParent);
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                json = rs.getString(1);
            } else {
                String errorMsg = "Error imposible!";
                logger.error(errorMsg);
                throw new EJBException(errorMsg);
            }
            rs.close();

        } catch (SQLException e) {
            String errorMsg = "Error al buscar los hijos del tag con ID=" + idParent;
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        return tagFactory.createTagsFromJSON(json);

    }

    @Override
    public void assignTag(ConceptSMTK conceptSMTK, Tag tag) {

    }

    @Override
    public void unassignTag(ConceptSMTK conceptSMTK, Tag tag) {

    }

    @Override
    public Tag findTagByID(long id) {
        return null;
    }


}
