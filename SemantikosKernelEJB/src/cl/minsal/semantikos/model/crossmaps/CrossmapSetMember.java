package cl.minsal.semantikos.model.crossmaps;

import cl.minsal.semantikos.model.PersistentEntity;
import cl.minsal.semantikos.model.User;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 11/3/16.
 */
public class CrossmapSetMember extends PersistentEntity {

    /** ID de negocio */
    private long idCrossmapSetMember;

    /** Terminología a la que pertenece */
    private CrossmapSet crossmapSet;

    private String cod1;

    private String cod2;

    private String various1;
    private String various2;
    private String various3;

    private String glose;

    private Timestamp creationDate;

    private User creator;

    private Timestamp validityUntil;
}
