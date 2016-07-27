package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by des01c7 on 26-07-16.
 */
@ManagedBean(name="smtkBean")
@ViewScoped
public class SMTKTypeBean implements Serializable{

    private Category category;
    private String pattern;
    private LazyDataModel<ConceptSMTK> conceptSearch;
    private ConceptSMTK conceptSelected;
    private List<ConceptSMTK> conceptSave;

    private ConceptSMTK conceptSemantikos;

    @EJB
    private ConceptManagerInterface conceptManager;

    @PostConstruct
    public void init() {
        conceptSave= new ArrayList<ConceptSMTK>();
        final Long[] categoryArr = new Long[1];
        //categoryArr[0]=category.getIdCategory();
        categoryArr[0]=(long)105590001;

        conceptSearch = new LazyDataModel<ConceptSMTK>() {
            @Override
            public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<ConceptSMTK> conceptSMTKs;

                conceptSMTKs = conceptManager.findConceptByConceptIDOrDescriptionCategoryPageNumber(pattern, categoryArr, first, pageSize);
                this.setRowCount(conceptManager.getAllConceptCount(pattern, categoryArr));
                return conceptSMTKs;
            }

        };

    }

    public void addRelationSMTK(ConceptSMTK c){

        conceptSave.add(c);

        for (int i = 0; i < conceptSave.size(); i++) {
            System.out.println(conceptSave.get(i).getId());
        }
    }

    public void removeRelationSMTK(){



    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public LazyDataModel<ConceptSMTK> getConceptSearch() {
        return conceptSearch;
    }

    public void setConceptSearch(LazyDataModel<ConceptSMTK> conceptSearch) {
        this.conceptSearch = conceptSearch;
    }

    public ConceptManagerInterface getConceptManager() {
        return conceptManager;
    }

    public void setConceptManager(ConceptManagerInterface conceptManager) {
        this.conceptManager = conceptManager;
    }

    public ConceptSMTK getConceptSemantikos() {
        return conceptSemantikos;
    }

    public void setConceptSemantikos(ConceptSMTK conceptSemantikos) {
        this.conceptSemantikos = conceptSemantikos;
    }

    public List<ConceptSMTK> getConceptSave() {
        return conceptSave;
    }

    public void setConceptSave(List<ConceptSMTK> conceptSave) {
        this.conceptSave = conceptSave;
    }

    public ConceptSMTK getConceptSelected() {
        return conceptSelected;
    }

    public void setConceptSelected(ConceptSMTK conceptSelected) {
        this.conceptSelected = conceptSelected;
    }
}
