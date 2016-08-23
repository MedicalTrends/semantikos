package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ChangeType;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;

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
    public void recordNewConcept(ConceptSMTK conceptSMTK, IUser user);

    /**
     * Este método es responsable de registrar en el log de auditoría la actualización del concepto, por el usuario.
     * Este método solo registra la creación del concepto, y no de cada una de sus relaciones o descripciones.
     *
     * TODO: Determinar qué cosas hay que auditar al momento de crear un nuevo concepto.
     *
     * @param conceptSMTK El concepto que se creo.
     * @param user        El usuario que creó el concepto.
     */
    public void recordUpdateConcept(ConceptSMTK conceptSMTK, IUser user);

    /**
     * Este método es responsable de obtener y agrupar en una lista todos los tipos de cambios existentes.
     *
     * @return Una <code>List</code> con los tipos de cambio.
     */
    public List<ChangeType> getAllChangeTypes();

}
