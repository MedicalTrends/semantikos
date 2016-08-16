package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;

import javax.ejb.Stateless;

/**
 * @author Andrés Farías
 */
@Stateless
public class AuditManagerImpl implements AuditManagerInterface {

    @Override
    public void recordNewConcept(ConceptSMTK conceptSMTK, User user) {
        // TODO: Implement this.
    }
}
