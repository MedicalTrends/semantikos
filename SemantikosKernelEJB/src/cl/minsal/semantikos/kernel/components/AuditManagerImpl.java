package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.AuditDAO;
import cl.minsal.semantikos.model.ChangeType;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Stateless
public class AuditManagerImpl implements AuditManagerInterface {

    @EJB
    private AuditDAO auditDAO;

    @Override
    public void recordNewConcept(ConceptSMTK conceptSMTK, IUser user) {
        // TODO: Implement this.
    }

    @Override
    public void recordUpdateConcept(ConceptSMTK conceptSMTK, IUser user) {
        // TODO: Implement this.
    }

    @Override
    public List<ChangeType> getAllChangeTypes() {
        return auditDAO.getAllChangeTypes();
    }
}
