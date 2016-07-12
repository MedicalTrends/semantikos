package cl.minsal.semantikos.kernel.EJB.implementation;

import cl.minsal.semantikos.kernel.domain.Concept;
import cl.minsal.semantikos.kernel.EJB.interfaces.ConceptManager;

import javax.ejb.Stateless;

/**
 * Created by des01c7 on 29-06-16.
 */

@Stateless
public class ConceptManagerImpl implements ConceptManager {

    @Override
    public Concept newConcept(int idCategory, String termino) {

        Concept concept = new Concept(idCategory, termino);
        return concept;
    }

}
