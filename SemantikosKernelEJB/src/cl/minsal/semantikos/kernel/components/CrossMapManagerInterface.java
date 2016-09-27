package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.relationships.CrossMap;
import cl.minsal.semantikos.model.User;

import javax.ejb.Local;

/**
 * @author Andrés Farías on 8/30/16.
 */
@Local
public interface CrossMapManagerInterface {


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
}
