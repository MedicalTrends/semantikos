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
import cl.minsal.semantikos.model.crossmaps.IndirectCrossmap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
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
    public Crossmap create(DirectCrossmap directCrossmap, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapCreationBR().applyRules(directCrossmap, user);

        /* Se realiza la creación a nivel de persitencia*/
        crossmapsDAO.create(directCrossmap, user);

        /* Se registra en el historial */
        if (directCrossmap.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapCreation(directCrossmap, user);
        }

        /* Se retorna la instancia creada */
        return directCrossmap;
    }

    @Override
    public Crossmap remove(Crossmap crossmap, User user) {

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

        List<Crossmap> allCrossmaps = new ArrayList<>();
        allCrossmaps.addAll(this.getDirectCrossmaps(conceptSMTK));
        allCrossmaps.addAll(this.getIndirectCrossmaps(conceptSMTK));

        return allCrossmaps;
    }

    @Override
    public List<CrossmapSet> getCrossmapSets() {
        //TODO: Terminar esto.
        return null;
    }

    @Override
    public List<DirectCrossmap> getDirectCrossmaps(ConceptSMTK conceptSMTK) {
        ArrayList<DirectCrossmap> crossmaps = new ArrayList<>();
        if (conceptSMTK.isPersistent()) {
            crossmaps.addAll(crossmapsDAO.getDirectCrossmapsByIdConcept(conceptSMTK.getId()));
        } else {
            crossmaps.addAll(crossmapsDAO.getDirectCrossmapsByConceptID(conceptSMTK.getConceptID()));
        }

        return crossmaps;
    }

    @Override
    public List<IndirectCrossmap> getIndirectCrossmaps(ConceptSMTK conceptSMTK) {
        if (conceptSMTK.isPersistent()) {
            return crossmapsDAO.getIndirectCrossmapsByIdConcept(conceptSMTK.getId());
        } else {
            return crossmapsDAO.getIndirectCrossmapsByConceptID(conceptSMTK.getConceptID());
        }

    }

    @Override
    public DirectCrossmap bind(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember) {
        return crossmapsDAO.bindConceptSMTKToCrossmapSetMember(conceptSMTK, crossmapSetMember);
    }

    @Override
    public List<CrossmapSetMember> findByPattern(CrossmapSet crossmapSet, String pattern) {
        //TODO: Terminar esto
        return null;
    }
}
