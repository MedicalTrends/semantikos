package cl.minsal.semantikos.view.components;

import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.RelationshipDefinitionWeb;
import cl.minsal.semantikos.view.daos.SemantikosWeb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Andrés Farías on 10/5/16.
 */
@Stateless
public class ViewAugmenterImpl implements ViewAugmenter {

    @EJB
    private SemantikosWeb semantikosWebDAO;

    @Override
    public RelationshipDefinitionWeb augmentRelationshipDefinition(RelationshipDefinition relDef) {
        long idComposite = semantikosWebDAO.getCompositeOf(relDef);

        return new RelationshipDefinitionWeb(relDef.getId(), relDef.getName(), relDef.getDescription(), relDef.getTargetDefinition(), relDef.getMultiplicity(), idComposite);
    }
}
