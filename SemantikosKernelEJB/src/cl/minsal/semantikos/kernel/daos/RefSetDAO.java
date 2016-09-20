package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.RefSet;

import javax.ejb.Local;

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
     * @param refSet El RefSet qeu se desea actualizar.
     */
    public void update(RefSet refSet);
}
