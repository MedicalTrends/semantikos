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

        // Agregar columnas estáticas
        query.getColumns().add(new ConceptQueryColumn("ConceptID",new Sort(true, true)));
        query.getColumns().add(new ConceptQueryColumn("Término",new Sort(null, false)));
        query.getColumns().add(new ConceptQueryColumn("Observación",new Sort(null, false)));
        query.getColumns().add(new ConceptQueryColumn("Etiqueta",new Sort(null, false)));
        query.getColumns().add(new ConceptQueryColumn("Está en",new Sort(null, false)));

        // Agregar columnas dinámicas
        for (RelationshipDefinition relationshipDefinition : getShowableAttributesByCategory(category)) {
            query.getColumns().add(new ConceptQueryColumn(relationshipDefinition.getName(), new Sort(null, false)));
        }

        List<Category> categories = new ArrayList<Category>();
        categories.add(category);
        query.setCategories(categories);

        List<ConceptQueryFilter> filters = new ArrayList<ConceptQueryFilter>();
        query.setFilters(filters);

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
        //TODO:implement
        return new ArrayList<RelationshipDefinition>();
    }

    @Override
    public List<RelationshipDefinition> getSearchableAttributesByCategory(Category category) {
        //TODO:implement
        return new ArrayList<RelationshipDefinition>();
    }
}
