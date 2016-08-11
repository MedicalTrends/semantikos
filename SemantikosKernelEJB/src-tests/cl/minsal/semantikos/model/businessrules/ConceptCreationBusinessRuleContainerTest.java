package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.junit.Test;

public class ConceptCreationBusinessRuleContainerTest {

    private static final String ADMIN_PROFILE_NAME = "Admin";
    private static final String DESIGNER_PROFILE_NAME = "Diseñador";
    private static final String MODELER_PROFILE_NAME = "Modelador";

    private ConceptCreationBusinessRuleContainer conceptCreationBRC = new ConceptCreationBusinessRuleContainer();

    @Test(expected = BusinessRuleException.class)
    public void testApply() throws Exception {
        conceptCreationBRC.apply(new ConceptSMTK(), createUserProfile(DESIGNER_PROFILE_NAME));
    }


    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @throws Exception
     */
    @Test
    public void testBR001_01() throws Exception {
        conceptCreationBRC.br001creationRights(new ConceptSMTK(), createUserProfile(DESIGNER_PROFILE_NAME));
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @throws Exception
     */
    @Test
    public void testBR001_02() throws Exception {
        conceptCreationBRC.br001creationRights(new ConceptSMTK(), createUserProfile(MODELER_PROFILE_NAME));
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @throws Exception
     */
    @Test
    public void testBR002_01() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK();
        Category catFMB = new Category();
        conceptSMTK.setCategory(catFMB);
        conceptCreationBRC.br002creationRights(conceptSMTK, createUserProfile(DESIGNER_PROFILE_NAME));
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     *
     * @throws Exception
     */
    @Test
    public void testBR002_02() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK();
        Category catFMB = new Category();
        conceptSMTK.setCategory(catFMB);
        conceptCreationBRC.br002creationRights(conceptSMTK, createUserProfile(MODELER_PROFILE_NAME));
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     * Un usuario Admin debe arrojar una excepcion
     * @throws Exception
     */
    @Test(expected = BusinessRuleException.class)
    public void testBR002_03() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK();
        Category catFMB = new Category();
        conceptSMTK.setCategory(catFMB);
        conceptCreationBRC.br002creationRights(conceptSMTK, createUserProfile(ADMIN_PROFILE_NAME));
    }

    /**
     * Usuarios con rol de Diseñador o Modelador pueden crear conceptos de esta categoria.
     * Este test verifica si usuarios con otros roles no lo logran
     *
     */
    @Test(expected = BusinessRuleException.class)
    public void testBR001_03() {
        ConceptSMTK conceptSMTK = new ConceptSMTK();
        conceptSMTK.setCategory(createFarmacosSustanciasCategory());
        conceptCreationBRC.br001creationRights(conceptSMTK, createUserProfile(ADMIN_PROFILE_NAME));
    }

    @Test
    public void testApply02() throws Exception {

        Category substancesCategory = createFarmacosSustanciasCategory();

        ConceptSMTK conceptSMTK = new ConceptSMTK();
        conceptSMTK.setCategory(substancesCategory);

        DescriptionType fsnDT = DescriptionTypeFactory.getInstance().getFSNDescriptionType();
        Description fsn = new Description("FSN", fsnDT);
        conceptSMTK.addDescription(fsn);

        DescriptionType favDT = DescriptionTypeFactory.getInstance().getFavoriteDescriptionType();
        Description fav = new Description("Preferida", favDT);
        conceptSMTK.addDescription(fav);

        conceptCreationBRC.apply(conceptSMTK, createUserProfile(DESIGNER_PROFILE_NAME));
    }

    private Category createFarmacosSustanciasCategory() {
        Category substancesCategory = new Category();
        substancesCategory.setName("Fármacos - Sustancias");
        return substancesCategory;
    }

    /**
     * Este método es responsable de crear un usuario con perfil Diseñador.
     *
     * @return Un usuario fresco de perfil diseñador.
     */
    private User createUserProfile(String profileName) {
        User user = new User();
        Profile profile = new Profile();
        profile.setName(profileName);
        user.addProfile(profile);
        return user;
    }
}