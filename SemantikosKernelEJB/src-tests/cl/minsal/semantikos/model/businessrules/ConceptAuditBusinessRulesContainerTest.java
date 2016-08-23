package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.UserNull;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConceptAuditBusinessRulesContainerTest {

    @Test(expected = BusinessRuleException.class)
    public void testApplyNotModeled() throws Exception {
        ConceptSMTK conceptSMTK = new ConceptSMTK();

        new ConceptAuditBusinessRulesContainer().apply(conceptSMTK, UserNull.getInstance());
    }
}