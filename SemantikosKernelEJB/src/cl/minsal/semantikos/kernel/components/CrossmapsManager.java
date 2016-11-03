package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.DirectCrossmaps;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.crossmaps.CrossmapSetMember;
import cl.minsal.semantikos.model.crossmaps.Crossmaps;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías on 8/30/16.
 */
@Local
public interface CrossmapsManager {


    /**
     * Este método es responsable de crear un CrossMap TODO: Por definir bien.
     *
     * @param crossmaps El crossmap a crear.
     * @param user      El usuario que desea crear el CrossMap
     *
     * @return El crossmap creado.
     */
    public Crossmaps createCrossMap(Crossmaps crossmaps, User user);

    /**
     * Este método es responsable de eliminar un CrossMap de un concepto. TODO: Por definir bien qué significa eliminar
     * un crossmap.
     *
     * @param crossmaps El crossmap que se desea eliminar.
     * @param user      El usuario que elimina el crossmap.
     *
     * @return El crossmap eliminado y actualizado.
     */
    public Crossmaps removeCrossMap(Crossmaps crossmaps, User user);

    /**
     * Este método es responsable de recuperar los crossmaps de un concepto y actualizarle su lista de crossmaps. Si el
     * <code>conceptSMTK</code> no es persistente, se recuperan los crossmaps asociados a su <code>CONCEPT_ID</code>.
     *
     * @param conceptSMTK El concepto cuyos Crossmaps se desea recuperar.
     *
     * @return La lista de Crossmaps asociados al concepto <code>conceptSMTK</code>.
     */
    public List<Crossmaps> getCrossmaps(ConceptSMTK conceptSMTK);

    /**
     * Este método es responsable de recuperar los crossmaps de un concepto y actualizarle su lista de crossmaps. Si el
     * <code>conceptSMTK</code> no es persistente, se recuperan los crossmaps asociados a su <code>CONCEPT_ID</code>.
     *
     * @param conceptSMTK El concepto cuyos Crossmaps se desea recuperar.
     *
     * @return La lista de Crossmaps asociados al concepto <code>conceptSMTK</code>.
     */
    public List<Crossmaps> getDirectCrossmaps(ConceptSMTK conceptSMTK);

    /**
     * Este método es responsable de recuperar los crossmaps indirectos de un concepto y actualizarle su lista de
     * crossmaps. Si el
     * <code>conceptSMTK</code> no es persistente, se recuperan los crossmaps asociados a su <code>CONCEPT_ID</code>.
     *
     * @param conceptSMTK El concepto cuyos Crossmaps se desea recuperar.
     *
     * @return La lista de Crossmaps asociados al concepto <code>conceptSMTK</code>.
     */
    public List<Crossmaps> getIndirectCrossmaps(ConceptSMTK conceptSMTK);

    /**
     * Este método es responsable de establecer un crossmap directo hacia un término de otra terminología.
     *
     * @param conceptSMTK       El concepto SMTK.
     * @param crossmapSetMember El término en la terminología externa.
     *
     * @return El crossmap creado.
     */
    public DirectCrossmaps bindConceptSMTKToCrossmapSetMember(ConceptSMTK conceptSMTK, CrossmapSetMember crossmapSetMember);

    /**
     * Este método es responsable de eliminar un crossmap de un concepto.
     *
     * @param crossmaps El crossmap que se desea eliminar.
     */
    public void remove(Crossmaps crossmaps);

}
