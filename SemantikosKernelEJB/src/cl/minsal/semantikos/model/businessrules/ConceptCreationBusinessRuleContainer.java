package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Profile;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrés Farías
 */
public class ConceptCreationBusinessRuleContainer implements BusinessRulesContainer {

    private static final Logger logger = LoggerFactory.getLogger(ConceptCreationBusinessRuleContainer.class);

    private static final String CATEGORY_FARMACOS_SUSTANCIAS_NAME = "Fármacos - Sustancias";

    @Override
    public void apply(ConceptSMTK conceptSMTK, User user) throws BusinessRuleException {

        /* Reglas que aplican para todas las categorías */
        br101HasFSN(conceptSMTK);

        /* Las reglas de negocio dependen de la categoría del concepto */
        switch (conceptSMTK.getCategory().getName()) {

            case CATEGORY_FARMACOS_SUSTANCIAS_NAME:
                logger.debug("Aplicando reglas de negocio para GUARDADO para categoría Fármacos - Sustancias.");
                br001creationRights(conceptSMTK, user);
                break;
        }
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @param conceptSMTK El concepto a crear ser creado.
     * @param user        El usuario que realiza la acción.
     */
    protected void br001creationRights(ConceptSMTK conceptSMTK, User user) {

        /* Solo aplica a farmacos - sustancias */
        Category farmacosSustanciaCategory = new Category();
        farmacosSustanciaCategory.setName(CATEGORY_FARMACOS_SUSTANCIAS_NAME);
        if (!conceptSMTK.belongsTo(farmacosSustanciaCategory)) return;

        Profile designerProfile = new Profile();
        designerProfile.setName("Diseñador");

        Profile modelerProfile = new Profile();
        modelerProfile.setName("Modelador");

        boolean isDesigner = user.getProfiles().contains(designerProfile);
        boolean isModeler = user.getProfiles().contains(modelerProfile);

        /* El usuario debe ser modelador o diseñador */
        if (!(isDesigner || isModeler)) {
            throw new BusinessRuleException("Solo usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.");
        }

    }

    private void br101HasFSN(ConceptSMTK conceptSMTK) {
        conceptSMTK.getDescriptionFSN();
    }

    private void br102SavingConceptAllCategories(ConceptSMTK conceptSMTK) {
        conceptSMTK.getDescriptionFavorite();
    }
}
