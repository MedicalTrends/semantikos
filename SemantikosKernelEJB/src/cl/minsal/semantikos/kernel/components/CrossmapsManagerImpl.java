package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.CrossmapsDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmaps;
import cl.minsal.semantikos.model.crossmaps.Crossmaps;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.CrossMapCreationBR;
import cl.minsal.semantikos.model.businessrules.CrossMapRemovalBR;
import cl.minsal.semantikos.model.crossmaps.CrossmapSetMember;

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
    public Crossmaps createCrossMap(Crossmaps crossmaps, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapCreationBR().applyRules(crossmaps, user);

        /* TODO: Se realiza la creación */

        /* Se registra en el historial */
        if (crossmaps.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapCreation(crossmaps, user);
        }

        /* Se retorna la instancia creada */
        return crossmaps;
    }

    @Override
    public Crossmaps removeCrossMap(Crossmaps crossmaps, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapRemovalBR().applyRules(crossmaps, user);

        /* TODO: Se realiza la eliminación */

        /* Se registra en el historial */
        if (crossmaps.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapRemoval(crossmaps, user);
        }

        /* Se retorna la instancia creada */
        return crossmaps;
    }

    @Override
    public List<Crossmaps> getCrossmaps(ConceptSMTK conceptSMTK) {

        List<Crossmaps> allCrossmapses = this.getDirectCrossmaps(conceptSMTK);
        allCrossmapses.addAll(this.getIndirectCrossmaps(conceptSMTK));

        return allCrossmapses;
    }

    @Override
    public List<Crossmaps> getDirectCrossmaps(ConceptSMTK conceptSMTK) {
        if (conceptSMTK.isPersistent()){
            return crossmapsDAO.getDirectCrossmapsByIdConcept(conceptSMTK.getId());
        } else{
            return crossmapsDAO.getDirectCrossmapsByConceptID(conceptSMTK.getConceptID());
        }
    }

    @Override
    public List<Crossmaps> getIndirectCrossmaps(ConceptSMTK conceptSMTK) {
        if (conceptSMTK.isPersistent()){
            return crossmapsDAO.getIndirectCrossmapsByIdConcept(conceptSMTK.getId());
        } else{
            return crossmapsDAO.getIndirectCrossmapsByConceptID(conceptSMTK.getConceptID());
        }

    }

    @Override
    public DirectCrossmaps bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember) {
        return crossmapsDAO.bindConceptSMTKToCrossmapSetMember(conceptSMTK, crossmapSetMember);
    }

    @Override
    public void remove(Crossmaps crossmaps) {

    }
}
