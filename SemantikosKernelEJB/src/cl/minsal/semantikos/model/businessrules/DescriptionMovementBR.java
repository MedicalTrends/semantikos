package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static cl.minsal.semantikos.model.DescriptionType.*;

/**
 * @author Andrés Farías on 8/26/16.
 */
public class DescriptionMovementBR {

    private static final Logger logger = LoggerFactory.getLogger(DescriptionMovementBR.class);

    public void apply(ConceptSMTK sourceConcept, ConceptSMTK targetConcept, Description description) {

        /* Traslado de Descripciones abreviadas */
        brDescriptionTranslate001(sourceConcept, targetConcept, description);

        /* Descripciones que no se pueden trasladar */
        brDescriptionTranslate010(description);

        /* Estado de los conceptos */
        brDescriptionTranslate011(targetConcept);
    }

    /**
     * Los tipos de traslado pueden ser:
     * <ul>
     * <li>Trasladar una descripción desde un Concepto en Borrador a un Concepto Modelado</li>
     * <li>Trasladar una descripción desde un Concepto Modelado a otro Concepto Modelado</li>
     * </ul>
     * @param targetConcept El concepto al cual se traslada la descripción.
     */
    private void brDescriptionTranslate011(ConceptSMTK targetConcept) {
        if (!targetConcept.isModeled()){
            throw new BusinessRuleException("No se puede trasladar una descripción a un concepto Borrador");
        }
    }

    /**
     * Las descripciones a trasladar no pueden ser de tipo “FSN”, ni “Preferida”.
     *
     * @param description La descripción que se desea trasladar.
     */
    private void brDescriptionTranslate010(Description description) {
        if (description.getDescriptionType().equals(FSN) || description.getDescriptionType().equals(PREFERIDA)) {
            throw new BusinessRuleException("Las descripciones a trasladar no pueden ser de tipo “FSN”, ni “Preferida”.");
        }
    }

    /**
     * En el proceso de trasladar una Descripción de Tipo Descriptor “Abreviada”, si el concepto destino ya tiene
     * definida una descripción Abreviada, entonces la descripción a ser trasladada pasará como tipo descriptor
     * “General”.
     *
     * @param sourceConcept El concepto en donde se encuentra la descripción inicialmente.
     * @param targetConcept El concepto al cual se quiere mover la descripción.
     * @param description   La descripción que se desea trasladar.
     */
    private void brDescriptionTranslate001(ConceptSMTK sourceConcept, ConceptSMTK targetConcept, Description description) {

        /* Aplica si el tipo de la descripción es Abreviada */
        if (description.getDescriptionType().equals(ABREVIADA)) {

            /* Se busca en el concepto destino si posee alguna descripción del tipo Abreviada */
            List<Description> descriptions = targetConcept.getDescriptions();
            for (Description aTargetDescription : descriptions) {

                /* Si tiene una del tipo abreviada, la descripción a trasladar cambia de tipo a General */
                if (aTargetDescription.getDescriptionType().equals(ABREVIADA)) {
                    logger.info("Aplicando regla de negocio de Movimiento de traslados de descripciones Abreviadas");
                    description.setDescriptionType(GENERAL);
                }
            }
        }

    }
}
