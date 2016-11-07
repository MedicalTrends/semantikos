package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmap;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.crossmaps.Crossmap;
import cl.minsal.semantikos.model.crossmaps.CrossmapSet;
import cl.minsal.semantikos.model.crossmaps.CrossmapSetMember;
import cl.minsal.semantikos.model.crossmaps.IndirectCrossmap;
import cl.minsal.semantikos.model.relationships.Target;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Local
public interface CrossmapsDAO {

    /**
     * Este método es responsable de crear un Crossmap
     *
     * @param directCrossmap El crossmap a crear.
     * @param user           El usuario que desea crear el CrossMap
     *
     * @return El crossmap creado.
     */
    public DirectCrossmap create(DirectCrossmap directCrossmap, User user);

    /**
     * Este método es responsable de recuperar un CrossMap Directo desde la base de datos.
     *
     * @param id El identificador único en la base de datos.
     *
     * @return Un CrossMap Directo fresco creado a partir de la base de datos.
     */
    public DirectCrossmap getDirectCrossmapById(long id);

    List<IndirectCrossmap> getIndirectCrossmapsByIdConcept(long id);

    List<IndirectCrossmap> getIndirectCrossmapsByConceptID(String conceptID);

    public List<DirectCrossmap> getDirectCrossmapsByIdConcept(long id);

    List<DirectCrossmap> getDirectCrossmapsByConceptID(String conceptID);

    DirectCrossmap bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember);

    /**
     * Este método es responsable de recuperar un CrossmapSetMember por su ID de la base de datos.
     *
     * @param idCrossmapSetMember El ID del crossmapSet que se desea recuperar.
     *
     * @return Un CrossmapSetMember fresco.
     */
    public CrossmapSetMember getCrossmapSetMemberById(long idCrossmapSetMember);
}
