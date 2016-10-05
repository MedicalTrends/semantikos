package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.Multiplicity;

/**
 * @author Andrés Farías on 10/5/16.
 */
public class RelationshipDefinitionWeb extends RelationshipDefinition {

    /** El identificador del composite que se quiere usar en las vistas */
    private long compositeID;

    public RelationshipDefinitionWeb(long id, String name, String description, TargetDefinition targetDefinition, Multiplicity multiplicity, long compositeID) {
        super(id, name, description, targetDefinition, multiplicity);
        this.compositeID = compositeID;
    }

    public long getCompositeID() {
        return compositeID;
    }
}
