package cl.minsal.semantikos.model.basictypes;

import java.util.Comparator;

/**
 *
 */
public class BasicTypeValue<T extends Comparator> {

    private BasicTypeDefinition basicTypeDefinition;

    private T value;

    public BasicTypeValue(BasicTypeDefinition basicTypeDefinition, T value) {
        this.basicTypeDefinition = basicTypeDefinition;
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
