package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by andres on 7/22/16.
 */
public class TargetTypeDAOImpl implements TargetTypeDAO {

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    @Override
    public BasicTypeDefinition findByID(long idBasicType) {

        /* Se invoca la consulta para recuperar las relaciones */
        Query nativeQuery = this.em.createNativeQuery("SELECT semantikos.get_basic_type_definition_by_id(?)");
        nativeQuery.setParameter(1, idBasicType);
        List<Object[]> basicTypeDefinitionRS = nativeQuery.getResultList();

        for (Object[] objects : basicTypeDefinitionRS) {
            long id = new BigInteger((String)objects[0]).longValue();
            String name = (String) objects[1];
            String description = (String) objects[2];

            int lowerBoundary = Integer.parseInt((String) objects[3]);
            int upperBoundary = Integer.parseInt((String) objects[4]);

            BasicTypeDefinition basicTypeDefinition = new BasicTypeDefinition(id, name, description);

        }

        return null;
    }
}
