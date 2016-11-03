package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.CrossMap;
import cl.minsal.semantikos.model.User;

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
     * @param crossMap El crossmap a crear.
     * @param user     El usuario que desea crear el CrossMap
     *
     * @return El crossmap creado.
     */
    public CrossMap createCrossMap(CrossMap crossMap, User user);

    /**
     * Este método es responsable de eliminar un CrossMap de un concepto. TODO: Por definir bien qué significa eliminar
     * un crossmap.
     *
     * @param crossMap El crossmap que se desea eliminar.
     * @param user     El usuario que elimina el crossmap.
     *
     * @return El crossmap eliminado y actualizado.
     */
    public CrossMap removeCrossMap(CrossMap crossMap, User user);

    /**
     * Este método es responsable de recuperar los crossmaps de un concepto y actualizarle su lista de crossmaps. Si el
     * <code>conceptSMTK</code> no es persistente, se recuperan los crossmaps asociados a su <code>CONCEPT_ID</code>.
     *
     * @param conceptSMTK El concepto cuyos Crossmaps se desea recuperar.
     *
     * @return La lista de Crossmaps asociados al concepto <code>conceptSMTK</code>.
     */
    public List<CrossMap> getCrossmaps(ConceptSMTK conceptSMTK);

    /**
     * Este método es responsable de recuperar los crossmaps de un concepto y actualizarle su lista de crossmaps. Si el
     * <code>conceptSMTK</code> no es persistente, se recuperan los crossmaps asociados a su <code>CONCEPT_ID</code>.
     *
     * @param conceptSMTK El concepto cuyos Crossmaps se desea recuperar.
     *
     * @return La lista de Crossmaps asociados al concepto <code>conceptSMTK</code>.
     */
    public List<CrossMap> getDirectCrossmaps(ConceptSMTK conceptSMTK);

    /**
     * Este método es responsable de recuperar los crossmaps de un concepto y actualizarle su lista de crossmaps. Si el
     * <code>conceptSMTK</code> no es persistente, se recuperan los crossmaps asociados a su <code>CONCEPT_ID</code>.
     *
     * @param conceptSMTK El concepto cuyos Crossmaps se desea recuperar.
     *
     * @return La lista de Crossmaps asociados al concepto <code>conceptSMTK</code>.
     */
    public List<CrossMap> getIndirectCrossmaps(ConceptSMTK conceptSMTK);
}
