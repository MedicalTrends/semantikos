package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías on 8/25/16.
 */
public class DescriptionEditionBR implements BusinessRulesContainer {


    public void applyRules(Description original, Description changed) {
        brDescriptionEdition001(original, changed);
    }

    private void brDescriptionEdition001(Description original, Description changed) {
        if (!original.getDescriptionId().equalsIgnoreCase(changed.getDescriptionId())) {
            throw new BusinessRuleException("Los CONCEPT_ID de las descripciones no coinciden.");
        }
    }
}
