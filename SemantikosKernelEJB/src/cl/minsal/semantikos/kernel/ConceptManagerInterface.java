package cl.minsal.semantikos.kernel;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;

import javax.ejb.Local;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stk-des01 on 01-06-16.
 */
@Local
public interface ConceptManagerInterface {

    public ArrayList<Description> findDescriptionForPattern(String pattern);

    public String addConcept(String idCategory, boolean isValid);

    public int getIDConceptBy(int idDescription);

    public ConceptSMTK newConcept(int idCategory, String termino);

    public List<ConceptSMTK> findConceptByPatternCategoryPageNumber(String Pattern, String[] category, int pageNumber, int pageSize);

    public int getAllConceptCount(String Pattern, String[] category);

}
