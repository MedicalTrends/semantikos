package cl.minsal.semantikos.view.components;

import cl.minsal.semantikos.model.relationships.RelationshipDefinitionWeb;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import javax.ejb.Local;

/**
 * @author Andrés Farías on 10/5/16.
 */
@Local
public interface ViewAugmenter {

    /**
     * Blablabla
     * @param relationshipDefinition bla bla
     * @return blabla
     */
    public RelationshipDefinitionWeb augmentRelationshipDefinition(RelationshipDefinition relationshipDefinition);
}
