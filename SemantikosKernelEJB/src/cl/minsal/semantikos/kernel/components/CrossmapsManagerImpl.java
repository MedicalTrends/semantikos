package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.CrossmapsDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmap;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.CrossMapCreationBR;
import cl.minsal.semantikos.model.businessrules.CrossMapRemovalBR;
import cl.minsal.semantikos.model.crossmaps.Crossmap;
import cl.minsal.semantikos.model.crossmaps.CrossmapSet;
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
    public Crossmap createCrossMap(Crossmap crossmap, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapCreationBR().applyRules(crossmap, user);

        /* TODO: Se realiza la creación */

        /* Se registra en el historial */
        if (crossmap.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapCreation(crossmap, user);
        }

        /* Se retorna la instancia creada */
        return crossmap;
    }

    @Override
    public Crossmap removeCrossMap(Crossmap crossmap, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapRemovalBR().applyRules(crossmap, user);

        /* TODO: Se realiza la eliminación */

        /* Se registra en el historial */
        if (crossmap.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapRemoval(crossmap, user);
        }

        /* Se retorna la instancia creada */
        return crossmap;
    }

    @Override
    public List<Crossmap> getCrossmaps(ConceptSMTK conceptSMTK) {

        List<Crossmap> allCrossmapses = this.getDirectCrossmaps(conceptSMTK);
        allCrossmapses.addAll(this.getIndirectCrossmaps(conceptSMTK));

        return allCrossmapses;
    }

    @Override
    public List<CrossmapSet> getCrossmapSets() {
        //TODO: Terminar esto.
        return null;
    }

    @Override
    public List<Crossmap> getDirectCrossmaps(ConceptSMTK conceptSMTK) {
        if (conceptSMTK.isPersistent()) {
            return crossmapsDAO.getDirectCrossmapsByIdConcept(conceptSMTK.getId());
        } else {
            return crossmapsDAO.getDirectCrossmapsByConceptID(conceptSMTK.getConceptID());
        }
    }

    @Override
    public List<Crossmap> getIndirectCrossmaps(ConceptSMTK conceptSMTK) {
        if (conceptSMTK.isPersistent()) {
            return crossmapsDAO.getIndirectCrossmapsByIdConcept(conceptSMTK.getId());
        } else {
            return crossmapsDAO.getIndirectCrossmapsByConceptID(conceptSMTK.getConceptID());
        }

    }

    @Override
    public DirectCrossmap bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember) {
        return crossmapsDAO.bindConceptSMTKToCrossmapSetMember(conceptSMTK, crossmapSetMember);
    }

    @Override
    public void remove(Crossmap crossmap) {
        //TODO: Terminar esto
    }

    @Override
    public List<CrossmapSetMember> findByPattern(CrossmapSet crossmapSet, String pattern) {
        //TODO: Terminar esto
        return null;
    }
}
