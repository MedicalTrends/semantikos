package cl.minsal.semantikos.kernel.EJB.implementation;

import cl.minsal.semantikos.kernel.domain.Category;
import cl.minsal.semantikos.kernel.DAO.implementation.CategoryDAOImpl;
import cl.minsal.semantikos.kernel.EJB.interfaces.CategoryManager;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by des01c7 on 29-06-16.
 */

@Stateless
public class CategoryManagerImpl implements CategoryManager {

    @Override
    public Category getCategoryById(int id) {
        CategoryDAOImpl categoryDAO= new CategoryDAOImpl();
        return categoryDAO.getCategoryById(id);
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }
}
