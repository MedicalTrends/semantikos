package cl.minsal.semantikos.model.basictypes;

import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class BasicTypeValue<T extends Comparable> implements Target {

    private static final Logger logger = LoggerFactory.getLogger(BasicTypeValue.class);

    /** Identificador único de la base de datos */
    private long id;

    private T value;

    public BasicTypeValue(T value) {
        this.value = value;
        this.id = -1;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.BasicType;
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
