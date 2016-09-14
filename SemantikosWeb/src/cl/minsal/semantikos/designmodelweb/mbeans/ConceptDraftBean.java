package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.model.ConceptSMTK;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.POST;
import java.io.Serializable;
import java.util.List;

/**
 * Created by des01c7 on 09-09-16.
 */

@ManagedBean(name = "conceptDraftBean")
@ViewScoped
public class ConceptDraftBean implements Serializable{

    @EJB
    private ConceptManagerInterface conceptManager;

    private List<ConceptSMTK> conceptSMTKList;

    @PostConstruct
    public void init(){
        conceptSMTKList=conceptManager.getConceptDraft();
    }

    public List<ConceptSMTK> getConceptSMTKList() {
        return conceptSMTKList;
    }

    public void setConceptSMTKList(List<ConceptSMTK> conceptSMTKList) {
        this.conceptSMTKList = conceptSMTKList;
    }
}
