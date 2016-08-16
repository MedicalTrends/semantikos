package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;

import javax.ejb.Stateless;

/**
 * @author Andrés Farías
 */
@Stateless
public class AuditManagerImpl implements AuditManagerInterface {

    @Override
    public void recordNewConcept(ConceptSMTK conceptSMTK, IUser IUser) {
        // TODO: Implement this.
    }
}
