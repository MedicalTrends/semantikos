package cl.minsal.semantikos.model;

import cl.minsal.semantikos.kernel.daos.DAO;

/**
 * @author Andrés Farías on 8/29/16.
 */
public interface PersistentEntity {

    public static final long NON_PERSISTED_ID = DAO.NON_PERSISTED_ID;
    /**
     * Este método es responsable de retornar el Identificador único de la Entidad en la base de datos.
     * @return El Identificador único en la base de datos o el Identificador Nulo (<code>NON_PERSISTED_ID</code>).
     */
    public long getId();
}
