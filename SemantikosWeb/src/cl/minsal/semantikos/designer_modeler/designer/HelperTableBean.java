package cl.minsal.semantikos.designer_modeler.designer;


import cl.minsal.semantikos.kernel.components.HelperTableManager;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by des01c7 on 23-08-16.
 */
@ManagedBean(name = "helperTableBean")
@ViewScoped
public class HelperTableBean implements Serializable{

    @EJB
    HelperTableManager helperTableManager;


    public List<HelperTableRecord> searchHelperTableRecords(String query){


        FacesContext context = FacesContext.getCurrentInstance();
        HelperTable table = (HelperTable) UIComponent.getCurrentComponent(context).getAttributes().get("table");




        return helperTableManager.searchValidRecords(table,table.getShowableColumnsNames(),query);
    }
}
