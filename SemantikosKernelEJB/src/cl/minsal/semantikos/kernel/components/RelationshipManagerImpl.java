package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.RelationshipDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.RelationshipEditionBR;
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

        /* Se registra en el historial */
        auditManager.recordRelationshipCreation(relationship, user);

        /* Se retorna persistida */
        return relationship;
    }

    @Override
    public Relationship[] findRelationByCategories(Category sourceCategory, Category destinyCategory) {
        // TODO: Terminar esto
        return new Relationship[0];
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
