package cl.minsal.semantikos.admin_refset;

import cl.minsal.semantikos.kernel.components.CategoryManager;
import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.components.RefSetManager;
import cl.minsal.semantikos.model.*;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;

/**
 * Created by des01c7 on 20-09-16.
 */
@ManagedBean(name="refsetsBean")
@ViewScoped
public class RefSetsBean {


    private RefSet refSetToCreate;

    private List<RefSet> refSetList;

    private List<Category> categories;

    private Category categorySelected;

    private User user;

    private LazyDataModel<ConceptSMTK> conceptsToCategory;

    private LazyDataModel<ConceptSMTK> conceptsToDescription;

    private String pattern;

    private RefSet refSetEdit;


    @EJB
    private CategoryManager categoryManager;

    @EJB
    private ConceptManager conceptManager;

    @EJB
    private RefSetManager refSetManager;


    @PostConstruct
    public void init(){


        user = new User();

        user.setIdUser(1);
        user.setUsername("amauro");
        user.setPassword("amauro");
        Profile designerProfile = new Profile(4, "Administrador de RefSets", "Usuario administrador de RefSets.");
        user.getProfiles().add(designerProfile);

        categories= categoryManager.getCategories();

        Institution institution= new Institution();
        institution.setId(1);
        institution.setName("MINSAL");


        conceptsToCategory = null;

        conceptsToDescription= null;

        refSetToCreate = new RefSet(null,institution,null);

        refSetList= refSetManager.getAllRefSets();

    }

    public void createRefset(){

        refSetToCreate=refSetManager.createRefSet(refSetToCreate.getName(),refSetToCreate.getInstitution(),user);
        refSetManager.updateRefSet(refSetToCreate,user);

    }

    public void selectCategoryEvent(){


        conceptsToCategory = new LazyDataModel<ConceptSMTK>() {
            @Override
            public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<ConceptSMTK> conceptSMTKs;
                Long[] idCategory;
                if(categorySelected==null){
                    idCategory = new Long[0];
                }else{
                    idCategory = new Long[1];
                    idCategory[0]= categorySelected.getId();
                }

                conceptSMTKs = conceptManager.findConceptBy(null, idCategory, first, pageSize);
                this.setRowCount(conceptManager.countConceptBy(null, idCategory));

                return conceptSMTKs;
            }

        };

    }


    public void patternEvent(){

        if(pattern!=null){
            if(pattern.length()>2){
                conceptsToDescription = new LazyDataModel<ConceptSMTK>() {
                    @Override
                    public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                        List<ConceptSMTK> conceptSMTKs;
                        conceptSMTKs = conceptManager.findConceptBy(pattern, new Long[0], first, pageSize);
                        this.setRowCount(conceptManager.countConceptBy(pattern, new Long[0]));

                        return conceptSMTKs;
                    }

                };

            }else{
                conceptsToDescription=null;
            }

        }
    }


    public void addConcept(RefSet refSet, ConceptSMTK conceptSMTK){
        refSet.bindConceptTo(conceptSMTK);
    }

    public void removeConcept(RefSet refSet, ConceptSMTK conceptSMTK){
        refSet.unbindConceptTo(conceptSMTK);
    }




    public RefSet getRefSetToCreate() {
        return refSetToCreate;
    }

    public void setRefSetToCreate(RefSet refSetToCreate) {
        this.refSetToCreate = refSetToCreate;
    }

    public List<RefSet> getRefSetList() {
        return refSetList;
    }

    public void setRefSetList(List<RefSet> refSetList) {
        this.refSetList = refSetList;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Category getCategorySelected() {
        return categorySelected;
    }

    public void setCategorySelected(Category categorySelected) {
        this.categorySelected = categorySelected;
    }


    public CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public LazyDataModel<ConceptSMTK> getConceptsToCategory() {
        return conceptsToCategory;
    }

    public void setConceptsToCategory(LazyDataModel<ConceptSMTK> conceptsToCategory) {
        this.conceptsToCategory = conceptsToCategory;
    }

    public LazyDataModel<ConceptSMTK> getConceptsToDescription() {
        return conceptsToDescription;
    }

    public void setConceptsToDescription(LazyDataModel<ConceptSMTK> conceptsToDescription) {
        this.conceptsToDescription = conceptsToDescription;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    public RefSet getRefSetEdit() {
        return refSetEdit;
    }

    public void setRefSetEdit(RefSet refSetEdit) {
        this.refSetEdit = refSetEdit;
    }
}
