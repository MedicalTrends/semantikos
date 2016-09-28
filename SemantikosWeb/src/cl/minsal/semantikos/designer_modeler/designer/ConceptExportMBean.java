package cl.minsal.semantikos.designer_modeler.designer;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.model.ConceptSMTKWeb;
import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.SnomedCTRelationship;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;
import cl.minsal.semantikos.model.snomedct.RelationshipSCT;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UINamingContainer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francisco Mendez
 */
@ManagedBean(name = "conceptExport")
@ViewScoped
public class ConceptExportMBean extends UINamingContainer {

    private String init;

    public String getInit() {
        return init;
    }

    private List<ConceptBasic> conceptBasics;

    private ConceptSMTKWeb conceptSMTK;

    //80614
    @EJB
    private ConceptDAO conceptDAO;

    private List<Relationship> crossMapsRelationships;

    private List<RefSet> refSets;


    @PostConstruct
    protected void initialize() throws ParseException {


    }

    public void loadConcept() {

            conceptBasics = new ArrayList<ConceptBasic>();

            conceptBasics.add(new ConceptBasic("IDCONCEPT", conceptSMTK.getConceptID()));
            conceptBasics.add(new ConceptBasic("Categoría", conceptSMTK.getCategory().toString()));
            conceptBasics.add(new ConceptBasic("Estado", conceptSMTK.isModeled() ? "Modelado" : "Borrador"));
            conceptBasics.add(new ConceptBasic("Fecha Informe", new Timestamp(System.currentTimeMillis()).toString()));
            conceptBasics.add(new ConceptBasic("Fecha Publicación", "No implementado"));
            conceptBasics.add(new ConceptBasic("FSN", conceptSMTK.getDescriptionFSN().toString()));
            conceptBasics.add(new ConceptBasic("FSN", conceptSMTK.getDescriptionFavorite().toString()));
            conceptBasics.add(new ConceptBasic("Tipo Creación", conceptSMTK.isFullyDefined() ? "Completamente Definido" : "Primitivo"));
            conceptBasics.add(new ConceptBasic("Observación", conceptSMTK.getObservation()));

            crossMapsRelationships = new ArrayList<Relationship>();

    }


    public List<ConceptBasic> getConceptBasics() {
        return conceptBasics;
    }

    public void setConceptBasics(List<ConceptBasic> conceptBasics) {
        this.conceptBasics = conceptBasics;
    }

    public ConceptSMTKWeb getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTKWeb conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }

    /**
     * Este método es responsable de actuar como una fábrica de objetos <code>SnomedRelationship</code>.
     *
     * @return Una lista de los DTO de relaciones Snomed CT.
     */
    public List<SnomedCTRelationshipDTO> getSnomedCTRelationships() {

        List<SnomedCTRelationshipDTO> snomedCTRelationships = new ArrayList<SnomedCTRelationshipDTO>();
        for (SnomedCTRelationship relationship : conceptSMTK.getRelationshipsSnomedCT()) {
            snomedCTRelationships.add(new SnomedCTRelationshipDTO(relationship));
        }

        return snomedCTRelationships;
    }

    public List<Relationship> getCrossMapsRelationships() {
        return crossMapsRelationships;
    }

    public List<RefSet> getRefSets() {
        return refSets;
    }
}

class SnomedCTRelationshipDTO {

    private SnomedCTRelationship relationship;

    public SnomedCTRelationshipDTO(SnomedCTRelationship relationship) {
        this.relationship = relationship;
    }

    public ConceptSCT getSnomedCT() {
        return relationship.getTarget();
    }

    public String getRelationType(){
        return relationship.getRelationshipDefinition().getName();
    }
}
