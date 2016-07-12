package cl.minsal.semantikos.kernel.EJB.interfaces;

import cl.minsal.semantikos.kernel.domain.Category;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by des01c7 on 29-06-16.
 */

@Local
public interface CategoryManager {


    public Category getCategoryById(int id);
    public List<Category> getCategories();

}
