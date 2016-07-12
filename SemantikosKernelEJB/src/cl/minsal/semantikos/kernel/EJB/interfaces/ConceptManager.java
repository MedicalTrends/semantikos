package cl.minsal.semantikos.kernel.EJB.interfaces;

import cl.minsal.semantikos.kernel.domain.Concept;
import cl.minsal.semantikos.kernel.domain.Concept;

import javax.ejb.Local;

/**
 * Created by des01c7 on 29-06-16.
 */

@Local
public interface ConceptManager {


    public Concept newConcept(int idCategory, String termino);

}
