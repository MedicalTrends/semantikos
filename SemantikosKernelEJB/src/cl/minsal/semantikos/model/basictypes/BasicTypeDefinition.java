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

    public boolean isHasDomain(){
        return !this.domain.isEmpty();
    }

    public boolean isInteger() {
        return ((this.interval.lowerBoundary instanceof java.lang.Long || this.interval.upperBoundary instanceof java.lang.Long) && this.domain.isEmpty());
    }

    public boolean isString() {
        return ((this.interval.lowerBoundary instanceof java.lang.String || this.interval.upperBoundary instanceof java.lang.String) && this.domain.isEmpty());
    }

    public boolean isDate() {
        return ((this.interval.lowerBoundary instanceof java.util.Date || this.interval.upperBoundary instanceof java.util.Date) && this.domain.isEmpty()) ;
    }

    public boolean isFloat() {
        //System.out.println("isFloat="+(this.interval.lowerBoundary instanceof java.lang.Float || this.interval.upperBoundary instanceof java.lang.Float));
        return ((this.interval.lowerBoundary instanceof java.lang.Float || this.interval.upperBoundary instanceof java.lang.Float) && this.domain.isEmpty());
    }


    public String typeOf(){
        if(this.domain != null){
            if (this.domain.get(0) instanceof java.lang.Integer)
                return "Integer";
            if (this.domain.get(0) instanceof java.lang.String)
                return "String";
            if (this.domain.get(0) instanceof java.util.Date)
                return "Date";
        }
        if(this.interval != null) {
            if (this.interval.lowerBoundary instanceof java.lang.Integer)
                return "Integer";
            if (this.interval.lowerBoundary instanceof java.lang.String)
                return "String";
            if (this.interval.lowerBoundary instanceof java.util.Date)
                return "Date";
        }
        return "";
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

    public List<String> asString(){
        return (List<String>)domain;
    }
}
