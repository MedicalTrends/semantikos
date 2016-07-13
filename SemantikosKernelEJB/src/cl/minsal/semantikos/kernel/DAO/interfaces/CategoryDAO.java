package cl.minsal.semantikos.kernel.DAO.interfaces;

import cl.minsal.semantikos.model.Category;

import java.util.List;


/**
 * Created by des01c7 on 01-07-16.
 */
public interface CategoryDAO {

    public Category getCategoryById(int id);

    public List<Category> getAllCategories();
}
