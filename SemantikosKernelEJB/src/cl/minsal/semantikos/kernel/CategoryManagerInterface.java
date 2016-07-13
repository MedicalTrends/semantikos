package cl.minsal.semantikos.kernel;

import cl.minsal.semantikos.model.AttributeCategory;
import cl.minsal.semantikos.model.Category;

import javax.ejb.Local;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stk-des01 on 27-05-16.
 */
@Local
public interface CategoryManagerInterface {

    public List<AttributeCategory> findDescriptionByIDConcept(int id);

    public List<AttributeCategory> getAllDescription();

    public List<Category> getCategories();

    public int addCategory(Category category);

    public void addAttribute(AttributeCategory attributeCategory, int idCategory);

    public int addTypeRelationship(String name, int typeRelation, int idCategoryDes, int multiplicity);

    public Category getCategoryById(int id);




}
