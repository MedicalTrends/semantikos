package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.RelationshipDefinition;

import javax.ejb.Local;
import java.text.ParseException;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */
@Local
public interface RelationshipDefinitionDAO {

    /**
     * Este método es responsable de recuperar los atributos (RelationshipDefinitions) de una Categoría.
     *
     * @param idCategory Identificador de la categoría.
     *
     * @return Una lista con los atributos de la categoría.
     */
    public List<RelationshipDefinition> getRelationshipDefinitionsByCategory(int idCategory) throws ParseException;
}
