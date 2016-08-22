package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.IUser;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Local
public interface ConceptManagerInterface {

    public ArrayList<Description> findDescriptionForPattern(String pattern);

    public String addConcept(String idCategory, boolean isValid);

    /**
     * Este método es responsable de recuperar el concepto con DESCRIPTION_ID.
     *
     * @param descriptionID El DESCRIPTION_ID (valor de negocio) del concepto que se desea recuperar.
     *
     * @return Un objeto fresco de tipo <code>ConceptSMTK</code> con el Concepto solicitado.
     */
    public ConceptSMTK getConceptByCONCEPT_ID(String descriptionID);

    /**
     * Este método es responsable de recuperar el concepto con id (de BD)
     *
     * @param id El identificador de BD del concepto.
     *
     * @return Un concepto fresco de tipo <code>ConceptSMTK</code>.
     */
    public ConceptSMTK getConceptByID(long id);

    public List<ConceptSMTK> findConceptByPatternCategoryPageNumber(String pattern, Long[] category, int pageNumber, int pageSize);

    /**
     * Este método es responsable de buscar conceptos cuyo CONCEPT_ID o en descripciones de términos de conceptos, y
     * entregar los resultados de manera paginada.
     *
     * @param patter     El patrón de búsqueda.
     * @param categories Las categorías a las que pertenecen los conceptos sobre los cuales se realiza la búsqueda.
     * @param pageNumber El número de página que se desea obtener.
     * @param pageSize   La cantidad de resultados por página.
     *
     * @return Una lista de conceptos (correspondiendo a la página solicitada), sin ningún orden particular, de los
     * conceptos que corresponden al criterio de búsqueda.
     */
    public List<ConceptSMTK> findConceptByConceptIDOrDescriptionCategoryPageNumber(String patter, Long[] categories, int pageNumber, int pageSize);

    public int getAllConceptCount(String pattern, Long[] categories);

    /**
     * Este método es responsable de persistir un concepto que no se encuentra persistido.
     *
     * @param conceptSMTK El concepto a persistir.
     */
    void persist(@NotNull ConceptSMTK conceptSMTK, IUser user);

    /**
     * Este método es responsable de actualizar el estado de la entidad en la base de datos.
     *
     * @param conceptSMTK El concepto cuyo estado se actualizará en la base de datos.
     *
     * @return Una copia fresca del objeto actualizado.
     */
    public ConceptSMTK merge(ConceptSMTK conceptSMTK);

    /**
     * Método encargado de generar el concept ID
     * @return retorna un String con el Concept ID generado
     */
    public String generateConceptId();

}