package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.AuditDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.audit.AuditActionType;
import cl.minsal.semantikos.model.audit.ConceptAuditAction;
import cl.minsal.semantikos.model.relationships.Relationship;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static cl.minsal.semantikos.model.audit.AuditActionType.*;

/**
 * @author Andrés Farías
 */
@Stateless
public class AuditManagerImpl implements AuditManagerInterface {

    @EJB
    private AuditDAO auditDAO;

    @Override
    public void recordNewConcept(ConceptSMTK conceptSMTK, User user) {
        ConceptAuditAction conceptAuditAction = new ConceptAuditAction(conceptSMTK, CONCEPT_CREATION, now(), user, conceptSMTK);
        auditDAO.recordAuditAction(conceptAuditAction);
    }

    @Override
    public void recordUpdateConcept(ConceptSMTK conceptSMTK, User user) {
        // TODO: Implement this.
    }

    @Override
    public void recordDescriptionMovement(ConceptSMTK sourceConcept, ConceptSMTK targetConcept, Description description) {
        // TODO: Implement this.
    }

    @Override
    public void recordConceptPublished(ConceptSMTK theConcept, User user) {
        ConceptAuditAction auditAction = new ConceptAuditAction(theConcept, CONCEPT_PUBLICATION, now(), user, theConcept);
        auditDAO.recordAuditAction(auditAction);
    }

    @Override
    public void recordFavouriteDescriptionUpdate(ConceptSMTK conceptSMTK, Description originalDescription, User user) {
        ConceptAuditAction auditAction = new ConceptAuditAction(conceptSMTK, CONCEPT_FAVOURITE_DESCRIPTION_CHANGE, now(), user, originalDescription);
        auditDAO.recordAuditAction(auditAction);
    }

    @Override
    public void recordConceptCategoryChange(ConceptSMTK conceptSMTK, Category originalCategory, User user) {
        ConceptAuditAction auditAction = new ConceptAuditAction(conceptSMTK, CONCEPT_CATEGORY_CHANGE, now(), user, originalCategory);
        auditDAO.recordAuditAction(auditAction);
    }

    @Override
    public void recordAttributeChange(ConceptSMTK conceptSMTK, Relationship originalRelationship, User user) {
        ConceptAuditAction auditAction = new ConceptAuditAction(conceptSMTK, CONCEPT_ATTRIBUTE_CHANGE, now(), user, originalRelationship);
        auditDAO.recordAuditAction(auditAction);
    }

    @Override
    public void recordRelationshipCreation(Relationship relationship, User user) {
        ConceptAuditAction auditAction = new ConceptAuditAction(relationship.getSourceConcept(), CONCEPT_RELATIONSHIP_CREATION, now(), user, relationship);
        auditDAO.recordAuditAction(auditAction);
    }

    @Override
    public List<AuditActionType> getAllAuditActionTypes() {
        return Arrays.asList(AuditActionType.values());
    }

    @Override
    public List<ConceptAuditAction> getConceptAuditActions(ConceptSMTK conceptSMTK, int numberOfChanges, boolean changes) {
        return auditDAO.getConceptAuditActions(conceptSMTK.getId(), numberOfChanges, changes);
    }

    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }
}
