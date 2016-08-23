package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ChangeType;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías on 8/23/16.
 */
@Stateless
public class AuditDAOImpl implements AuditDAO {

    @Override
    public List<ChangeType> getAllChangeTypes() {
        //TODO: Gustavo
        return null;
    }
}
