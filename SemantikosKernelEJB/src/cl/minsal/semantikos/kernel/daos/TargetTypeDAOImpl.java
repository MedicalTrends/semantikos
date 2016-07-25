package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by andres on 7/22/16.
 */
@Stateless
public class TargetTypeDAOImpl implements TargetTypeDAO {

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    @Override
    public BasicTypeDefinition findByID(long idBasicType) throws ParseException {

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

            nativeQuery = this.em.createNativeQuery("SELECT semantikos.get_basic_domain_definition_by_id(?)");
            nativeQuery.setParameter(1, idBasicType);
            List<Object[]> basicDomainDefinitionRS = nativeQuery.getResultList();

            for (Object[] objects2 : basicTypeDefinitionRS) {

                Float floatValue;
                String stringValue;
                Integer intValue;
                Boolean booleanValue;
                Date dateValue;

                if(objects2[0] != null) {
                    floatValue = Float.parseFloat((String) objects2[0]);
                    basicTypeDefinition.addToDomain(floatValue);
                }
                if(objects2[1] != null) {
                    dateValue = new SimpleDateFormat("dd-MM-yyyy").parse((String) objects2[1]);
                    basicTypeDefinition.addToDomain(dateValue);
                }
                if(objects2[2] != null) {
                    stringValue = (String) objects2[2];
                    basicTypeDefinition.addToDomain(stringValue);
                }
                if(objects2[3] != null) {
                    booleanValue = Boolean.parseBoolean((String) objects2[3]);
                    basicTypeDefinition.addToDomain(booleanValue);
                }
                if(objects2[4] != null) {
                    intValue = Integer.parseInt((String) objects2[4]);
                    basicTypeDefinition.addToDomain(intValue);
                }
            }

            return basicTypeDefinition;
        }
        return null;
    }
}
