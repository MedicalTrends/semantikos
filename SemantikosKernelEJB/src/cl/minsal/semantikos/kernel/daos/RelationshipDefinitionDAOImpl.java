package cl.minsal.semantikos.kernel.daos;


import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Multiplicity;
import cl.minsal.semantikos.model.helpertables.HelperTableFactory;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */

@Stateless
public class RelationshipDefinitionDAOImpl implements RelationshipDefinitionDAO {

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    @EJB
    CategoryDAO categoryDAO;

    @EJB
    DescriptionDAO descriptionDAO;

    @EJB
    private TargetTypeDAO targetTypeDAO;

    @Override
    public List<RelationshipDefinition> getRelationshipDefinitionsByCategory(long idCategory) {

        ArrayList<RelationshipDefinition> attributes = new ArrayList<RelationshipDefinition>();

        ConnectionBD connect = new ConnectionBD();
        String GET_RELATIONSHIP_DEFINITIONS_BY_ID_CATEGORY = "{call semantikos.get_relationship_definitions_by_category(?)}";

        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(GET_RELATIONSHIP_DEFINITIONS_BY_ID_CATEGORY)) {

            /* Se invoca la consulta para recuperar las relaciones */

            call.setLong(1, idCategory);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {

                long idRelationship = rs.getLong("id_relationship_definition");
                String name = rs.getString("name");
                String description = rs.getString("description");

                /* Limites de la multiplicidad */
                int lowerBoundary = rs.getInt("multiplicity_from");
                int upperBoundary = rs.getInt("multiplicity_to");
                Multiplicity multiplicity = new Multiplicity(lowerBoundary, upperBoundary);

                /* Se recupera el target definition */
                TargetDefinition targetDefinition = getTargetDefinition(rs.getString("id_category"), rs.getString("id_accesory_table_name"), rs.getString("id_extern_table_name"), rs.getString("id_basic_type"), rs.getString("is_sct_type"));

                 /* Se crea el objeto */
                RelationshipDefinition relationshipDefinition = new RelationshipDefinition(idRelationship, name, description, targetDefinition, multiplicity);
                attributes.add(relationshipDefinition);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attributes;

    }

    /**
     * Este método es responsable de retornar una instancia del target Definition adecuado.
     *
     * @param idCategory      Identificador de la categoría destino.
     * @param idAccesoryTable Identificador de la Tabla auxiliar.
     * @param idBasicType     Identificador del tipo básico.
     * @param isSCTType       Indicador booleano (<code>"true"</code> o "<code>false</code>").
     *
     * @return Una instancia del Target Definition concreto.
     *
     * @throws IllegalArgumentException Arrojado si todos los parámetros son nulos.
     */
    private TargetDefinition getTargetDefinition(String idCategory, String idAccesoryTable, String idExternTable, String idBasicType, String isSCTType) throws IllegalArgumentException {

        /* Se testea si es un tipo básico */

        if (idBasicType != null) {
            long id = new BigInteger(idBasicType).longValue();
            return targetTypeDAO.findByID(id);
        }

        if (idCategory != null) {
            long id = new BigInteger(idCategory).longValue();
            return categoryDAO.getCategoryById(id);
        }

        if (idAccesoryTable != null) {
            long id = Long.parseLong(idAccesoryTable);
            return HelperTableFactory.getInstance().getHelperTable(id);
        }

        throw new IllegalArgumentException("Todos los parámetros eran nulos.");
    }
}
