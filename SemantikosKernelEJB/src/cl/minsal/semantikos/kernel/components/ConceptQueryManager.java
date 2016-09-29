package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
@Local
public interface ConceptQueryManager {

    public cl.minsal.semantikos.model.browser.ConceptQuery getDefaultQueryByCategory(Category category);

    public List<ConceptSMTK> executeQuery(cl.minsal.semantikos.model.browser.ConceptQuery query);

    public List<RelationshipDefinition> getShowableAttributesByCategory(Category category);

}
