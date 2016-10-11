package cl.minsal.semantikos.view.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.RelationshipDefinitionWeb;
import cl.minsal.semantikos.view.daos.ExtendedRelationshipDefinitionInfo;
import cl.minsal.semantikos.view.daos.SemantikosWebDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Andrés Farías on 10/5/16.
 */
@Stateless
public class ViewAugmenterImpl implements ViewAugmenter {

    @EJB
    private SemantikosWebDAO semantikosWebDAODAO;

    @Override
    public RelationshipDefinitionWeb augmentRelationshipDefinition(Category category, RelationshipDefinition relDef) {

        ExtendedRelationshipDefinitionInfo extendedRelationshipDefinitionInfo = semantikosWebDAODAO.getCompositeOf(category, relDef);
        RelationshipDefinitionWeb relationshipDefinitionWeb = new RelationshipDefinitionWeb(relDef.getId(), relDef.getName(), relDef.getDescription(), relDef.getTargetDefinition(), relDef.getMultiplicity(), extendedRelationshipDefinitionInfo.getIdComposite(), extendedRelationshipDefinitionInfo.getOrder());
        relationshipDefinitionWeb.setRelationshipAttributeDefinitions(relDef.getRelationshipAttributeDefinitions());
        return relationshipDefinitionWeb;
    }
}
