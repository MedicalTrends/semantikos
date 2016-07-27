package cl.minsal.semantikos.model.relationships;

/**
 * @author Andres Farias
 */
public interface TargetDefinition {

    /**
     * Este metodo es responsable de determinar si el target type es de tipo básico es o no.
     *
     * @return <code>true</code> si es de tipo básico y <code>false</code> sino.
     */
    public boolean isBasicType();

    public boolean isSMTKType();
}
