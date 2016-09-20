package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.RefSet;

import javax.ejb.Local;

/**
 * @author Andrés Farías on 9/20/16.
 */
@Local
public interface RefSetDAO {

    /**
     * Este método es responsable de persistir un RefSet. Si el metodo se ejecuta correctamente la entidad es
     * actualizada con un nuevo identificador.
     *
     * @param refSet El RefSet que se desea persistir.
     */
    public void persist(RefSet refSet);
}
