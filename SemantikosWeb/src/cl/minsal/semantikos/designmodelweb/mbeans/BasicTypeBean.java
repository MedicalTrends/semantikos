package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.model.RelationshipDefinition;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;

import javax.annotation.PostConstruct;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import java.text.ParseException;

/**
 * Created by root on 22-07-16.
 */
@FacesComponent(value="basicTypeBean") // To be specified in componentType attribute.
@SuppressWarnings({"rawtypes", "unchecked"}) // We don't care about the actual model item type anyway.
public class BasicTypeBean extends UINamingContainer {

    BasicTypeDefinition basicTypeDefinition;

    BasicTypeValue basicTypeValue;

    @PostConstruct
    protected void initialize() throws ParseException {


    }

    public BasicTypeValue getBasicTypeValue() {
        return basicTypeValue;
    }

    public void setBasicTypeValue(BasicTypeValue basicTypeValue) {
        this.basicTypeValue = basicTypeValue;
    }

    public BasicTypeDefinition getBasicTypeDefinition() {
        return basicTypeDefinition;
    }

    public void setBasicTypeDefinition(BasicTypeDefinition basicTypeDefinition) {
        this.basicTypeDefinition = basicTypeDefinition;
    }

}
