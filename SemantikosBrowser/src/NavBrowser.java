

import cl.minsal.semantikos.kernel.CategoryManagerImpl;
import cl.minsal.semantikos.kernel.CategoryManagerInterface;
import cl.minsal.semantikos.kernel.ConceptManagerImpl;
import cl.minsal.semantikos.kernel.ConceptManagerInterface;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;


/*
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
*/

@ManagedBean
@ViewScoped
public class NavBrowser  {


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public LazyDataModel<ConceptSMTK> getConcepts() {
        return concepts;
    }

    public void setConcepts(LazyDataModel<ConceptSMTK> concepts) {
        this.concepts = concepts;
    }

    public String[] getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(String[] selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public CategoryManagerInterface getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManagerInterface categoryManager) {
        this.categoryManager = categoryManager;
    }

    public ConceptManagerInterface getConceptManager() {
        return conceptManager;
    }

    public void setConceptManager(ConceptManagerInterface conceptManager) {
        this.conceptManager = conceptManager;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ConceptSMTK getConcept() {
        return concept;
    }

    public void setConcept(ConceptSMTK concept) {
        this.concept = concept;
    }

    private List<Category> categories;
    private LazyDataModel<ConceptSMTK> concepts;

    private String[]selectedCategories;
    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @EJB
    private CategoryManagerInterface categoryManager;

    @EJB
    private ConceptManagerInterface conceptManager;

    private Category category;
    private ConceptSMTK concept;



    @PostConstruct
    public void init() {


        categories=categoryManager.getCategories();

        System.out.println("inicio");
        /*
        concepts = new LazyDataModel<ConceptSMTK>() {
            @Override
            public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                List <ConceptSMTK> conceptSMTKs= conceptManager.findConceptByPatternCategoryPageNumber(pattern,selectedCategories,first,pageSize);
                this.setRowCount(conceptManager.getAllConceptCount(pattern,selectedCategories));
                return conceptSMTKs;
            }
        };*/


    }




}