package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.browser.ConceptQueryFilter;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
public class ConceptQueryManagerImpl implements ConceptQueryInterface{


    @Override
    public ConceptQuery getDefaultQueryByCategory(Category category) {
        ConceptQuery query = new ConceptQuery();

        List<Category> categories = new ArrayList<Category>();
        categories.add(category);
        query.setCategories(categories);

        List<ConceptQueryFilter> filters = new ArrayList<ConceptQueryFilter>();
        query.setFilters(filters);


        return query;
    }

    @Override
    public List<ConceptSMTK> executeQuery(ConceptQuery query) {


//TODO: implement
        return new ArrayList<ConceptSMTK>();
    }

    @Override
    public List<RelationshipDefinition> getShowableAttributesByCategory(Category category) {
        return null;
    }
}
