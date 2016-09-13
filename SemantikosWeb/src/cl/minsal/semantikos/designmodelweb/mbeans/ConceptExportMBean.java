package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.snomedct.SnomedCT;

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

    private List<ConceptBasic> conceptBasics;

    private ConceptSMTK conceptSMTK;

    //80614
    @EJB
    private ConceptDAO conceptDAO;

    private List<Relationship> crossMapsRelationships;

    private List<RefSet> refSets;

    @PostConstruct
    protected void initialize() throws ParseException {
        conceptBasics = new ArrayList<ConceptBasic>();

        conceptSMTK=conceptDAO.getConceptByID(80614);

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

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    /**
     * Este método es responsable de actuar como una fábrica de objetos <code>SnomedRelationship</code>.
     *
     * @return Una lista de los DTO de relaciones Snomed CT.
     */
    public List<SnomedRelationship> getSnomedCTRelationships() {

        List<SnomedRelationship> snomedCTRelationships = new ArrayList<SnomedRelationship>();
        for (Relationship relationship : conceptSMTK.getRelationshipsSnomedCT()) {
            snomedCTRelationships.add(new SnomedRelationship(relationship));
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

class SnomedRelationship {

    private Relationship relationship;

    public SnomedRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public SnomedCT getSnomedCT() {
        return (SnomedCT) relationship.getTarget();
    }
}
