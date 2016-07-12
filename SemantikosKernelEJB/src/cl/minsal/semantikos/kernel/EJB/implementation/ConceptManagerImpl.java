package cl.minsal.semantikos.kernel.EJB.implementation;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.kernel.EJB.interfaces.ConceptManager;

import javax.ejb.Stateless;

/**
 * Created by des01c7 on 29-06-16.
 */

//@Stateless
public class ConceptManagerImpl implements ConceptManager {

    @Override
    public ConceptSMTK newConcept(int idCategory, String termino) {

        ConceptSMTK conceptSMTK = new ConceptSMTK(idCategory, termino);
        return conceptSMTK;
    }

}
