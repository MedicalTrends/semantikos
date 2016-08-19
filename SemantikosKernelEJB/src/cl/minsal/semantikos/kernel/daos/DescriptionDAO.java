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

    public List<Description> getDescriptionBy(int id);

    /**
     * Este método es responsable de recuperar todas las descripciones de un concepto a partir de su ID.
     *
     * @param id El ID del concepto cuyas descripciones se desea recuperar.
     *
     * @return La lista de las descripciones del concepto cuyo ID fue dado.
     */
    public List<Description> getDescriptionsByConceptID(long id);

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
     */
    public void persist(Description description, ConceptSMTK conceptSMTK, IUser user);
}
