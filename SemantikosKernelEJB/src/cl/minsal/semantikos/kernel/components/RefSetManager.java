package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Institution;
import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.model.User;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías on 9/20/16.
 */
@Local
public interface RefSetManager {

    /**
     * Este método es responsable de crear un RefSet.
     *
     * @param institution La institución asociada.
     * @param user        El usuario que crea el RefSet.
     *
     * @return El RefSet creado.
     */
    public RefSet createRefSet(Institution institution, User user);

    /**
     * Este método es responsable de crear un RefSet.
     *
     * @param refSet El RefSet que se desea actualizar.
     * @param user   El usuario que crea el RefSet.
     *
     * @return El RefSet creado.
     */
    public RefSet updateRefSet(RefSet refSet, User user);

    /**
     * Este método es responsable de retornar la lista completa de RefSets.
     *
     * @return Una lista de RefSets.
     */
    public List<RefSet> getAllRefSets();
}
