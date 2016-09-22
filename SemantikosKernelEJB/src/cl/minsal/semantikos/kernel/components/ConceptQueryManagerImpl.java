package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
@Stateless
public class ConceptQueryManagerImpl implements ConceptQueryManager {


    @Override
    public cl.minsal.semantikos.model.browser.ConceptQuery getDefaultQueryByCategory(Category category) {
        return null;
    }

    @Override
    public List<ConceptSMTK> executeQuery(cl.minsal.semantikos.model.browser.ConceptQuery query) {
        return null;
    }

    @Override
    public List<RelationshipDefinition> getShowableAttributesByCategory(Category category) {
        return null;
    }
}
