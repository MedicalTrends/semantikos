package cl.minsal.semantikos.model.exceptions;



/**
 * @author Andrés Farias
 */
public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException( String errorMessage) {
        super(errorMessage);
    }
}
