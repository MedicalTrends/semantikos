package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.CrossMap;
import cl.minsal.semantikos.model.relationships.Target;

import javax.ejb.Local;

/**
 * @author Andrés Farías
 */
@Local
public interface CrossMapDAO {

    /**
     * Este método es responsable de recuperar un CrossMap a partir de la base de datos.
     * @param idCrossMap El identificador único en la base de datos.
     * @return Un CrossMap fresco creado a partir de la base de datos.
     */
    public CrossMap getCrossMapByID(long idCrossMap);
}
