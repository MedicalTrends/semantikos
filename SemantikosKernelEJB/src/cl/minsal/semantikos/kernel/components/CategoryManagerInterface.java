package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import javax.ejb.Local;
import java.text.ParseException;
import java.util.List;

/**
 * Esta interfaz tiene como propósito definir el comportamiento del componente de gestión de categorías.
 *
 * @author Andrés Farías
 */
@Local
public interface CategoryManagerInterface {

    /**
     * Este metodo es responsable de recuperar una Categoría completa, con sus propiedades básicas y todos sus
     * meta-atributos
     *
     * @param idCategory Identificador Unico de la categoría.
     *
     * @return La categoría buscada.
     */
    public Category getCategoryById(int idCategory) throws ParseException;

    /**
     * Este método responsable de recuperar toda la meta-data que consituye la definición de una categoría, en
     * particular todos los atributos que define.
     *
     * @param id Identificador único de la categoría.
     *
     * @return La lista de definiciones de atributos de la categoría.
     */
    public List<RelationshipDefinition> getCategoryMetaData(int id);

    public List<RelationshipDefinition> getAllDescription();

    public List<Category> getCategories();

    public int addCategory(Category category);

    public void addAttribute(RelationshipDefinition attributeCategory, int idCategory);

    public int addTypeRelationship(String name, int typeRelation, int idCategoryDes, int multiplicity);


}
