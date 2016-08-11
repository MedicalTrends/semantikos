package cl.minsal.semantikos.model.exceptions;

import com.sun.javafx.beans.annotations.NonNull;

/**
 * @author Andrés Farias
 */
public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(@NonNull String errorMessage) {
        super(errorMessage);
    }
}
