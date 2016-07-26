package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
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

    public ConceptSMTK newConcept(Category category, String termino);

    public List<ConceptSMTK> findConceptByPatternCategoryPageNumber(String pattern, Long[] category, int pageNumber, int pageSize);

    /**
     * Este método es responsable de buscar conceptos cuyo CONCEPT_ID o en descripciones de términos de conceptos, y
     * entregar los resultados de manera paginada.
     *
     * @param patter     El patrón de búsqueda.
     * @param categories   Las categorías a las que pertenecen los conceptos sobre los cuales se realiza la búsqueda.
     * @param pageNumber El número de página que se desea obtener.
     * @param pageSize   La cantidad de resultados por página.
     *
     * @return Una lista de conceptos (correspondiendo a la página solicitada), sin ningún orden particular, de los
     * conceptos que corresponden al criterio de búsqueda.
     */
    public List<ConceptSMTK> findConceptByConceptIDOrDescriptionCategoryPageNumber(String patter, Long[] categories, int pageNumber, int pageSize);

    public int getAllConceptCount(String Pattern, Long[] category);


}