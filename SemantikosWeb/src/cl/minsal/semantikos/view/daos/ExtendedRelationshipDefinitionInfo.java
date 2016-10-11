package cl.minsal.semantikos.view.daos;

import cl.minsal.semantikos.model.PersistentEntity;

/**
 * @author Andrés Farías on 10/6/16.
 */
public class ExtendedRelationshipDefinitionInfo {

    public static final ExtendedRelationshipDefinitionInfo DEFAULT_CONFIGURATION = new ExtendedRelationshipDefinitionInfo(PersistentEntity.NON_PERSISTED_ID, 0);

    /** Identificador del composite */
    private final long idComposite;

    /** El orden de la relación */
    private final int order;

    public ExtendedRelationshipDefinitionInfo(long idComposite, int order) {
        this.idComposite = idComposite;
        this.order = order;
    }

    public long getIdComposite() {
        return idComposite;
    }

    public int getOrder() {
        return order;
    }
}
