package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.CrossMap;
import cl.minsal.semantikos.model.relationships.Target;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Local
public interface CrossmapsDAO {

    /**
     * Este método es responsable de recuperar un CrossMap a partir de la base de datos.
     * @param idCrossMap El identificador único en la base de datos.
     * @return Un CrossMap fresco creado a partir de la base de datos.
     */
    public CrossMap getCrossMapByID(long idCrossMap);

    List<CrossMap> getCrossmapsByIdConcept(long id);

    List<CrossMap> getCrossmapsByConceptID(String conceptID);
}
