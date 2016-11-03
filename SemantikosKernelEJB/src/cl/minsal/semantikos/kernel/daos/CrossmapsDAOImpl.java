package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.CrossMap;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías on 8/19/16.
 */
@Stateless
public class CrossmapsDAOImpl implements CrossmapsDAO {
    @Override
    public CrossMap getCrossMapByID(long idCrossMap) {
        return null;
    }

    @Override
    public List<CrossMap> getIndirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<CrossMap> getIndirectCrossmapsByConceptID(String conceptID) {
        return null;
    }

    @Override
    public List<CrossMap> getDirectCrossmapsByIdConcept(long id) {
        return null;
    }

    @Override
    public List<CrossMap> getDirectCrossmapsByConceptID(String conceptID) {
        return null;
    }
}
