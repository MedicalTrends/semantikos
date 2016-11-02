package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.ConceptQueryDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.browser.ConceptQueryColumn;
import cl.minsal.semantikos.model.browser.ConceptQueryFilter;
import cl.minsal.semantikos.model.browser.Sort;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
@Stateless
public class ConceptQueryManagerImpl implements ConceptQueryManager{


    @EJB
    ConceptQueryDAO conceptQueryDAO;

    @Override
    public ConceptQuery getDefaultQueryByCategory(Category category) {
        //TODO: implementar de veldá

        ConceptQuery query = new ConceptQuery();

        List<Category> categories = new ArrayList<Category>();
        categories.add(category);
        query.setCategories(categories);

        List<ConceptQueryFilter> filters = new ArrayList<ConceptQueryFilter>();
        query.setFilters(filters);

        // Agregar columnas dinámicas
        for (RelationshipDefinition relationshipDefinition : getShowableAttributesByCategory(category)) {
            query.getColumns().add(new ConceptQueryColumn(relationshipDefinition.getName(), new Sort(null, false)));
        }
        // Agregar filtros dinámicos
        for (RelationshipDefinition relationshipDefinition : getSearchableAttributesByCategory(category)) {
            query.getFilters().add(new ConceptQueryFilter(relationshipDefinition));
        }

        return query;
    }

    @Override
    public List<ConceptSMTK> executeQuery(ConceptQuery query) {

        //return conceptQueryDAO.callQuery(query);
        return conceptQueryDAO.executeQuery(query);

    }

    @Override
    public List<RelationshipDefinition> getShowableAttributesByCategory(Category category) {
        return conceptQueryDAO.getShowableAttributesByCategory(category);
    }

    @Override
    public List<RelationshipDefinition> getSearchableAttributesByCategory(Category category) {
        return conceptQueryDAO.getSearchableAttributesByCategory(category);
    }
}
