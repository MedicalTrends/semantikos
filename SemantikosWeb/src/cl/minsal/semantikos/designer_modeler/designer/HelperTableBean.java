package cl.minsal.semantikos.designer_modeler.designer;

import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.components.HelperTableManager;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

    private HelperTableRecord recordSelected;

    private List<HelperTableRecord> recordSearchList;

    private HelperTableRecord recordSel;

    @EJB
    private HelperTableManager helperTableManager;


    public List<HelperTableRecord> getRecordSearchInput(String patron) {

        if(patron.trim().length()==0)
            return emptyList();

        FacesContext context = FacesContext.getCurrentInstance();
        HelperTable helperTable = (HelperTable) UIComponent.getCurrentComponent(context).getAttributes().get("helperTable");

        List<HelperTableRecord> someRecords = new ArrayList<HelperTableRecord>();

        if(recordSearchList == null)
            recordSearchList = helperTableManager.getAllRecords(helperTable);

        for (HelperTableRecord record : recordSearchList) {
            if(record.getFields().get("description").toLowerCase().contains(patron.trim().toLowerCase()))
                someRecords.add(record);
        }

        return someRecords;
    }

    public HelperTableRecord getRecordById(Long id){

        if(id==null)
            return null;

        for (HelperTableRecord record : recordSearchList) {
            if(id.equals(new Long(record.getFields().get("id"))))
                return record;
        }

        return null;
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

    public HelperTableManager getHelperTableManager() {
        return helperTableManager;
    }

    public void setHelperTableManager(HelperTableManager helperTableManager) {
        this.helperTableManager = helperTableManager;
    }

}
