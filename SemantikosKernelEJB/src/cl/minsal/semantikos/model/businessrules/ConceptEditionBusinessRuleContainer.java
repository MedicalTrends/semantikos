package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrés Farías on 8/25/16.
 */
public class ConceptEditionBusinessRuleContainer implements BusinessRulesContainer {

    private static final Logger logger = LoggerFactory.getLogger(ConceptEditionBusinessRuleContainer.class);

    public void apply(ConceptSMTK conceptSMTK, User IUser) throws BusinessRuleException {
    }

    public void preconditionsConceptEditionTag(ConceptSMTK conceptSMTK){
        brTagSMTK002UpdateTag(conceptSMTK);
    }

    /**
     * Esta regla de negocio (no levantada aun) indica que no es posible modificar el campo CONCEPT_ID.
     *
     * @param conceptSMTK El concepto que se desea modificar.
     * @param user        El usuario que modifica el concepto.
     */
    protected void br101ConceptIDEdition(ConceptSMTK conceptSMTK, User user) {

        /* Nunca se puede modificar, asi que siempre lanza la excepción */
        throw new BusinessRuleException("No es posible modificar el CONCEPT_ID del concepto " + conceptSMTK.toString() + ", por el usuario " + user.toString());
    }

    /**
     * Un concepto puede modificar su Tag Semántikos solo si se encuentra en Borrador.
     *
     * @param conceptSMTK El concepto cuyo tag smtk se quiere modificar
     */
    protected void brTagSMTK002UpdateTag(ConceptSMTK conceptSMTK) {

        if (conceptSMTK.isModeled()) {
            String brDesc = "No es posible modificar el Tag Semántiko de un concepto Modelado.";
            String conceptInfo = "Se intentó actualizar el concepto " + conceptSMTK.toString();

            logger.info(conceptInfo + "\n" + brDesc);
            throw new BusinessRuleException(brDesc + "\n" + conceptInfo);
        }
    }
}
