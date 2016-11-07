package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmap;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.crossmaps.CrossmapSetMember;
import cl.minsal.semantikos.model.crossmaps.IndirectCrossmap;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * @author Andrés Farías on 8/19/16.
 */
@Stateless
public class CrossmapsDAOImpl implements CrossmapsDAO {

    private static final Logger logger = LoggerFactory.getLogger(ConceptDAOImpl.class);

    @EJB
    private ConceptDAO conceptDAO;

    @EJB
    private RelationshipDefinitionDAO relationshipDAO;

    @Override
    public DirectCrossmap create(DirectCrossmap directCrossmap, User user) {

        ConnectionBD connect = new ConnectionBD();
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.create_direct_crossmap(?,?,?,?)}")) {

            /*
                1. source concept
                2. external term id
                3. validity until.
                4. user id
            */
            call.setLong(1, directCrossmap.getSourceConcept().getId());
            call.setLong(2, directCrossmap.getTarget().getId());
            call.setTimestamp(3, new Timestamp(currentTimeMillis()));
            call.setLong(4, user.getIdUser());
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                directCrossmap.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            String s = "Error al crear un Crossmap en la base de datos";
            logger.error(s);
            throw new EJBException(s, e);
        }

        return directCrossmap;
    }

    @Override
    public DirectCrossmap getDirectCrossmapById(long idCrossmap) {
        ConnectionBD connect = new ConnectionBD();
        DirectCrossmap directCrossmapFromResultSet;
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall("{call semantikos.get_direct_crossmap(?)}")) {

            call.setLong(1, idCrossmap);
            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                directCrossmapFromResultSet = createDirectCrossmapFromResultSet(rs);
            } else {
                throw new EJBException("Error al intentar obtener un crossmap directo de ID= " + idCrossmap);
            }
            rs.close();
        } catch (SQLException e) {
            String s = "Error al crear un Crossmap en la base de datos";
            logger.error(s);
            throw new EJBException(s, e);
        }

        return directCrossmapFromResultSet;
    }

    /**
     * Este método es responsable de crear un objeto <code>DirectCrossmap</code> a partir de un ResultSet.
     *
     * @param rs El ResultSet a partir del cual se crea el crossmap.
     *
     * @return Un Crossmap Directo creado a partir del result set.
     */
    private DirectCrossmap createDirectCrossmapFromResultSet(ResultSet rs) throws SQLException {
        // id bigint, id_concept bigint, id_crossmapset bigint, id_user bigint, id_validity_until timestamp
        long id = rs.getLong("id");
        long idConcept = rs.getLong("id_concept");
        long idCrossmapSet = rs.getLong("id_crossmapsetmember");
        long idRelationshipDefinition = rs.getLong("id_relationshipdefinition");
        long idUser = rs.getLong("id_user");
        Timestamp validityUntil = rs.getTimestamp("validity_until");

        ConceptSMTK conceptSMTK = conceptDAO.getConceptByID(idConcept);
        CrossmapSetMember crossmapSetMember = this.getCrossmapSetMemberById(idCrossmapSet);
        RelationshipDefinition relationshipDefinition = relationshipDAO.getRelationshipDefinitionByID(idRelationshipDefinition);
        return new DirectCrossmap(id, conceptSMTK, crossmapSetMember, relationshipDefinition, validityUntil);
    }

    @Override
    public List<IndirectCrossmap> getIndirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<IndirectCrossmap> getIndirectCrossmapsByConceptID(String conceptID) {
        return null;
    }

    @Override
    public List<DirectCrossmap> getDirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<DirectCrossmap> getDirectCrossmapsByConceptID(String conceptID) {
        return null;
    }

    @Override
    public DirectCrossmap bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember) {
        return null;
    }

    @Override
    public CrossmapSetMember getCrossmapSetMemberById(long idCrossmapSet) {
        // TODO: Terminar la BDD.
        return null;
    }
}
