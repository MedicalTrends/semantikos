package cl.minsal.semantikos.ws.response;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Development on 2016-10-11.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "concepto")
public class ConceptResponse implements Serializable {

    @XmlElement(name="id")
    private Long id;
    @XmlElement(name="idConcepto")
    private String conceptId;
    @XmlElement(name="aSerRevisado")
    private Boolean isToBeReviewed;
    @XmlElement(name="aSerConsultado")
    private Boolean isToBeConsulted;
    @XmlElement(name="modelado")
    private Boolean modeled;
    @XmlElement(name="completamenteDefinido")
    private Boolean isFullyDefined;
    @XmlElement(name="publicado")
    private Boolean isPublished;
    @XmlElement(name="validoHasta")
    private Date validUntil;
    @XmlElement(name="observacion")
    private String observation;
    @XmlElement(name="tagSMTK")
    private TagSMTKResponse tagSMTK;
    @XmlElement(name="categoria")
    private CategoryResponse category;
    @XmlElement(name="descripcionPreferida")
    private DescriptionResponse preferedDescription;
    @XmlElement(name="descripcionFSN")
    private DescriptionResponse fsnDescription;
    @XmlElementWrapper(name = "descripciones")
    @XmlElement(name="descripcion")
    private List<DescriptionResponse> descriptions;
    @XmlElementWrapper(name = "relaciones")
    @XmlElement(name="relacion")
    private List<RelationshipResponse> relationships;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public Boolean getToBeReviewed() {
        return isToBeReviewed;
    }

    public void setToBeReviewed(Boolean toBeReviewed) {
        isToBeReviewed = toBeReviewed;
    }

    public Boolean getToBeConsulted() {
        return isToBeConsulted;
    }

    public void setToBeConsulted(Boolean toBeConsulted) {
        isToBeConsulted = toBeConsulted;
    }

    public Boolean getModeled() {
        return modeled;
    }

    public void setModeled(Boolean modeled) {
        this.modeled = modeled;
    }

    public Boolean getFullyDefined() {
        return isFullyDefined;
    }

    public void setFullyDefined(Boolean fullyDefined) {
        isFullyDefined = fullyDefined;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public TagSMTKResponse getTagSMTK() {
        return tagSMTK;
    }

    public void setTagSMTK(TagSMTKResponse tagSMTK) {
        this.tagSMTK = tagSMTK;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public DescriptionResponse getPreferedDescription() {
        return preferedDescription;
    }

    public void setPreferedDescription(DescriptionResponse preferedDescription) {
        this.preferedDescription = preferedDescription;
    }

    public DescriptionResponse getFsnDescription() {
        return fsnDescription;
    }

    public void setFsnDescription(DescriptionResponse fsnDescription) {
        this.fsnDescription = fsnDescription;
    }

    public List<DescriptionResponse> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<DescriptionResponse> descriptions) {
        this.descriptions = descriptions;
    }

    public List<RelationshipResponse> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<RelationshipResponse> relationships) {
        this.relationships = relationships;
    }
}