package cl.minsal.semantikos.designer_modeler.designer;

import cl.minsal.semantikos.kernel.components.SnomedCTManager;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * @author Gustavo Punucura
 * @created 26-07-16.
 */

@ManagedBean(name = "sctBean")
@ViewScoped
public class SCTTypeBean implements Serializable {

    @EJB
    private SnomedCTManager cstManager;

    private String pattern;

    /** El concepto seleccionado en la JSP */
    private ConceptSCT conceptSelected;

    /** Elementos ... */
    private Map<Long, LazyDataModel<ConceptSMTK>> conceptSearchMap;

    private List<ConceptSMTK> conceptSel;

    /**
     * Constructor por defecto para la inicialización de componentes.
     */
    public SCTTypeBean() {
        conceptSearchMap = new HashMap<Long, LazyDataModel<ConceptSMTK>>();
    }

    public List<ConceptSMTK> getConceptSel() {
        return conceptSel;
    }

    public void setConceptSel(List<ConceptSMTK> conceptSel) {
        this.conceptSel = conceptSel;
    }

    /**
     * Este método realiza la búsqueda del auto-complete, recuperando todos los conceptos (mostrando su toString()) SCT
     * cuyas descripciones coinciden con el patrón buscado.
     *
     * @param patron El patrón de búsqueda.
     *
     * @return Una lista con los conceptos a desplegar.
     */
    public List<ConceptSCT> getConceptSearchInput(String patron) {

        /* Si el patrón viene vacío o es menor a tres caracteres, no se hace nada */
        if (patron == null || patron.trim().length() <= 3) {
            return emptyList();
        }

        /* La búsqueda empieza aquí */
        return cstManager.findConceptsByPattern(patron);
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

    public ConceptSCT getConceptSelected() {
        return conceptSelected;
    }

    public void setConceptSelected(ConceptSCT conceptSelected) {
        this.conceptSelected = conceptSelected;
    }


}
