package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.model.TypeDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stk-des01 on 07-06-16.
 */
public interface DescriptionManagerInterface {

    public void addDescriptionToConcept(String idConcept, String description, String type);

    public ArrayList<TypeDescription> getAllTypeDescription();

    public String getIdDescription(String tipoDescription);

    public List<DescriptionType> getAllTypes();

    public List<Description> findDescriptionsByConcept(int idConcept);


}
