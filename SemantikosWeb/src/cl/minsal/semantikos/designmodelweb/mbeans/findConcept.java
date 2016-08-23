package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.model.ConceptSMTK;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 23-08-16.
 */
@ManagedBean(name = "findConceptBean")
@ViewScoped
public class findConcept implements Serializable{

    @EJB
    private ConceptManagerInterface conceptManager;

    private List<ConceptSMTK> findConcepts;

    private ConceptSMTK conceptSMTK;



    @PostConstruct
    public void init() {
        findConcepts = new ArrayList<ConceptSMTK>();
    }

    public List<ConceptSMTK> getConceptSearchInput(String patron) {

        if (patron != null) {
            if (patron.length() > 2) {
                findConcepts=conceptManager.findConceptBy(patron);
                return findConcepts;
            }
        }
        return findConcepts;
    }

    public List<ConceptSMTK> getFindConcepts() {
        return findConcepts;
    }

    public void setFindConcepts(List<ConceptSMTK> findConcepts) {
        this.findConcepts = findConcepts;
    }

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }
}
