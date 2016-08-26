package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.relationships.Relationship;

public class RelationshipWeb extends Relationship {

    public boolean hasBeenModified;

    public RelationshipWeb(Relationship r, boolean hasBeenModified) {
        super(r.getSourceConcept(), r.getTarget(), r.getRelationshipDefinition());
        this.hasBeenModified = hasBeenModified;
    }

    public RelationshipWeb(long id, Relationship r, boolean hasBeenModified) {
        super(r.getSourceConcept(), r.getTarget(), r.getRelationshipDefinition());
        this.hasBeenModified = hasBeenModified;
        this.setId(r.getId());
    }

    public boolean hasBeenModified() {
        return hasBeenModified;
    }

    public void setModified(boolean hasBeenModified) {
        this.hasBeenModified = hasBeenModified;
    }
}
