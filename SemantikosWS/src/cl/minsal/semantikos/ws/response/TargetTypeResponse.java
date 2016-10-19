package cl.minsal.semantikos.ws.response;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Development on 2016-10-14.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tipoObjetivo")
public class TargetTypeResponse implements Serializable {

    @XmlElement(name="id")
    private Long id;
    @XmlElement(name="nombre")
    private String name;
    @XmlElement(name="descripcion")
    private String description;
    @XmlElementWrapper(name = "definicionesObjetivo")
    @XmlElement(name="definicionObjetivo")
    private List<TargetDefinitionResponse> targetDefinitions;
    @XmlElementWrapper(name = "definicionesTipoBasico")
    @XmlElement(name="definicionTipoBasico")
    private List<BasicTypeDefinitionResponse> basicTypeDefinitions;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TargetDefinitionResponse> getTargetDefinitions() {
        return targetDefinitions;
    }

    public void setTargetDefinitions(List<TargetDefinitionResponse> targetDefinitions) {
        this.targetDefinitions = targetDefinitions;
    }

    public List<BasicTypeDefinitionResponse> getBasicTypeDefinitions() {
        return basicTypeDefinitions;
    }

    public void setBasicTypeDefinitions(List<BasicTypeDefinitionResponse> basicTypeDefinitions) {
        this.basicTypeDefinitions = basicTypeDefinitions;
    }
}
