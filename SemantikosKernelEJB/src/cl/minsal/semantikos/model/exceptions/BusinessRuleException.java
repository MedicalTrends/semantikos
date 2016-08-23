package cl.minsal.semantikos.model.exceptions;


import cl.minsal.semantikos.model.ConceptSMTK;

/**
 * @author Andrés Farias
 */
public class BusinessRuleException extends RuntimeException{

    /* El concepto que ha violado la regla de negocio */
    private ConceptSMTK conceptSMTK;

    public BusinessRuleException(String errorMessage) {
        super(errorMessage);
    }

    public BusinessRuleException(ConceptSMTK conceptSMTK, String errorMessage) {
        this(errorMessage);
        this.conceptSMTK = conceptSMTK;
    }
}
