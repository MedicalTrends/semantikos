package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.RefSet;

import javax.ejb.Local;
import java.sql.Timestamp;

/**
 * @author Andrés Farías on 9/20/16.
 */
@Local
public interface RefSetDAO {

    /**
     * Este método es responsable de persistir un RefSet. Si el método se ejecuta correctamente la entidad es
     * actualizada con un nuevo identificador.
     *
     * @param refSet El RefSet que se desea persistir.
     */
    public void persist(RefSet refSet);

    /**
     * Este método es responsable de actualizar un RefSet ya existente.
     *
     * @param refSet El RefSet qeu se desea actualizar.
     */
    public void update(RefSet refSet);

    /**
     * Este método es responsable de persistir la asociación de una descripción a un RefSet.
     *
     * @param description La descripción que se desea asociar al refset.
     * @param refSet      El Refset al cual se asocia la descripción.
     */
    public void bind(Description description, RefSet refSet);

    /**
     * Este método es responsable de persistir la des-asociación de una descripción a un RefSet.
     *
     * @param description La descripción que se desea des-asociar al refset.
     * @param refSet      El Refset al cual se asocia la descripción.
     */
    public void unbind(Description description, RefSet refSet);
}
