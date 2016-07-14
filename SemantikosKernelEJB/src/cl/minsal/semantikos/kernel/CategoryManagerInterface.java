package cl.minsal.semantikos.kernel;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.RelationShipDefinition;

import javax.ejb.Local;
import java.util.List;

/**
 * Esta interfaz tiene como propósito definir el comportamiento del componente de gestión de categorías.
 *
 * @author Andrés Farías
 */
@Local
public interface CategoryManagerInterface {

    /**
     * Este método responsable de recuperar toda la meta-data que consituye la definición de una categoría, en
     * particular todos los atributos que define.
     *
     * @param id Identificador único de la categoría.
     *
     * @return La lista de definiciones de atributos de la categoría.
     */
    public List<RelationShipDefinition> getCategoryMetaData(int id);

    public List<RelationShipDefinition> getAllDescription();

    public List<Category> getCategories();

    public int addCategory(Category category);

    public void addAttribute(RelationShipDefinition attributeCategory, int idCategory);

    public int addTypeRelationship(String name, int typeRelation, int idCategoryDes, int multiplicity);

    public Category getCategoryById(int id);


}
