package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.kernel.components.CategoryManager;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 * @author Andrés Farías on 8/29/16.
 */
public class DescriptionCreationBR {


    @EJB
    private CategoryManager categoryManager;

    /**
     * TODO: Este método es responsable de aplicar las reglas de negocio asociadas a la creación de una Descripción.
     *
     * @param concept El concepto al cual se asocia la descripción.
     * @param term    El término que contiene la descripción a crear.
     * @param type    El tipo de la descripción.
     * @param user    El usuario que quiere crear la descripción.
     * @param categoryManager El compononente de negocio que encapsula las reglas referentes a las categorias.
     */
    public void applyRules(ConceptSMTK concept, String term, DescriptionType type, User user, CategoryManager categoryManager) {

        /* Se verifican las invariantes */
        Description description = new Description(concept, term, type);
        new DescriptionInvariantsBR().invariants(description);

        /* Y las reglas de negocio para sobre la creación */
        brDescriptionCreation001(concept, term, categoryManager);
    }

    /**
     * Esta invariante implementa la regla de negocio BR-CAT-001. Esta regla consiste en que un término es único en una
     * categoría.
     *
     * @param concept El concepto al cual se asocia la descripción.
     * @param term    El término que contiene la descripción a crear.
     */
    private void brDescriptionCreation001(ConceptSMTK concept, String term, CategoryManager categoryManager) {
        Category category = concept.getCategory();

        if (categoryManager.categoryContains(category, term)){
            throw new BusinessRuleException("Un término sólo puede existir una vez en una categoría.");
        }
    }
}
