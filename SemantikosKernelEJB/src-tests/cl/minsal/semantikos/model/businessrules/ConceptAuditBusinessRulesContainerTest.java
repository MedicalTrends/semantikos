package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.junit.Test;

import static cl.minsal.semantikos.model.CategoryFactory.getNullCategory;

public class ConceptAuditBusinessRulesContainerTest {

    @Test(expected = BusinessRuleException.class)
    public void testApplyNotModeled() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK(getNullCategory());

        new ConceptAuditBusinessRulesContainer().apply(conceptSMTK, User.getDummyUser());
    }
}