package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Category;

import javax.ejb.Local;
import java.util.List;


/**
 * Created by des01c7 on 01-07-16.
 */

@Local
public interface CategoryDAO {

    public Category getCategoryById(long id);

    public Category getFullCategoryById(long id);

    public List<Category> getAllCategories();
}
