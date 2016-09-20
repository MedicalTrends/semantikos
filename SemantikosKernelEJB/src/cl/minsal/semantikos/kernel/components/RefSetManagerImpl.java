package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.RefSetDAO;
import cl.minsal.semantikos.model.Institution;
import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.RefSetCreationBR;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Andrés Farías on 9/20/16.
 */
@Stateless
public class RefSetManagerImpl implements RefSetManager {

    private RefSetDAO refsetDAO;

    @EJB
    private AuditManager auditManager;

    @Override
    public RefSet createRefSet(Institution institution, User user) {

        /* Se validan las pre-condiciones */
        new RefSetCreationBR().validatePreConditions(user);

        /* Se crea el RefSet y se persiste */
        RefSet refSet = new RefSet(institution);
        refsetDAO.persist(refSet);

        auditManager.recordRefSetCreation(refSet, user);

        /* Se registra la creación del RefSet */
        return refSet;
    }
}
