package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.CrossmapsDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.CrossMap;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.CrossMapCreationBR;
import cl.minsal.semantikos.model.businessrules.CrossMapRemovalBR;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías on 8/30/16.
 */
@Stateless
public class CrossmapsManagerImpl implements CrossmapsManager {

    @EJB
    private AuditManager auditManager;

    @EJB
    private CrossmapsDAO crossmapsDAO;

    @Override
    public CrossMap createCrossMap(CrossMap crossMap, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapCreationBR().applyRules(crossMap, user);

        /* TODO: Se realiza la creación */

        /* Se registra en el historial */
        if (crossMap.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapCreation(crossMap, user);
        }

        /* Se retorna la instancia creada */
        return crossMap;
    }

    @Override
    public CrossMap removeCrossMap(CrossMap crossMap, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapRemovalBR().applyRules(crossMap, user);

        /* TODO: Se realiza la eliminación */

        /* Se registra en el historial */
        if (crossMap.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapRemoval(crossMap, user);
        }

        /* Se retorna la instancia creada */
        return crossMap;
    }

    @Override
    public List<CrossMap> getCrossmaps(ConceptSMTK conceptSMTK) {
        if (conceptSMTK.isPersistent()){
        return crossmapsDAO.getCrossmapsByIdConcept(conceptSMTK.getId());
        } else{
            return crossmapsDAO.getCrossmapsByConceptID(conceptSMTK.getConceptID());
        }
    }
}
