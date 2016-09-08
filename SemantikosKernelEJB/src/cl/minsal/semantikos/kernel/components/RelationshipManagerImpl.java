package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.RelationshipDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.RelationshipBindingBR;
import cl.minsal.semantikos.model.businessrules.RelationshipEditionBR;
import cl.minsal.semantikos.model.businessrules.RelationshipRemovalBR;
import cl.minsal.semantikos.model.businessrules.RelelationshipCreationBR;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * @author Andrés Farías.
 */
@Stateless
public class RelationshipManagerImpl implements RelationshipManager {

    /** El gestor de relaciones con la base de datos */
    @EJB
    private RelationshipDAO relationshipDAO;

    @EJB
    private AuditManagerInterface auditManager;

    @Override
    public Relationship createRelationship(ConceptSMTK origin, Target target, RelationshipDefinition relationType, boolean isValid, User user) {
        new RelelationshipCreationBR().applyRules(origin, target, relationType, isValid);
        Relationship relationship = new Relationship(origin, target, relationType);

        /* Se persiste la relación */
        relationshipDAO.persist(relationship);

        /* Se registra en el historial si el concepto fuente de la relación está modelado */
        if (relationship.getSourceConcept().isModeled()) {
            auditManager.recordRelationshipCreation(relationship, user);
        }

        /* Se retorna persistida */
        return relationship;
    }

    @Override
    public RelationshipDefinition createRelationshipDefinition(RelationshipDefinition relationshipDefinition) {
        return relationshipDAO.persist(relationshipDefinition);
    }

    @Override
    public Relationship bindRelationshipToConcept(ConceptSMTK concept, Relationship relationship, User user) {

        /* Primero se validan las reglas de negocio asociadas a la eliminación de un concepto */
        new RelationshipBindingBR().applyRules(concept, relationship, user);

        /* Se hace la relación a nivel del modelo */
        if (!concept.getRelationships().contains(relationship)){
            concept.addRelationship(relationship);
        }

        if (!relationship.isPersistent()){
            relationshipDAO.persist(relationship);
        }

          /* Se registra en el historial si el concepto fuente de la relación está modelado */
        if (relationship.getSourceConcept().isModeled()) {
            auditManager.recordRelationshipCreation(relationship, user);
        }

        /* Se retorna persistida */
        return relationship;
    }

    @Override
    public Relationship removeRelationship(Relationship relationship, User user) {

        /* Primero se validan las reglas de negocio asociadas a la eliminación de un concepto */
        new RelationshipRemovalBR().applyRules(relationship, user);

        /* Luego se elimina la relación */
        relationship.setValidityUntil(new Timestamp(currentTimeMillis()));
        relationshipDAO.invalidate(relationship);

        /* Se registra en el historial la eliminación (si el concepto asociado está modelado) */
        if (relationship.getSourceConcept().isModeled()) {
            auditManager.recordRelationshipRemoval(relationship, user);
        }

        return relationship;
    }

    @Override
    public List<Relationship> findRelationByCategories(Category sourceCategory, Category destinyCategory) {
        return relationshipDAO.getRelationshipByCategories(sourceCategory, destinyCategory);
    }

    @Override
    public Relationship[] findRelationsByOriginConcept(long id) {
        // TODO: Terminar esto
        return new Relationship[0];
    }

    @Override
    public List<Relationship> findRelationsByTargetSCTConcept(ConceptSCT conceptSCT) {
        return relationshipDAO.getRelationshipsToCSTConcept(conceptSCT);
    }

    @Override
    public void updateRelationship(@NotNull ConceptSMTK conceptSMTK, @NotNull Relationship originalRelationship, @NotNull Relationship editedRelationship, @NotNull User user) {

        /* Se aplican las reglas de negocio */
        new RelationshipEditionBR().applyRules(originalRelationship, editedRelationship);

        /* Y se actualizan */
        this.invalidate(originalRelationship);
        relationshipDAO.persist(editedRelationship);

        /* Registrar en el Historial si es preferida (Historial BR) */
        if (editedRelationship.isAttribute()) {
            auditManager.recordAttributeChange(conceptSMTK, originalRelationship, user);
        }
    }

    @Override
    // TODO: Terminar esto
    public int updateRelationAttribute(int idRelationship, int idRelationshipAttribute) {
        return 0;
    }

    @Override
    public Relationship invalidate(Relationship relationship) {
        relationship.setValidityUntil(new Timestamp(currentTimeMillis()));
        relationshipDAO.invalidate(relationship);

        return relationship;
    }

    @Override
    public List<Relationship> getRelationshipsLike(RelationshipDefinition relationshipDefinition, Target target) {
        return relationshipDAO.getRelationshipsLike(relationshipDefinition, target);
    }

    @Override
    public List<Relationship> getRelationshipsBySourceConcept(ConceptSMTK concept) {
        return relationshipDAO.getRelationshipsBySourceConcept(concept.getId());
    }
}
