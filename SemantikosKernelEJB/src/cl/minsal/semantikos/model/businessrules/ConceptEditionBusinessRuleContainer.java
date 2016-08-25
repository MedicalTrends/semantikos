package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías on 8/25/16.
 */
public class ConceptEditionBusinessRuleContainer implements BusinessRulesContainer {

    public void apply(ConceptSMTK conceptSMTK, IUser IUser) throws BusinessRuleException {
        //TODO: Implement me
    }

    /**
     * Esta regla de negocio (no levantada aun) indica que no es posible modificar el campo CONCEPT_ID.
     *
     * @param conceptSMTK El concepto que se desea modificar.
     * @param user        El usuario que modifica el concepto.
     */
    protected void br101ConceptIDEdition(ConceptSMTK conceptSMTK, IUser user) {

        /* Nunca se puede modificar, asi que siempre lanza la excepción */
        throw new BusinessRuleException("No es posible modificar el CONCEPT_ID del concepto " + conceptSMTK.toString() + ", por el usuario " + user.toString());
    }
}
