package cl.minsal.semantikos.model;

/**
 * @author Andrés Farías on 8/23/16.
 */
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
