package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.model.RelationshipDefinition;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 * Created by root on 22-07-16.
 */
@FacesComponent(value="basicTypeBean") // To be specified in componentType attribute.
@SuppressWarnings({"rawtypes", "unchecked"}) // We don't care about the actual model item type anyway.
public class BasicTypeBean extends UINamingContainer {

    public BasicTypeDefinition getBasicTypeDefinition() {
        return basicTypeDefinition;
    }

    public void setBasicTypeDefinition(BasicTypeDefinition basicTypeDefinition) {
        this.basicTypeDefinition = basicTypeDefinition;
    }

    BasicTypeDefinition basicTypeDefinition;

}
