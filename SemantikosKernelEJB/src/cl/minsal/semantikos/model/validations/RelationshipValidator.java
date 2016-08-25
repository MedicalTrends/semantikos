package cl.minsal.semantikos.model.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

/**
 * Created by root on 25-08-16.
 */
public class RelationshipValidator implements ConstraintValidator<RelationshipConstraint, ConceptSMTK> {

        @Override
        public void initialize(RelationshipConstraint constraintAnnotation) {
        }
        /**
         * 1. The address should not be null.
         * 2. The address should have all the data values specified.
         * 3. Pin code in the address should be of atleast 6 characters.
         * 4. The country in the address should be of atleast 4 characters.
         */

        @Override
        public boolean isValid(ConceptSMTK value, ConstraintValidatorContext context) {

            for (RelationshipDefinition relationshipDef : value.getCategory().getRelationshipDefinitions()) {
                if(value.getValidRelationshipsByRelationDefinition(relationshipDef).size()<relationshipDef.getMultiplicity().getLowerBoundary())
                    return false;
            }

            return true;
        }
    }
