package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by des01c7 on 26-07-16.
 */

@ManagedBean(name = "smtkBean")
@ViewScoped
public class SMTKTypeBean implements Serializable {

    private String pattern;


    private Map<Long, LazyDataModel<ConceptSMTK>> conceptSearchMap;

    private List<ConceptSMTK> conceptSearchList;




    @EJB
    private ConceptManagerInterface conceptManager;


    public LazyDataModel<ConceptSMTK> getConceptSearchForRDId(final Long idRelationshipDefinition) {

        if (conceptSearchMap == null)
            conceptSearchMap = new HashMap<Long, LazyDataModel<ConceptSMTK>>();


        if (!conceptSearchMap.containsKey(idRelationshipDefinition)) {
            LazyDataModel<ConceptSMTK> conceptSearch;
            conceptSearch = new LazyDataModel<ConceptSMTK>() {
                @Override
                public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<ConceptSMTK> conceptSMTKs;


                    final Long[] categoryArr = new Long[1];
                    if ((idRelationshipDefinition).intValue() == 2) {

                        categoryArr[0] = (long) 105590001;
                    } else {
                        categoryArr[0] = (long) 362981000;
                    }

                    conceptSMTKs = conceptManager.findConceptByConceptIDOrDescriptionCategoryPageNumber(pattern, categoryArr, first, pageSize);
                    this.setRowCount(conceptManager.getAllConceptCount(pattern, categoryArr));
                    return conceptSMTKs;
                }

            };

            conceptSearchMap.put(idRelationshipDefinition, conceptSearch);
        }

        return conceptSearchMap.get(idRelationshipDefinition);
    }



    public List<ConceptSMTK> getConceptSearchInput(String patron) {

        FacesContext context = FacesContext.getCurrentInstance();
        RelationshipDefinition rD = (RelationshipDefinition) UIComponent.getCurrentComponent(context).getAttributes().get("relationshipD");

        conceptSearchList = new ArrayList<ConceptSMTK>();
        final Long[] categoryArr = new Long[1];
        categoryArr[0] = (long) 105590001;
        System.out.println(patron);
        if (patron != null) {
            if (patron.length() > 2) {

                if (rD.getId() == 2) {

                    categoryArr[0] = (long) 105590001;
                    return conceptManager.findConceptByConceptIDOrDescriptionCategoryPageNumber(patron, categoryArr, 0, 20);
                } else {
                    categoryArr[0] = (long) 362981000;
                     return conceptManager.findConceptByConceptIDOrDescriptionCategoryPageNumber(patron, categoryArr, 0, 20);
                }
            }
        }
        return conceptSearchList;
    }

    @PostConstruct
    public void init() {

    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public ConceptManagerInterface getConceptManager() {
        return conceptManager;
    }

    public void setConceptManager(ConceptManagerInterface conceptManager) {
        this.conceptManager = conceptManager;
    }

}
