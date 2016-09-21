package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
public class ConceptQueryManagerImpl implements ConceptQueryInterface{


    @Override
    public ConceptQuery getDefaultQueryByCategory(Category category) {
        return null;
    }

    @Override
    public List<ConceptSMTK> executeQuery(ConceptQuery query) {
        return null;
    }

    @Override
    public List<RelationshipDefinition> getShowableAttributesByCategory(Category category) {
        return null;
    }
}
