package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías on 8/25/16.
 */
public class ConceptEditionBusinessRuleContainer implements BusinessRulesContainer {

    public void apply(ConceptSMTK conceptSMTK, User IUser) throws BusinessRuleException {
        //TODO: Implement me
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
}
