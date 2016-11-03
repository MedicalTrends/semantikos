package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmaps;
import cl.minsal.semantikos.model.crossmaps.Crossmaps;
import cl.minsal.semantikos.model.crossmaps.CrossmapSetMember;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías on 8/19/16.
 */
@Stateless
public class CrossmapsDAOImpl implements CrossmapsDAO {
    @Override
    public Crossmaps getCrossMapByID(long idCrossMap) {
        return null;
    }

    @Override
    public List<Crossmaps> getIndirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<Crossmaps> getIndirectCrossmapsByConceptID(String conceptID) {
        return null;
    }

    @Override
    public List<Crossmaps> getDirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<Crossmaps> getDirectCrossmapsByConceptID(String conceptID) {
        return null;
    }

    @Override
    public DirectCrossmaps bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember) {
        return null;
    }
}
