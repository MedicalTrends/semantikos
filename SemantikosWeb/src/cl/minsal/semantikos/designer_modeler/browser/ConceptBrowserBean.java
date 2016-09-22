package cl.minsal.semantikos.designer_modeler.browser;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.model.*;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by diego on 26/06/2016.
 */

@ManagedBean(name = "conceptBrowserBean")
@ViewScoped
public class ConceptBrowserBean implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ConceptBrowserBean.class);

    @EJB
    ConceptQueryManager conceptQueryManager;

    @EJB
    TagManager tagManager;

    @EJB
    HelperTableManagerInterface helperTableManager;

    private cl.minsal.semantikos.model.browser.ConceptQuery conceptQuery;

    private List<Tag> tags = new ArrayList<Tag>();

    private List<Category> categories;
    private LazyDataModel<ConceptSMTK> concepts;

    private Long[] selectedCategories;
    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    private Category category;
    private ConceptSMTK conceptSMTK;
    private Description description;
    private ConceptSMTK conceptSelected;

    @EJB
    private CategoryManager categoryManager;

    @EJB
    private ConceptManager conceptManager;

    @PostConstruct
    public void init() {


        categories = categoryManager.getCategories();

        concepts = new LazyDataModel<ConceptSMTK>() {
            @Override
            public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                List<ConceptSMTK> conceptSMTKs=null;
                selectedCategories= new Long[0];
                conceptSMTKs = conceptManager.findConceptBy(pattern, selectedCategories, first, pageSize);
                this.setRowCount(conceptManager.countConceptBy(pattern, selectedCategories));


                return conceptSMTKs;
            }

        };

        conceptQuery = conceptQueryManager.getDefaultQueryByCategory(category);

        tags = tagManager.getAllTags();

    }

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

    public Long[] getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(Long[] selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public ConceptSMTK getConceptSelected() {
        return conceptSelected;
    }

    public void setConceptSelected(ConceptSMTK conceptSelected) {
        this.conceptSelected = conceptSelected;
    }

    public cl.minsal.semantikos.model.browser.ConceptQuery getConceptQuery() {
        return conceptQuery;
    }

    public void setConceptQuery(cl.minsal.semantikos.model.browser.ConceptQuery conceptQuery) {
        this.conceptQuery = conceptQuery;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public HelperTableManagerInterface getHelperTableManager() {
        return helperTableManager;
    }

    public void setHelperTableManager(HelperTableManagerInterface helperTableManager) {
        this.helperTableManager = helperTableManager;
    }
}

