package cl.minsal.semantikos.model.validations;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Created by root on 25-08-16.
 */

//Linking the validator I had shown above.
@Constraint(validatedBy = {RelationshipValidator.class})
//This constraint annotation can be used only on fields and method parameters.
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RelationshipConstraint {
    //The message to return when the instance of MyAddress fails the validation.
    String message() default "Invalid Relationship";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
