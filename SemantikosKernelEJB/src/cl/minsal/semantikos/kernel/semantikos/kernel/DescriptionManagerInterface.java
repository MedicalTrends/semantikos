package cl.minsal.semantikos.kernel.semantikos.kernel;


import cl.minsal.semantikos.model.TypeDescription;

import java.util.ArrayList;

/**
 * Created by stk-des01 on 07-06-16.
 */
public interface DescriptionManagerInterface {

    public void addDescriptionToConcept(String idConcept, String description, String type);

    public ArrayList<TypeDescription> getAllTypeDescription();

    public String getIdDescription(String tipoDescription);


}
