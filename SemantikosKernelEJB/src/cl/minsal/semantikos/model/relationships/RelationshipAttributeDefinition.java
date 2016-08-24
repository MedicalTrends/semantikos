package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.Multiplicity;

/**
 * @author Andrés Farías
 */
public class RelationshipAttributeDefinition {

    /** Identificador único de la entidad */
    private long id;

    /** El valor del atributo de la relación */
    private TargetDefinition target;

    /** Nombre del atributo */
    private String name;

    /** Multiplicidad */
    private Multiplicity multiplicity;

    public RelationshipAttributeDefinition(long id, TargetDefinition target, String name, Multiplicity multiplicity) {
        this.id = id;
        this.target = target;
        this.name = name;
        this.multiplicity = multiplicity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TargetDefinition getTarget() {
        return target;
    }

    public void setTarget(TargetDefinition target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Multiplicity getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(Multiplicity multiplicity) {
        this.multiplicity = multiplicity;
    }
}
