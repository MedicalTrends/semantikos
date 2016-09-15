package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.kernel.components.CategoryManager;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías on 8/29/16.
 */
public class DescriptionCreationBR {

    /**
     * Este método es responsable de aplicar las reglas de negocio asociadas a la creación de una Descripción.
     *
     * @param concept         El concepto al cual se asocia la descripción.
     * @param term            El término que contiene la descripción a crear.
     * @param type            El tipo de la descripción.
     * @param user            El usuario que quiere crear la descripción.
     * @param categoryManager El componente de negocio que encapsula las reglas referentes a las categorías.
     */
    public void applyRules(ConceptSMTK concept, String term, DescriptionType type, User user, CategoryManager categoryManager) {

        /* Se verifican las invariantes */
        Description description = new Description(concept, term, type);
        new DescriptionInvariantsBR().invariants(description);
    }

    /**
     * Este método aplica las pre-condiciones a la creación.
     *
     * @param concept         El concepto al cual se agrega la descripción.
     * @param term            El término que se desea agregar.
     * @param categoryManager El Manager.
     */
    public void validatePreCondition(ConceptSMTK concept, String term, CategoryManager categoryManager) {
        brDescriptionCreation001(concept, term, categoryManager);
    }

    /**
     * Esta invariante implementa la regla de negocio BR-DES-009. Esta regla consiste en que un término es único en una
     * categoría.
     *
     * @param concept         El concepto al cual se asocia la descripción.
     * @param term            El término que contiene la descripción a crear.
     * @param categoryManager El Manager para realizar la verificación a nivel de la BDD.
     */
    private void brDescriptionCreation001(ConceptSMTK concept, String term, CategoryManager categoryManager) {
        Category category = concept.getCategory();

        if (categoryManager.categoryContains(category, term)) {
            throw new BusinessRuleException("Un término sólo puede existir una vez en una categoría.");
        }
    }
}
