package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmap;
import cl.minsal.semantikos.model.crossmaps.Crossmap;
import cl.minsal.semantikos.model.crossmaps.CrossmapSetMember;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías on 8/19/16.
 */
@Stateless
public class CrossmapsDAOImpl implements CrossmapsDAO {
    @Override
    public Crossmap getCrossMapByID(long idCrossMap) {
        return null;
    }

    @Override
    public List<Crossmap> getIndirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<Crossmap> getIndirectCrossmapsByConceptID(String conceptID) {
        return null;
    }

    @Override
    public List<Crossmap> getDirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<Crossmap> getDirectCrossmapsByConceptID(String conceptID) {
        return null;
    }

    @Override
    public DirectCrossmap bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember) {
        return null;
    }
}
