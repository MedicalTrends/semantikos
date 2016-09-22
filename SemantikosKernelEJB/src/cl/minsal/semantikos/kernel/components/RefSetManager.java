package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.*;

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
     * @param name        Nombre del RefSet. Nombre corto y descriptivo de su contenido, para identificación por
     *                    humanos
     *
     * @param institution La institución asociada.
     * @param user        El usuario que crea el RefSet.
     * @return El RefSet creado.
     */
    public RefSet createRefSet(String name, Institution institution, User user);

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
     * Este método es responsable de asociar una descripción a un RefSet.
     *
     * @param conceptSMTK La descripción que se desea agregar.
     * @param refSet      El RefSet al cual se desea asociar.
     * @param user        El usuario que realiza la acción.
     */
    public void bindConceptToRefSet(ConceptSMTK conceptSMTK, RefSet refSet, User user);

    /**
     * Este método es responsable de des-asociar una descripción a un RefSet.
     *
     * @param conceptSMTK La descripción que se desea agregar.
     * @param refSet      El RefSet al cual se desea asociar.
     * @param user        El usuario que realiza la acción.
     */
    public void unbindConceptToRefSet(ConceptSMTK conceptSMTK, RefSet refSet, User user);

    /**
     * Este método es responsable de dejar inválido un RefSet.
     *
     * @param refSet El RefSet que se desea invalidar.
     */
    public void invalidate(RefSet refSet, User user);

    /**
     * Este método es responsable de retornar la lista completa de RefSets.
     *
     * @return Una lista de RefSets.
     */
    public List<RefSet> getAllRefSets();
}
