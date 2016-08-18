package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.RelationshipDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías.
 */
@Stateless
public class RelationshipManagerImpl implements RelationshipManager {

    /** El gestor de relaciones con la base de datos */
    @EJB
    private RelationshipDAO relationshipDAO;

    @Override
    public int createRelationship(ConceptSMTK origin, Target target, RelationshipDefinition relationType, boolean isValid) {
        // TODO: Terminar esto
        return 0;
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
    // TODO: Terminar esto
    public int updateRelationAttribute(int idRelationship, int idRelationshipAttribute) {
        return 0;
    }

    @Override
    // TODO: Terminar esto
    public Relationship updateRelationProperties(int idRelation, boolean isActive) {
        return null;
    }
}
