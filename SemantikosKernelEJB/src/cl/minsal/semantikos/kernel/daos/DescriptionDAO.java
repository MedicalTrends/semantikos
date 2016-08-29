package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.*;

import javax.ejb.Local;
import java.util.List;


/**
 * @author Andrés Farías.
 */
@Local
public interface DescriptionDAO {

    public List<DescriptionType> getDescriptionTypes();

    public Description getDescriptionBy(long id);

    /**
     * Este método es responsable de recuperar todas las descripciones de un concepto a partir de su ID.
     *
     * @param idConcept El ID del concepto cuyas descripciones se desea recuperar.
     *
     * @return La lista de las descripciones del concepto cuyo ID fue dado.
     */
    public List<Description> getDescriptionsByConceptID(long idConcept);

    /**
     * Este método es responsable de retornar un Factory.
     *
     * @return El factory adecuado... //TODO: WHAT?!
     */
    public DescriptionTypeFactory refreshDescriptionTypes();

    /**
     * Este método es responsable de persistir una descripción en la BDD. Luego de ser persistida, la descripción es
     * actualizada con su nuevo Identificador único.
     *
     * @param description La descripción a persistir.
     * @param conceptSMTK El concepto al que está asociada la descripción.
     * @param user        El usuario que persiste la descripción.
     */
    public Description persist(Description description, ConceptSMTK conceptSMTK, User user);

    /**
     * Este método es responsable de persistir y asociar a un concepto todas las descripciones que no están
     * persistidas,
     * del conjunto de descripciones dadas como parámetro.
     *
     * @param descriptions Las descripciones que se deben persistir. Esta lista de descripciones puede contener
     *                     descripciones que ya se encuentran persistidas. Estas descripciones persistentes son
     *                     ignoradas y ningún cambio en ellas es realizado.
     * @param conceptSMTK  El concepto al cual se asocian las descripciones que se persisten.
     * @param user         El usuario que realiza la acción.
     *
     * @return La lista de descripciones persistidas.
     */
    public List<Description> persistNonPersistent(List<Description> descriptions, ConceptSMTK conceptSMTK, User user);

    /**
     * Este método es responsable de invalidar la descripción.
     *
     * @param description La descripción que se deben actualizar.
     */
    public void invalidate(Description description);

    /**
     * Este método es responsable de actualizar los datos de una descripción.
     *
     * @param description La descripción que será actualizada.
     */
    public void update(Description description);
}
