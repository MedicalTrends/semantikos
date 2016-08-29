package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.audit.AuditActionType;
import cl.minsal.semantikos.model.audit.ConceptAuditAction;
import cl.minsal.semantikos.model.relationships.Relationship;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Local
public interface AuditManagerInterface {


    /**
     * Este método es responsable de registrar en el log de auditoría la creación del concepto, por el usuario.
     * Este método solo registra la creación del concepto, y no de cada una de sus relaciones o descripciones.
     *
     * TODO: Determinar qué cosas hay que auditar al momento de crear un nuevo concepto.
     *
     * @param conceptSMTK El concepto que se creo.
     * @param user        El usuario que creó el concepto.
     */
    public void recordNewConcept(ConceptSMTK conceptSMTK, User user);

    /**
     * Este método es responsable de registrar en el log de auditoría la actualización del concepto, por el usuario.
     * Este método solo registra la creación del concepto, y no de cada una de sus relaciones o descripciones.
     *
     * TODO: Determinar qué cosas hay que auditar al momento de crear un nuevo concepto.
     *
     * @param conceptSMTK El concepto que se creo.
     * @param user        El usuario que creó el concepto.
     */
    public void recordUpdateConcept(ConceptSMTK conceptSMTK, User user);

    /**
     * Este método es responsable de registrar en el log de auditoría la el traslado de un concepto.
     *
     * @param sourceConcept El concepto en donde se encuentra la descripción inicialmente.
     * @param targetConcept El concepto al cual se quiere mover la descripción.
     * @param description   La descripción que se desea trasladar.
     */
    public void recordDescriptionMovement(ConceptSMTK sourceConcept, ConceptSMTK targetConcept, Description description /* TODO: Agregar el usuario */);

    /**
     * Este método es responsable de registrar en el log de auditoría la el traslado de un concepto.
     *
     * @param sourceConcept El concepto en donde se encuentra la descripción inicialmente.
     * @param user          El usuario que publicó el concepto.
     */
    public void recordConceptPublished(ConceptSMTK sourceConcept, User user);

    /**
     * Este método es responsable de registrar en el historial el cambio de la descripción
     * <code>originalDescription</code>.
     *
     * @param conceptSMTK         El concepto al cual pertenece la descripción actualizada.
     * @param originalDescription La descripción original, sin el cambio.
     * @param user                El usuario que realizó la actualización.
     */
    public void recordFavouriteDescriptionUpdate(ConceptSMTK conceptSMTK, Description originalDescription, User user);

    /**
     * Este método es responsable de registrar en el historial el cambio de categoría de un concepto.
     *
     * @param conceptSMTK      El concepto cuya categoría cambia.
     * @param originalCategory La categoría original, antes del cambio.
     * @param user             El usuario que realiza el cambio.
     */
    public void recordConceptCategoryChange(ConceptSMTK conceptSMTK, Category originalCategory, User user);

    /**
     * Este método es responsable de registrar en el historial el cambio de una relación atributo del concepto.
     *
     * @param conceptSMTK          El concepto cuya relación se actualizó.
     * @param originalRelationship La relación antes de la modificación.
     * @param user                 El usuario que realizó la modificación.
     */
    public void recordAttributeChange(ConceptSMTK conceptSMTK, Relationship originalRelationship, User user);

    /**
     * Este método es responsable de obtener y agrupar en una lista todos los tipos de cambios existentes.
     *
     * @return Una <code>List</code> con los tipos de cambio.
     */
    public List<AuditActionType> getAllAuditActionTypes();

    /**
     * Este método es responsable de recuperar y retornar en una lista los últimos <code>numberOfChanges</code> cambios
     * que ha tenido un concepto.
     *
     * @param conceptSMTK     El concepto cuyos cambios se desean recuperar.
     * @param numberOfChanges La cantidad de cambios que se desea recuperar (ordenados de más reciente a menos
     *                        reciente).
     * @param changes         Indica si se desean las acciones auditables registradas que son cambios
     *
     * @return Una lista con los últimos <code>numberOfChanges</code> realizados sobre el concepto
     * <code>conceptSMTK</code>
     */
    public List<ConceptAuditAction> getConceptAuditActions(ConceptSMTK conceptSMTK, int numberOfChanges, boolean changes);

}
