package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.relationships.Relationship;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Local
public interface ConceptManagerInterface {


    /**
     * Este método es responsable de persistir un concepto que no se encuentra persistido. Esta acción, de
     * persistencia,
     * queda registrado como una actividad de auditoría.
     *
     * @param conceptSMTK El concepto a persistir.
     * @param user        El usuario que persiste el concepto.
     */
    public void persist(@NotNull ConceptSMTK conceptSMTK, User user);

    /**
     * Este método es responsable de cambiar el estado de publicación del concepto.
     * @param conceptSMTK El concepto cuyo estado de publicación ha cambiado.
     * @param user El usuario que realizó el cambio.
     */
    public void publish(@NotNull ConceptSMTK conceptSMTK, User user);

    /**
     * Este método es responsable de recuperar el concepto con DESCRIPTION_ID.
     *
     * @param conceptId El DESCRIPTION_ID (valor de negocio) del concepto que se desea recuperar.
     *
     * @return Un objeto fresco de tipo <code>ConceptSMTK</code> con el Concepto solicitado.
     */
    public ConceptSMTK getConceptByCONCEPT_ID(String conceptId);

    /**
     * Este método es responsable de recuperar el concepto con id (de BD)
     *
     * @param id El identificador de BD del concepto.
     *
     * @return Un concepto fresco de tipo <code>ConceptSMTK</code>.
     */
    public ConceptSMTK getConceptByID(long id);

    /**
     * Este método es responsable de buscar conceptos cuyo CONCEPT_ID o en descripciones de términos de conceptos, y
     * entregar los resultados de manera paginada.
     *
     * @param patternOrConceptID El patrón de búsqueda.
     * @param categories         Las categorías a las que pertenecen los conceptos sobre los cuales se realiza la
     *                           búsqueda.
     * @param pageNumber         El número de página que se desea obtener.
     * @param pageSize           La cantidad de resultados por página.
     *
     * @return Una lista de conceptos (correspondiendo a la página solicitada), sin ningún orden particular, de los
     * conceptos que corresponden al criterio de búsqueda.
     */
    public List<ConceptSMTK> findConceptBy(String patternOrConceptID, Long[] categories, int pageNumber, int pageSize);

    /**
     * Método encargado de realizar la búsqueda de conceptos por patron, en caso de no encontrar un "Perfect Match" por
     * la cadena de texto entregada,
     * realiza un truncate match, el que consiste en cortar cada palabra de la cadena de texto en las tres primeras
     * letras para luego realizar la búsqueda
     * nuevamente
     *
     * @param pattern cadena de texto
     *
     * @return retorna lista de conceptos
     */
    public List<ConceptSMTK> findConceptBy(String pattern);

    /**
     * Este método se encarga de entregar la cantidad de conceptos según patron y categoría
     *
     * @param pattern    patrón de búsqueda
     * @param categories arreglo de idś de categorías
     *
     * @return retorna un entero con la cantidad
     */
    public int countConceptBy(String pattern, Long[] categories);

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
     *
     * @return retorna un String con el Concept ID generado
     */
    public String generateConceptId();

    /**
     * Este método es responsable de recuperar todas las descripciones (vigentes) del concepto.
     *
     * @param concept El concepto cuyas descripciones se quieren recuperar.
     *
     * @return Una lista de Description vigentes asociadas al <code>concept</code>
     */
    public List<Description> getDescriptionsBy(ConceptSMTK concept);

    /**
     * Este método es responsable de cargar las relaciones del concepto.
     *
     * @param concept El concepto cuyas relaciones son actualizadas.
     *
     * @return La lista de relaciones actualizadas (que ya están asociadas al objeto <code>concepto</code>.
     */
    public List<Relationship> loadRelationships(ConceptSMTK concept);


}