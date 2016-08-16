package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.ConceptSMTK;

import java.sql.Timestamp;

/**
 * @author Andrés Farías
 */
public interface Target {

    /**
     * Este método es responsable de retornar el ID del target.
     * @return El identificador único en la base de datos del target.
     */
    public long getId();
}
