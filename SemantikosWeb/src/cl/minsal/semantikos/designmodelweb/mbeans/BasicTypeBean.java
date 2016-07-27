package cl.minsal.semantikos.designmodelweb.mbeans;

import javax.annotation.PostConstruct;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import java.text.ParseException;

/**
 * Created by root on 22-07-16.
 */
@FacesComponent(value="basicTypeBean") // To be specified in componentType attribute.
@SuppressWarnings({"rawtypes", "unchecked"}) // We don't care about the actual model item type anyway.
public class BasicTypeBean<T extends Comparable> extends UINamingContainer {

    //BasicTypeDefinition basicTypeDefinition;

    //BasicTypeValue basicTypeValue = new BasicTypeValue();


    T value = (T) "";

    @PostConstruct
    protected void initialize() throws ParseException {


    }

    /*
    public BasicTypeValue getBasicTypeValue() {

        return basicTypeValue;
    }

    public void setBasicTypeValue(BasicTypeValue basicTypeValue) {
        System.out.println("setBasicTypeValue");
        this.basicTypeValue = basicTypeValue;
    }
    */

    /*
    public BasicTypeDefinition getBasicTypeDefinition() {
        return basicTypeDefinition;
    }

    public void setBasicTypeDefinition(BasicTypeDefinition basicTypeDefinition) {
        this.basicTypeDefinition = basicTypeDefinition;
    }
    */

    public String getValue() {
        System.out.println("setValue");
        return value.toString();
    }

    public void setValue(T value) {
        System.out.println("setValue");
        this.value = value;
    }
    /*
    public List<T> getDomain() {
        return domain;
    }

    public void setDomain(List<T> domain) {
        this.domain = domain;
    }
    */


}
