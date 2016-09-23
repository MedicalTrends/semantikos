package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.kernel.daos.CategoryDAO;
import cl.minsal.semantikos.kernel.daos.RelationshipDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.CategoryCreationBR;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrés Farías on 27-05-16.
 */
@Stateless
public class CategoryManagerImpl implements CategoryManager {

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    private EntityManager entityManager;

    @EJB
    private CategoryDAO categoryDAO;

    @EJB
    private RelationshipDAO relationshipDAO;

    private static final Logger logger = LoggerFactory.getLogger(CategoryManagerImpl.class);

    @EJB
    private DescriptionManager descriptionManager;

    @Override
    public List<RelationshipDefinition> getCategoryMetaData(int id) {
        ArrayList<RelationshipDefinition> Attributes = new ArrayList<RelationshipDefinition>();

        Query nativeQuery = this.entityManager.createNativeQuery("SELECT get_conf_rel_all()");
        List<Object[]> relationships = nativeQuery.getResultList();

        long idRelationship;
        String name;
        int multiplicity;
        String description;
        boolean required;

        for (Object[] relationship : relationships) {
            idRelationship = ((BigInteger) relationship[0]).longValue();
            name = (String) relationship[1];
            multiplicity = Integer.parseInt((String) relationship[2]);

            /* Se crea el objeto */
            //Attributes.add(new AttributeCategory(idRelationship, name, multiplicity, description, required));
        }

        return Attributes;
    }

    @Override
    public void addAttribute(RelationshipDefinition attributeCategory, int idCategory) {

    }

    @Override
    public int addTypeRelationship(String name, int typeRelation, int idCategoryDes, int multiplicity) {

        // FIXME: Sin terminar
        int idTypeRelationShip = 0;


        return idTypeRelationShip;
    }

    @Override
    public Category createCategory(Category category, User user) {

        logger.debug("Persistiendo la categoría: " + category);

        /* Se validan las reglas de negocio */
        new CategoryCreationBR().applyRules(category, user);

        /* Se persiste la categoría */
        categoryDAO.persist(category);

        /* Se persisten sus definiciones de relaciones */
        for (RelationshipDefinition relationshipDefinition : category.getRelationshipDefinitions()) {
            relationshipDAO.persist(relationshipDefinition);
        }

        logger.debug("Categoría persistida: " + category);

        /* Se retorna */
        return category;
    }

    @Override
    public boolean categoryContains(Category category, String term) {
        List<Description> descriptions = descriptionManager.searchDescriptionsByTerm(term, Arrays.asList(category));

        /* Si la búsqueda resultó con al menos un término vigente, entonces si contiene */
        for (Description description : descriptions) {
            if (description.isValid()){
                return true;
            }
        }
        return descriptions.size() > 0;
    }

    @Override
    public Category getCategoryById(int id) throws ParseException {
        return categoryDAO.getCategoryById(id);
    }

    @Override
    public List<Category> getCategories() {

        logger.debug("Recuperando todas las categorías.");

        List<Category> categories = categoryDAO.getAllCategories();
        logger.debug(categories.size() + "categorías recuperadas.");

        return categories;
    }
}
