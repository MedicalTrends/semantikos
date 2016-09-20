package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.RefSetDAO;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.Institution;
import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * @author Andrés Farías on 9/20/16.
 */
@Stateless
public class RefSetManagerImpl implements RefSetManager {

    @EJB
    private RefSetDAO refsetDAO;

    @EJB
    private AuditManager auditManager;

    @Override
    public RefSet createRefSet(Institution institution, User user) {

        /* Se validan las pre-condiciones */
        new RefSetCreationBR().validatePreConditions(institution, user);

        /* Se crea el RefSet y se persiste */
        RefSet refSet = new RefSet(institution);
        refsetDAO.persist(refSet);

        /* Se registra la creación */
        auditManager.recordRefSetCreation(refSet, user);

        /* Se registra la creación del RefSet */
        return refSet;
    }

    @Override
    public RefSet updateRefSet(RefSet refSet, User user) {

        /* Se validan las pre-condiciones */
        new RefSetUpdateBR().validatePreConditions(refSet, user);

        /* Se crea el RefSet y se persiste */
        refsetDAO.update(refSet);

        /* Se registra la creación */
        auditManager.recordRefSetUpdate(refSet, user);

        /* Se registra la creación del RefSet */
        return refSet;
    }

    @Override
    public void bindDescriptionToRefSet(Description description, RefSet refSet, User user) {

        /* Se validan las pre-condiciones */
        new RefSetBindingBR().validatePreConditions();

        /* Se asocia la descripción al RefSet */
        refsetDAO.bind(description, refSet);

        /* Se registra la creación */
        auditManager.recordRefSetBinding(refSet, description, user);
    }

    @Override
    public void unbindDescriptionToRefSet(Description description, RefSet refSet, User user) {

        /* Se validan las pre-condiciones */
        new RefSetUnbindingBR().validatePreConditions();

        /* Se asocia la descripción al RefSet */
        refsetDAO.unbind(description, refSet);

        /* Se registra la creación */
        auditManager.recordRefSetUnbinding(refSet, description, user);
    }

    @Override
    public void invalidate(RefSet refSet, User user) {

        /* Se validan las pre-condiciones */
        new RefSetInvalidationBR().validatePreConditions();

        /* Se asocia la descripción al RefSet */
        refsetDAO.invalidate(refSet, new Timestamp(currentTimeMillis()));

        /* Se registra la creación */
        auditManager.recordRefSetInvalidate(refSet, user);
    }

    @Override
    public List<RefSet> getAllRefSets() {

        //FIXME: Retornar el correcto.
        ArrayList<RefSet> refSets = new ArrayList<RefSet>();
        refSets.add(new RefSet(new Institution()));

        return refSets;
    }
}
