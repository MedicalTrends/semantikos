package cl.minsal.semantikos.designer_modeler.designer;

import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.components.HelperTableManager;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * Created by des01c7 on 26-07-16.
 */

@ManagedBean(name = "helperTableBean")
@ViewScoped
public class HelperTableBean implements Serializable {

    private String pattern;

    private HelperTableRecord recordPlaceHolder = new HelperTableRecord();

    private Map<HelperTable, List<HelperTableRecord> > recordSearchLists = new HashMap<HelperTable, List<HelperTableRecord>>();

    @EJB
    private HelperTableManager helperTableManager;

    @PostConstruct
    public void init() {
    }

    public List<HelperTableRecord> getRecordList(HelperTable helperTable){
        if(!recordSearchLists.containsKey(helperTable))
            recordSearchLists.put(helperTable, helperTableManager.getAllRecords(helperTable));

        return recordSearchLists.get(helperTable);
    }

    public List<HelperTableRecord> getRecordSearchInput(String patron) {

        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext context2 = RequestContext.getCurrentInstance();

        HelperTable helperTable = (HelperTable) UIComponent.getCurrentComponent(context).getAttributes().get("helperTable");
        RelationshipDefinition relationshipDefinition = (RelationshipDefinition) UIComponent.getCurrentComponent(context).getAttributes().get("relationshipDefinition");

        if(!recordSearchLists.containsKey(helperTable))
            recordSearchLists.put(helperTable, helperTableManager.getAllRecords(helperTable));

        List<HelperTableRecord> someRecords = new ArrayList<HelperTableRecord>();

        for (HelperTableRecord record : recordSearchLists.get(helperTable)) {
            if(record.getFields().get("description").toLowerCase().contains(patron.trim().toLowerCase()))
                someRecords.add(record);
        }

        if(relationshipDefinition.isISP() && someRecords.isEmpty()){
            context2.execute("PF('dialogISP').show();");
        }

        return someRecords;
    }

    public HelperTableRecord getRecordById(HelperTable helperTable, Long id){

        if(id==null)
            return null;

        if(!recordSearchLists.containsKey(helperTable))
            recordSearchLists.put(helperTable, helperTableManager.getAllRecords(helperTable));

        for (HelperTableRecord record : recordSearchLists.get(helperTable)) {
            if(id.equals(new Long(record.getFields().get("id"))))
                return record;
        }

        return null;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public HelperTableManager getHelperTableManager() {
        return helperTableManager;
    }

    public void setHelperTableManager(HelperTableManager helperTableManager) {
        this.helperTableManager = helperTableManager;
    }

    public HelperTableRecord getRecordPlaceHolder() {
        return recordPlaceHolder;
    }

    public void setRecordPlaceHolder(HelperTableRecord recordPlaceHolder) {
        this.recordPlaceHolder = recordPlaceHolder;
    }


}
