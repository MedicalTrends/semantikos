package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Profile;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

/**
 * @author Andrés Farías
 */
public class ConceptCreationBusinessRuleContainer implements BusinessRulesContainer {

    private static final Logger logger = LoggerFactory.getLogger(ConceptCreationBusinessRuleContainer.class);

    protected static final String CATEGORY_FARMACOS_SUSTANCIAS_NAME = "Fármacos - Sustancias";
    protected static final String CATEGORY_FARMACOS_MEDICAMENTO_BASICO_NAME = "Fármacos - Medicamento Básico";
    protected static final String CATEGORY_FARMACOS_MEDICAMENTO_CLINICO_NAME = "Fármacos – Medicamento Clínico";
    protected static final String CATEGORY_FARMACOS_MEDICAMENTO_CLINICO_CON_ENVASE_NAME= "Fármacos – Medicamento Clínico con Envase";

    private Profile designerProfile = createProfile("Diseñador");
    private Profile modelerProfile = createProfile("Modelador");

    @Override
    public void apply(@NotNull ConceptSMTK conceptSMTK, User user) throws BusinessRuleException {

        /* Reglas que aplican para todas las categorías */
        br101HasFSN(conceptSMTK);

        /* Las reglas de negocio dependen de la categoría del concepto */
        switch (conceptSMTK.getCategory().getName()) {

            case CATEGORY_FARMACOS_SUSTANCIAS_NAME:
                logger.debug("Aplicando reglas de negocio para GUARDADO para categoría Fármacos - Sustancias.");
                br001creationRights(conceptSMTK, user);
                break;

            case CATEGORY_FARMACOS_MEDICAMENTO_BASICO_NAME:
                logger.debug("Aplicando reglas de negocio para GUARDADO para categoría Fármacos - Medicamento Básico.");
                br002creationRights(conceptSMTK, user);
                break;

            case CATEGORY_FARMACOS_MEDICAMENTO_CLINICO_NAME:
                logger.debug("Aplicando reglas de negocio para GUARDADO para categoría Fármacos - Medicamento Clínico.");
                br003creationRights(conceptSMTK, user);
                break;

            case CATEGORY_FARMACOS_MEDICAMENTO_CLINICO_CON_ENVASE_NAME:
                logger.debug("Aplicando reglas de negocio para GUARDADO para categoría Fármacos – Medicamento Clínico con Envase.");
                br004creationRights(conceptSMTK, user);
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

        boolean isDesigner = user.getProfiles().contains(designerProfile);
        boolean isModeler = user.getProfiles().contains(modelerProfile);

        /* El usuario debe ser modelador o diseñador */
        if (!(isDesigner || isModeler)) {
            throw new BusinessRuleException("Solo usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.");
        }

    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @param conceptSMTK El concepto a crear ser creado.
     * @param user        El usuario que realiza la acción.
     */
    protected void br002creationRights(ConceptSMTK conceptSMTK, User user) {

        /* Solo aplica a farmacos - medicamento básico */
        Category farmacosMedicamentoBasicoCategory = new Category();
        farmacosMedicamentoBasicoCategory.setName(CATEGORY_FARMACOS_MEDICAMENTO_BASICO_NAME);
        if (!conceptSMTK.belongsTo(farmacosMedicamentoBasicoCategory)) return;

        boolean isDesigner = user.getProfiles().contains(designerProfile);
        boolean isModeler = user.getProfiles().contains(modelerProfile);

        /* El usuario debe ser modelador o diseñador */
        if (!(isDesigner || isModeler)) {
            throw new BusinessRuleException("Solo usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.");
        }
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @param conceptSMTK El concepto a crear ser creado.
     * @param user        El usuario que realiza la acción.
     */
    protected void br003creationRights(ConceptSMTK conceptSMTK, User user) {

        /* Solo aplica a farmacos - medicamento básico */
        Category farmMedClinic = new Category();
        farmMedClinic.setName(CATEGORY_FARMACOS_MEDICAMENTO_CLINICO_NAME);
        if (!conceptSMTK.belongsTo(farmMedClinic)) return;

        boolean isDesigner = user.getProfiles().contains(designerProfile);
        boolean isModeler = user.getProfiles().contains(modelerProfile);

        /* El usuario debe ser modelador o diseñador */
        if (!(isDesigner || isModeler)) {
            throw new BusinessRuleException("Solo usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.");
        }
    }


    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @param conceptSMTK El concepto a crear ser creado.
     * @param user        El usuario que realiza la acción.
     */
    protected void br004creationRights(ConceptSMTK conceptSMTK, User user) {

        /* Solo aplica a Fármacos – Medicamento Clínico con Envase */
        Category farmMedClinicEnv = new Category();
        farmMedClinicEnv.setName(CATEGORY_FARMACOS_MEDICAMENTO_CLINICO_CON_ENVASE_NAME);
        if (!conceptSMTK.belongsTo(farmMedClinicEnv)) return;

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

    private Profile createProfile(String profileName) {
        Profile designerProfile = new Profile();
        designerProfile.setName(profileName);
        return designerProfile;
    }
}
