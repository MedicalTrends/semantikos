package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrés Farías
 */
public class SavingBusinessRuleContainer implements BusinessRulesContainer {

    private static final Logger logger = LoggerFactory.getLogger(SavingBusinessRuleContainer.class);

    @Override
    public void apply(ConceptSMTK conceptSMTK) throws BusinessRuleException {

        /* Reglas que aplican para todas las categorías */
        br01HasFSN(conceptSMTK);

        /* Las reglas de negocio dependen de la categoría del concepto */
        switch (conceptSMTK.getCategory().getName()) {

            case "Fármacos - Sustancias":
                logger.debug("Aplicando reglas de negocio para GUARDADO para categoría Fármacos - Sustancias.");
                br02SavingConceptAllCategories(conceptSMTK);
                break;
        }
    }

    private void br01HasFSN(ConceptSMTK conceptSMTK) {
        conceptSMTK.getDescriptionFSN();
    }

    private void br02SavingConceptAllCategories(ConceptSMTK conceptSMTK) {
        conceptSMTK.getDescriptionFavorite();
    }
}
