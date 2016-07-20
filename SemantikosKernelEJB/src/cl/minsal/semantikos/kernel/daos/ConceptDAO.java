package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.ConceptStateMachine;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */
@Local
public interface ConceptDAO {


    public List<ConceptSMTK> getConceptByPatternCategory(String[] Pattern, String[] Category, int pageNumber, int pageSize);
    public List<ConceptSMTK> getConceptByPatternOrConceptIDAndCategory(String PatternOrID, String[] Category, int pageNumber, int pageSize);
    public int getAllConceptCount(String[] Pattern, String[] category);
    public int getCountFindConceptID(String Pattern, String[] category);
}
