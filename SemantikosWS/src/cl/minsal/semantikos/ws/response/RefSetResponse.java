package cl.minsal.semantikos.ws.response;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Development on 2016-10-13.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "refSet")
public class RefSetResponse implements Serializable {

    @XmlElement(name="id")
    private Long id;
    @XmlElement(name="nombre")
    private String name;
    @XmlElement(name="validoHasta")
    private Date validityUntil;
    @XmlElement(name="creadoEn")
    private Date creationDate;
    @XmlElement(name="institucion")
    private InstitutionResponse institution;
    @XmlElementWrapper(name = "conceptos")
    @XmlElement(name="concepto")
    private List<ConceptResponse> concepts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstitutionResponse getInstitution() {
        return institution;
    }

    public void setInstitution(InstitutionResponse institution) {
        this.institution = institution;
    }

    public Date getValidityUntil() {
        return validityUntil;
    }

    public void setValidityUntil(Date validityUntil) {
        this.validityUntil = validityUntil;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<ConceptResponse> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<ConceptResponse> concepts) {
        this.concepts = concepts;
    }
}
