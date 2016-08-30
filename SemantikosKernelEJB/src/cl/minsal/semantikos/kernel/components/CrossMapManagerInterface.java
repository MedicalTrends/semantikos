package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.CrossMap;
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
}
