package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.IPersistentEntity;

/**
 * @author Andres Farias
 */
public interface TargetDefinition extends IPersistentEntity {

    /**
     * Este metodo es responsable de determinar si el target type es de tipo básico es o no.
     *
     * @return <code>true</code> si es de tipo básico y <code>false</code> sino.
     */
    public boolean isBasicType();

    /**
     * Este método es responsable de determinar si el target type es de tipo SMTK o no.
     *
     * @return <code>true</code> si es de tipo SMTK y <code>false</code> si no.
     */

    public boolean isSMTKType();

    /**
     * Este método es responsable de determinar si el target type es de tipo Tabla Auxiliar o no.
     *
     * @return <code>true</code> si es de tipo Tabla Auxiliar y <code>false</code> si no.
     */
    public boolean isHelperTable();

    /**
     * Este método es responsable de determinar si el target type es de tipo Concept SCT o no.
     *
     * @return <code>true</code> si es de tipo Snomed CT y <code>false</code> si no.
     */
    public boolean isSnomedCTType();

    /**
     * Este método es responsable de determinar si el target type es de tipo CrossMap o no.
     *
     * @return <code>true</code> si es de tipo CrossMap y <code>false</code> si no.
     */
    public boolean isCrossMapType();
}