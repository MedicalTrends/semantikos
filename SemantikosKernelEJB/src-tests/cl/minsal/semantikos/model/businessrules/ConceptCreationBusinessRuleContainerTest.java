package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.junit.Test;

import static cl.minsal.semantikos.model.CategoryFactory.getNullCategory;
import static cl.minsal.semantikos.model.DescriptionTypeFactory.getInstance;
import static cl.minsal.semantikos.model.ProfileFactory.ADMINISTRATOR_PROFILE;
import static cl.minsal.semantikos.model.ProfileFactory.DESIGNER_PROFILE;
import static cl.minsal.semantikos.model.ProfileFactory.MODELER_PROFILE;
import static cl.minsal.semantikos.model.businessrules.ConceptCreationBusinessRuleContainer.*;

public class ConceptCreationBusinessRuleContainerTest {

    private ConceptCreationBusinessRuleContainer conceptCreationBRC = new ConceptCreationBusinessRuleContainer();

    private Category catFarSus = createCategory(CATEGORY_FARMACOS_SUSTANCIAS_NAME);
    private Category catFarMedBas = createCategory(CATEGORY_FARMACOS_MEDICAMENTO_BASICO_NAME);
    private Category catFarMedCli = createCategory(CATEGORY_FARMACOS_MEDICAMENTO_CLINICO_NAME);

    private User IUserDesigner = createUserProfile(DESIGNER_PROFILE);
    private User IUserModeler = createUserProfile(MODELER_PROFILE);
    private User IUserAdmin = createUserProfile(ADMINISTRATOR_PROFILE);

    @Test(expected = BusinessRuleException.class)
    public void testApply_FarmSubs_Designer() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK(catFarSus);
        conceptCreationBRC.apply(conceptSMTK, IUserDesigner);
    }

    @Test(expected = BusinessRuleException.class)
    public void testApply_FarmSubst_Modeler() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK(catFarSus);
        conceptCreationBRC.apply(conceptSMTK, IUserModeler);
    }

    @Test
    public void testApply_FarmsSubs() throws Exception {
        ConceptSMTK conceptSMTK = createBasicConceptSMTK(catFarSus);
        conceptCreationBRC.apply(conceptSMTK, IUserDesigner);
    }

    @Test
    public void testApply_FarmsMedBas() throws Exception {
        ConceptSMTK conceptSMTK = createBasicConceptSMTK(catFarMedBas);
        conceptCreationBRC.apply(conceptSMTK, IUserDesigner);
    }

    @Test(expected = BusinessRuleException.class)
    public void testApply_FarmsMedBas_Modeler() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK(catFarMedBas);

        conceptCreationBRC.apply(conceptSMTK, IUserModeler);
    }

    @Test(expected = BusinessRuleException.class)
    public void testApply_FarmsMedCli_Designer() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK(catFarMedBas);
        conceptCreationBRC.apply(conceptSMTK, IUserDesigner);
    }

    @Test(expected = BusinessRuleException.class)
    public void testApply_FarmsMedCli_Modeler() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK(catFarMedBas);

        conceptCreationBRC.apply(conceptSMTK, IUserModeler);
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @throws Exception
     */
    @Test
    public void testBR001_01() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK(catFarSus);
        conceptCreationBRC.br001creationRights(conceptSMTK, IUserDesigner);
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @throws Exception
     */
    @Test
    public void testBR001_02() throws Exception {
        conceptCreationBRC.br001creationRights(new ConceptSMTK(getNullCategory()), IUserModeler);
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoría.
     * Este test verifica si usuarios con otros roles no lo logran
     */
    @Test(expected = BusinessRuleException.class)
    public void testBR001_03() {
        /* perfil Admin no debe poder crear conceptos */
        conceptCreationBRC.br001creationRights(new ConceptSMTK(catFarSus), IUserAdmin);
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoría.
     *
     * @throws Exception
     */
    @Test
    public void testBR002_01() throws Exception {
        conceptCreationBRC.br002creationRights(new ConceptSMTK(catFarMedBas), IUserDesigner);
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoría.
     *
     * @throws Exception
     */
    @Test
    public void testBR002_02() throws Exception {
        conceptCreationBRC.br002creationRights(new ConceptSMTK(catFarMedBas), IUserModeler);
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoría.
     * Un usuario Admin debe arrojar una excepcion
     *
     * @throws Exception
     */
    @Test(expected = BusinessRuleException.class)
    public void testBR002_03() throws Exception {
        conceptCreationBRC.br002creationRights(new ConceptSMTK(catFarMedBas), IUserAdmin);
    }

    /**
     * BR003	Usuarios con rol de Diseñador o Modelador pueden crear conceptos de la categoría: Fármacos – Medicamento
     * Clínico
     *
     * @throws Exception
     */
    @Test
    public void testBR003_01() throws Exception {
        conceptCreationBRC.br003creationRights(new ConceptSMTK(catFarMedCli), IUserDesigner);
    }

    /**
     * BR003	Usuarios con rol de Diseñador o Modelador pueden crear conceptos de la categoría: Fármacos – Medicamento
     * Clínico
     *
     * @throws Exception
     */
    @Test
    public void testBR003_02() throws Exception {
        conceptCreationBRC.br003creationRights(new ConceptSMTK(catFarMedCli), IUserModeler);
    }

    /**
     * BR003	Usuarios con rol de Diseñador o Modelador pueden crear conceptos de la categoría: Fármacos – Medicamento
     * Clínico. Un usuario Admin debe arrojar una excepcion
     *
     * @throws Exception
     */
    @Test(expected = BusinessRuleException.class)
    public void testBR003_03() throws Exception {
        conceptCreationBRC.br003creationRights(new ConceptSMTK(catFarMedCli), IUserAdmin);
    }

    @Test
    public void testBR003_04() throws Exception {
        ConceptSMTK conceptSMTK = createBasicConceptSMTK(catFarMedCli);
        conceptCreationBRC.apply(conceptSMTK, IUserDesigner);
    }

    private ConceptSMTK createBasicConceptSMTK(Category category) {
        ConceptSMTK conceptSMTK = new ConceptSMTK(category);

        DescriptionType fsnDT = getInstance().getFSNDescriptionType();
        Description fsn = new Description("FSN", fsnDT);
        conceptSMTK.addDescription(fsn);

        DescriptionType favDT = getInstance().getFavoriteDescriptionType();
        Description fav = new Description("Preferida", favDT);
        conceptSMTK.addDescription(fav);

        return conceptSMTK;
    }

    private Category createCategory(String categoryName) {
        Category substancesCategory = new Category();
        substancesCategory.setName(categoryName);
        return substancesCategory;
    }

    /**
     * Este método es responsable de crear un usuario con perfil Diseñador.
     *
     * @return Un usuario fresco de perfil diseñador.
     */
    private User createUserProfile(Profile profile) {
        User user = new User();
        user.addProfile(profile);
        return user;
    }
}