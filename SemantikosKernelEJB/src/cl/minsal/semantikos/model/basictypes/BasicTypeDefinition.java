package cl.minsal.semantikos.model.basictypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa un dominio de valores básicos.
 * La única restricción, por ahora para el tipo básica
 */
public class BasicTypeDefinition<T extends Comparable> {

    /** Identificador de la BD */
    private long id;

    /** Nombre del tipo */
    private String name;

    /** Descripcion del tipo: "¿Es pedible?" */
    private String description;

    /** El dominio de valores posibles */
    private List<T> domain;

    private IInterval<T> interval;

    public BasicTypeDefinition(String name, String description) {
        this(-1, name, description);
    }

    /**
     * The full constructor available for building a Basic Type with all its attributes and the ID.
     *
     * @param id          The basic type unique ID.
     * @param name        The basic type name.
     * @param description The description about this basic type.
     */
    public BasicTypeDefinition(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.domain = new ArrayList<T>();
        this.interval = new OpenInterval<T>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean addToDomain(T anElement) {
        return domain.add(anElement);
    }

    /**
     * This is responsible for determining if an element belongs to this BasicType.
     *
     * @param anElement The element to be checked to belong in the interval.
     *
     * @return <code>true</code> if it belongs to the interval and <code>false</code> otherwise.
     */
    public boolean contains(T anElement) {
        return this.interval.contains(anElement);
    }
}
