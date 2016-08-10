package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.junit.Test;

public class SavingBusinessRuleContainerTest {

    private SavingBusinessRuleContainer savingBusinessRuleContainer = new SavingBusinessRuleContainer();

    @Test(expected = BusinessRuleException.class)
    public void testApply() throws Exception {

        ConceptSMTK conceptSMTK = new ConceptSMTK();
        savingBusinessRuleContainer.apply(conceptSMTK);
    }

    @Test(expected = BusinessRuleException.class)
    public void testApply02() throws Exception {

        Category substancesCategory = new Category();
        substancesCategory.setName("Fármacos - Sustancias");

        ConceptSMTK conceptSMTK = new ConceptSMTK();
        conceptSMTK.setCategory(substancesCategory);

        DescriptionType fsnDT = DescriptionTypeFactory.getInstance().getFSNDescriptionType();
        Description fsn = new Description("FSN", fsnDT);
        conceptSMTK.addDescription(fsn);

        savingBusinessRuleContainer.apply(conceptSMTK);
    }
}