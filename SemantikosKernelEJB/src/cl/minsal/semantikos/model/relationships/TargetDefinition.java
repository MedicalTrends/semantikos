package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

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

    /**
     * Este método es responsable de determinar si el target type es de tipo Tabla Auxiliar o no.
     *
     * @return <code>true</code> si es de tipo Tabla Auxiliar y <code>false</code> si no.
     */
    public boolean isHelperTable();

}
