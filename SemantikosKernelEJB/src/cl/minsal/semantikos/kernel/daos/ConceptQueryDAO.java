package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by BluePrints Developer on 22-09-2016.
 */

@Local
public interface ConceptQueryDAO {

    public List<ConceptSMTK> callQuery(ConceptQuery query);

}
