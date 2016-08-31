package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.relationships.Relationship;

public class RelationshipWeb extends Relationship {

    public boolean hasBeenModified;

    public RelationshipWeb(Relationship r) {
        super(r.getSourceConcept(), r.getTarget(), r.getRelationshipDefinition());
        this.hasBeenModified = false;
    }

    public RelationshipWeb(long id, Relationship r) {
        this(r);
        this.setId(id);
    }

    public boolean hasBeenModified() {
        return hasBeenModified;
    }

    public void setModified(boolean hasBeenModified) {
        this.hasBeenModified = hasBeenModified;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        RelationshipWeb relationshipWeb = new RelationshipWeb((Relationship) o);
        return (this.getSourceConcept().getId()==relationshipWeb.getSourceConcept().getId() && this.getRelationshipDefinition().getId()==relationshipWeb.getRelationshipDefinition().getId() && this.getTarget().getId()==relationshipWeb.getTarget().getId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + new Long(this.getSourceConcept().getId()).hashCode();
        result = 31 * result + new Long(this.getRelationshipDefinition().getId()).hashCode();
        result = 31 * result + new Long(this.getTarget().getId()).hashCode();
        return result;
    }
}
