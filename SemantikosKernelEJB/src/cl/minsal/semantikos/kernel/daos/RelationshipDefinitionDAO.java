package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.RelationshipDefinition;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */
@Local
public interface RelationshipDefinitionDAO {


    public List<RelationshipDefinition> getRelationshipDefinitionsByCategory(int idCategory);
}
