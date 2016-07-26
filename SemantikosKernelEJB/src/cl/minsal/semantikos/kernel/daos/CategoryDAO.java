package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.RelationshipDefinition;

import javax.ejb.Local;
import java.text.ParseException;
import java.util.List;


/**
 * Este DAO es responsable de recuperar información relativa a las categorías desde la BD.
 */

@Local
public interface CategoryDAO {
    /**
     * Este método es responsable de recuperar toda la información de una categoría desde la BD y retornarla bien
     * organizada en un objeto de negocio.
     *
     * @return La categoría requerida por su ID.
     */
    public Category getCategoryById(long id) throws ParseException;
    
    public List<Category> getAllCategories();

    /**
     * Este método responsable de recuperar toda la meta-data que consituye la definición de una categoría, en
     * particular todos los atributos que define.
     *
     * @param idCategory Identificador único de la categoría.
     *
     * @return La lista de definiciones de atributos de la categoría.
     */
    public List<RelationshipDefinition> getCategoryMetaData(int idCategory) throws ParseException;
}