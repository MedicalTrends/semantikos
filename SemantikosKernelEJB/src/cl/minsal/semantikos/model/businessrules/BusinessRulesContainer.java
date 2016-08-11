package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías
 */
public interface BusinessRulesContainer {

    /**
     * Este método es responsable de aplicar reglas de negocio (asociadas a una clase de Reglas de Negocio)
     *
     * @param conceptSMTK El concepto sobre el cual se aplican las reglas de negocio.
     * @param user        El usuario que realiza operaciones sobre concepto.
     *
     * @throws BusinessRuleException Arrojada si el concepto viola una o más reglas de negocio.
     */
    public void apply(ConceptSMTK conceptSMTK, User user) throws BusinessRuleException;
}
