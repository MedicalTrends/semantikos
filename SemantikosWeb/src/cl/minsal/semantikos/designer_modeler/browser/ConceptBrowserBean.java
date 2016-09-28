package cl.minsal.semantikos.designer_modeler.browser;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.browser.ConceptQueryFilter;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.*;
import org.primefaces.extensions.model.fluidgrid.FluidGridItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private LazyDataModel<ConceptSMTK> concepts;

    private Category category;

    private int idCategory;

    private boolean flag = true;

    // Placeholders para los targets
    private BasicTypeValue basicTypeValue = new BasicTypeValue(null);

    private HelperTableRecord helperTableRecord = new HelperTableRecord();

    @EJB

    private CategoryManager categoryManager;

    @EJB
    private ConceptManager conceptManager;

    private List<FluidGridItem> items;


    //@PostConstruct
    public void init() {

        if(category == null){
            /*
            try {
                category = categoryManager.getCategoryById(3);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            */
            return;
        }

        if(flag) {
            flag = false;

            conceptQuery = conceptQueryManager.getDefaultQueryByCategory(category);

            for (RelationshipDefinition relationshipDefinition : category.getRelationshipDefinitions()) {
                ConceptQueryFilter conceptQueryFilter = new ConceptQueryFilter();
                conceptQueryFilter.setDefinition(relationshipDefinition);
                conceptQueryFilter.setMultiple(false);

                conceptQuery.getFilters().add(conceptQueryFilter);
            }
        }

        tags = tagManager.getAllTags();

        concepts = new LazyDataModel<ConceptSMTK>() {
            @Override
            public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {


                //List<ConceptSMTK> conceptSMTKs = conceptManager.findConceptBy(category, first, pageSize);

                List<ConceptSMTK> conceptSMTKs = conceptQueryManager.executeQuery(conceptQuery);
                this.setRowCount(30);

                return conceptSMTKs;
            }

        };

    }

    public void refreshResults(){

        concepts = new LazyDataModel<ConceptSMTK>() {
            @Override
            public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

            conceptQuery.setPageNumber(first);
            conceptQuery.setPageSize(pageSize);

                //List<ConceptSMTK> conceptSMTKs = conceptQueryManager.executeQuery(conceptQuery);;
            //this.setRowCount(conceptManager.countConceptBy(pattern, selectedCategories));

            List<ConceptSMTK> conceptSMTKs = conceptManager.findConceptBy(category, first, pageSize);
            this.setRowCount(30);
            return conceptSMTKs;
            }

        };
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
        try {
            this.category = categoryManager.getCategoryById(idCategory);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String stringifyList(List<Object> objects){
        return Arrays.toString(objects.toArray());
    }

    public LazyDataModel<ConceptSMTK> getConcepts() {
        return concepts;
    }

    public void setConcepts(LazyDataModel<ConceptSMTK> concepts) {
        this.concepts = concepts;
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
        refreshResults();
    }

    public ConceptQuery getConceptQuery() {
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

    public BasicTypeValue getBasicTypeValue() {
        return basicTypeValue;
    }

    public void setBasicTypeValue(BasicTypeValue basicTypeValue) {
        this.basicTypeValue = basicTypeValue;
    }

    public HelperTableRecord getHelperTableRecord() {
        if (helperTableRecord == null)
            helperTableRecord = new HelperTableRecord();

        return helperTableRecord;
    }

    public void setHelperTableRecord(HelperTableRecord helperTableRecord) {
        this.helperTableRecord = helperTableRecord;
    }

    /**
     * Este método se encarga de agregar o cambiar el filtro para el caso de selección simple
     */
    public void setSimpleSelection(RelationshipDefinition relationshipDefinition, Target target) {

        // Se busca el filtro
        for (ConceptQueryFilter conceptQueryFilter : conceptQuery.getFilters()) {
            if (conceptQueryFilter.getDefinition().equals(relationshipDefinition)) {
                if(conceptQueryFilter.getTargets().isEmpty()) //Si la lista de targets está vacía, se agrega el target
                    conceptQueryFilter.getTargets().add(target);
                else //Si no, se modifica
                    conceptQueryFilter.getTargets().set(0, target);
                break;
            }
        }
        // Se resetean los placeholder para los target de las relaciones
        basicTypeValue = new BasicTypeValue(null);
    }

    /**
     * Este método se encarga de agregar o cambiar el filtro para el caso de selección múltiple
     */
    public void setMultipleSelection(RelationshipDefinition relationshipDefinition, Target target) {

        // Se busca el filtro
        for (ConceptQueryFilter conceptQueryFilter : conceptQuery.getFilters()) {
            if (conceptQueryFilter.getDefinition().equals(relationshipDefinition)) {
                conceptQueryFilter.getTargets().add(target);
                break;
            }
        }
    }

}

