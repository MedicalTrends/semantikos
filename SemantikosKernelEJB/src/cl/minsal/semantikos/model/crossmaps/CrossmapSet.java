package cl.minsal.semantikos.model.crossmaps;

import cl.minsal.semantikos.model.PersistentEntity;
import cl.minsal.semantikos.model.User;

import java.sql.Timestamp;

/**
 * Esta clase implementa el concepto de terminología externa
 * @author Andrés Farías on 11/3/16.
 */
public class CrossmapSet extends PersistentEntity{

    /** Identificador de negocio */
    private long idCrossmapSet;

    private String abbreviatedName;

    private String name;

    /** Año de la versión */
    private int version;

    /** Fecha de creación */
    private Timestamp creationDate;

    /** Usuario que creo la terminología */
    private User creator;

    /** Fecha <em>hasta</em> de vigencia */
    private Timestamp validityUntil;
}
