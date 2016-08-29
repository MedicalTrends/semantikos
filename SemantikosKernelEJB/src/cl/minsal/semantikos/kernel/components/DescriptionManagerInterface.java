package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Diego Soto on 07-06-16.
 */
@Local
public interface DescriptionManagerInterface {

    public void addDescriptionToConcept(String idConcept, String description, String type);

    /**
     * Este método es responsable de mover una descripción (<code>description</code>) asociada a un concepto
     * (<code>sourceConcept</code>) a otro concepto (<code>targetConcept</code>)
     *
     * @param sourceConcept El concepto en donde se encuentra la descripción inicialmente.
     * @param targetConcept El concepto al cual se quiere mover la descripción.
     * @param description   La descripción que se desea trasladar.
     */
    public void moveDescriptionToConcept(ConceptSMTK sourceConcept, ConceptSMTK targetConcept, Description description);

    public String getIdDescription(String tipoDescription);

    /**
     * Método encargado de obtener todos los tipos de descripciones
     *
     * @return Lista de tipos de descripciones
     */
    public List<DescriptionType> getAllTypes();

    public List<Description> findDescriptionsByConcept(int idConcept);

    public DescriptionType getTypeFSN();

    public DescriptionType getTypeFavorite();

    /**
     * Este método es responsable de recuperar las descripciones de un concepto.
     *
     * @param concept El concepto cuyas descripciones deben ser recuperadas.
     *
     * @return Un objeto <code>java.util.List</code> con las descripciones del concepto <code>concept</code>.
     */
    List<Description> getDescriptionsOf(ConceptSMTK concept);

    /**
     * Este método es responsable de generar un description id
     *
     * @return Un objeto <code>java.util.List</code> con las descripciones del concepto <code>concept</code>.
     */
    public String generateDescriptionId();
}
