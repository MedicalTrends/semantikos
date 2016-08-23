package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ChangeType;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías on 8/23/16.
 */
@Local
public interface AuditDAO {

    /**
     * Este método es responsable de obtener y agrupar en una lista todos los tipos de cambios existentes.
     *
     * @return Una <code>List</code> con los tipos de cambio.
     */
    public List<ChangeType> getAllChangeTypes();
}
