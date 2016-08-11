package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.model.TypeDescription;

import javax.ejb.Local;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stk-des01 on 07-06-16.
 */
@Local
public interface DescriptionManagerInterface {

    public void addDescriptionToConcept(String idConcept, String description, String type);

    public String getIdDescription(String tipoDescription);

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
}
