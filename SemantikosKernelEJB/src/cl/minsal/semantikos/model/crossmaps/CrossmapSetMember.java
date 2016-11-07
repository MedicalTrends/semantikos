package cl.minsal.semantikos.model.crossmaps;

import cl.minsal.semantikos.model.PersistentEntity;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetType;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 11/3/16.
 */
public class CrossmapSetMember extends PersistentEntity implements Target {

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

    @Override
    public TargetType getTargetType() {
        return TargetType.CrossMap;
    }

    @Override
    public Target copy() {
        // TODO: Terminar esto.
        return null;
    }
}
