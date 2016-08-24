package cl.minsal.semantikos.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Andrés Farías on 8/23/16.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = State.class, name = "State") })
public interface IState {

    /**
     * Este método retorna el nombre del estado.
     *
     * @return El nombre del estado.
     */
    public String getName();

    /**
     * Este método es responsable de retornar el identificador único del estado en la base de datos.
     *
     * @return El ID de la BDD del estado.
     */
    public long getId();
}
