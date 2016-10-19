package cl.minsal.semantikos.ws.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Development on 2016-10-14.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "objetivo")
public class TargetResponse implements Serializable {

    @XmlElement(name="id")
    private Long id;
    @XmlElement(name="tipoObjetivo")
    private TargetTypeResponse targetTypeResponse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TargetTypeResponse getTargetTypeResponse() {
        return targetTypeResponse;
    }

    public void setTargetTypeResponse(TargetTypeResponse targetTypeResponse) {
        this.targetTypeResponse = targetTypeResponse;
    }
}
