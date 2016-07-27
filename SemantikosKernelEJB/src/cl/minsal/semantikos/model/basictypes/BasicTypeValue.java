package cl.minsal.semantikos.model.basictypes;

import cl.minsal.semantikos.model.relationships.Target;

/**
 *
 */
public class BasicTypeValue<T extends Comparable> implements Target {

    private BasicTypeDefinition basicTypeDefinition;

    private T value;

    /*
    public BasicTypeValue() {
        this.basicTypeDefinition = basicTypeDefinition;
        this.value = value;
    }
    */

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
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
        return getValue().toString();
    }

    public String asString(){
        return value.toString();
    }


}
