package cl.minsal.semantikos.model.exceptions;

import com.sun.javafx.beans.annotations.NonNull;

/**
 * Created by andres on 8/5/16.
 */
public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(@NonNull String errorMessage) {
        super(errorMessage);
    }
}
