package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmaps;
import cl.minsal.semantikos.model.crossmaps.Crossmaps;
import cl.minsal.semantikos.model.crossmaps.CrossmapSetMember;

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
    public Crossmaps getCrossMapByID(long idCrossMap);

    List<Crossmaps> getIndirectCrossmapsByIdConcept(long id);

    List<Crossmaps> getIndirectCrossmapsByConceptID(String conceptID);

    List<Crossmaps> getDirectCrossmapsByIdConcept(long id);

    List<Crossmaps> getDirectCrossmapsByConceptID(String conceptID);

    DirectCrossmaps bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember);
}
