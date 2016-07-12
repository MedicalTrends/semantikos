package cl.minsal.semantikos.kernel.EJB.interfaces;

import cl.minsal.semantikos.model.ConceptSMTK;

import javax.ejb.Local;

/**
 * Created by des01c7 on 29-06-16.
 */

@Local
public interface ConceptManager {


    public ConceptSMTK newConcept(int idCategory, String termino);

}
