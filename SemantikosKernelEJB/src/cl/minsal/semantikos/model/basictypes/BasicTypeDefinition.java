package cl.minsal.semantikos.model.basictypes;

import cl.minsal.semantikos.model.TargetDefinition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    /** Descripcion del tipo: "¿Es pedible?" */
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

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = String[].class, name="String[]"),
            @JsonSubTypes.Type(Integer[].class)
    })
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

    public boolean isIntervalType(){
        if(this.interval != null)
            return true;
        else
            return false;
    }

    public boolean isDomainType(){
        return !this.domain.isEmpty();
    }

    public String intervalTypeOf(){
        if(this.interval != null) {
            if (this.interval.bottomBoundary instanceof java.lang.Integer)
                return "Integer";
            if (this.interval.bottomBoundary instanceof java.lang.String)
                return "String";
            if (this.interval.bottomBoundary instanceof java.util.Date)
                return "Date";
        }
        return "";
    }

    public String domainTypeOf(){
        if (this.domain.get(0) instanceof java.lang.Integer)
            return "Integer";
        if (this.domain.get(0) instanceof java.lang.String)
            return "String";
        if (this.domain.get(0) instanceof java.util.Date)
            return "Date";
        return "";
    }


    @Override
    public boolean isBasicType() {
        return true;
    }
}
