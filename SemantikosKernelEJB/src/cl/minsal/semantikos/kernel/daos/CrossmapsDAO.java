package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmap;
import cl.minsal.semantikos.model.crossmaps.Crossmap;
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
    public Crossmap getCrossMapByID(long idCrossMap);

    List<Crossmap> getIndirectCrossmapsByIdConcept(long id);

    List<Crossmap> getIndirectCrossmapsByConceptID(String conceptID);

    List<Crossmap> getDirectCrossmapsByIdConcept(long id);

    List<Crossmap> getDirectCrossmapsByConceptID(String conceptID);

    DirectCrossmap bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember);
}
