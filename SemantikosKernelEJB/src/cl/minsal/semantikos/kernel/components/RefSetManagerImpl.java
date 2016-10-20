package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.RefSetDAO;
import cl.minsal.semantikos.model.*;
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
    public RefSet createRefSet(RefSet refSet, User user) {
        RefSet refsetPersist = createRefSet(refSet.getName(),refSet.getInstitution(),user);

            /* Se guardan los conceptos asignados al refset*/

        for (ConceptSMTK concept: refSet.getConcepts()) {
            bindConceptToRefSet(concept, refsetPersist, user);
            refsetPersist.bindConceptTo(concept);
        }

        return refsetPersist;
    }

    @Override
    public RefSet createRefSet(String name, Institution institution, User user) {

        /* Se validan las pre-condiciones */
        new RefSetCreationBR().validatePreConditions(institution, user);

        /* Se crea el RefSet y se persiste */
        RefSet refSet = new RefSet(name, institution, new Timestamp(currentTimeMillis()));
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
    public void bindConceptToRefSet(ConceptSMTK conceptSMTK, RefSet refSet, User user) {

        /* Se validan las pre-condiciones */
        new RefSetBindingBR().validatePreConditions();

        /* Se asocia la descripción al RefSet */
        refsetDAO.bind(conceptSMTK, refSet);

        /* Se registra la creación */
        auditManager.recordRefSetBinding(refSet, conceptSMTK, user);
    }

    @Override
    public void unbindConceptToRefSet(ConceptSMTK conceptSMTK, RefSet refSet, User user) {

        /* Se validan las pre-condiciones */
        new RefSetUnbindingBR().validatePreConditions();

        /* Se asocia la descripción al RefSet */
        refsetDAO.unbind(conceptSMTK, refSet);

        /* Se registra la creación */
        auditManager.recordRefSetUnbinding(refSet, conceptSMTK, user);
    }

    @Override
    public void invalidate(RefSet refSet, User user) {

        /* Se validan las pre-condiciones */
        new RefSetInvalidationBR().validatePreConditions();

        /* Se asocia la descripción al RefSet */
        refSet.setValidityUntil(new Timestamp(currentTimeMillis()));
        refsetDAO.update(refSet);

        /* Se registra la creación */
        auditManager.recordRefSetInvalidate(refSet, user);
    }

    @Override
    public List<RefSet> getAllRefSets() {

        return refsetDAO.getReftsets();
    }
}
