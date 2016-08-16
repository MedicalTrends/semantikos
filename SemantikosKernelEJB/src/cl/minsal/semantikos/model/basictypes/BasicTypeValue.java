package cl.minsal.semantikos.model.basictypes;

import cl.minsal.semantikos.model.relationships.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class BasicTypeValue<T extends Comparable> implements Target {

    private static final Logger logger = LoggerFactory.getLogger(BasicTypeValue.class);

    /** Identificador único de la base de datos */
    private long id;

    private BasicTypeDefinition basicTypeDefinition;

    private T value;

    /*
    public BasicTypeValue() {
        this.basicTypeDefinition = basicTypeDefinition;
        this.value = value;
    }
    */

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value)

    {
        logger.debug("seteando valor de target valor={}", value);
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof BasicTypeValue) && ( String.valueOf(value) != null )
                ? String.valueOf(value).equals(String.valueOf(((BasicTypeValue) other).value))
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(value) != null)
                ? (this.getClass().hashCode() + String.valueOf(value).hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        //return String.format("ExampleEntity[%d, %s]", idDescriptionType, glosa);
        if(this.getValue()!=null)
            return getValue().toString();
        else
            return "null";
    }

    public String asString(){
        return value.toString();
    }

}
