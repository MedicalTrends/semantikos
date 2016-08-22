package cl.minsal.semantikos.model.basictypes;

import cl.minsal.semantikos.model.relationships.TargetDefinition;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa un dominio de valores básicos.
 * La única restricción, por ahora para el tipo básica
 */
public class BasicTypeDefinition<T extends Comparable> implements TargetDefinition {

    /** Identificador de la BD */
    private long id;

    /** Nombre del tipo */
    private String name;

    /** Descripción del tipo: "¿Es pedible?" */
    private String description;

    /** El dominio de valores posibles */
    private List<T> domain;

    private Interval<T> interval;

    public BasicTypeDefinition() {
    }

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
        this.interval = new EmptyInterval<>();
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

    public List<T> getDomain() {
        return domain;
    }

    public Interval<T> getInterval() {
        return interval;
    }

    public void setInterval(Interval<T> interval) {
        this.interval = interval;
    }


    public void setDomain(List<T> domain) {
        this.domain = domain;
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

        /* Primero se busca en el intervalo */
        if (this.interval.contains(anElement))
            return true;


        /* Luego en el conjunto de valores discretos */
        return this.domain.contains(anElement);
    }

    /**
     * Este método es responsable de indicar si el tipo tiene valores concretos o discretos.
     *
     * @return <code>true</code> si es un dominio discreto de valores y <code>false</code> sino (dominio continuo).
     */
    public boolean isDiscreteDomain() {

        /* Si el tipo tiene un dominio discreto (no vacío), y no tiene un intervalo definido, entonces es discreto */
        return !this.domain.isEmpty() && this.interval.isEmpty();

    }

    @Override
    public boolean isBasicType() {
        return true;
    }

    @Override
    public boolean isSMTKType() {
        return false;
    }

    @Override
    public boolean isHelperTable() {
        return false;
    }

    @Override
    public boolean isSnomedCTType() {
        return false;
    }

    @Override
    public boolean isCrossMapType() {
        return false;
    }
}
