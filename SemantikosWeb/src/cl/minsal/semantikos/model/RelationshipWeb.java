package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipAttribute;
import cl.minsal.semantikos.model.relationships.RelationshipAttributeDefinition;
import cl.minsal.semantikos.model.relationships.Target;

import java.util.ArrayList;
import java.util.List;

public class RelationshipWeb extends Relationship implements Comparable<RelationshipWeb> {

    public boolean hasBeenModified;


    public RelationshipWeb(Relationship r) {
        super(r.getSourceConcept(),  r.getRelationshipDefinition(), new ArrayList<RelationshipAttribute>());
        if(r.getTarget() != null)
            this.setTarget(r.getTarget().copy());
        //super(r.getSourceConcept(), r.getTarget(), r.getRelationshipDefinition(), r.getRelationshipAttributes());
        this.hasBeenModified = false;

    }

    public RelationshipWeb(Relationship r, List<RelationshipAttribute> ra){
        this(r);
        for (RelationshipAttribute attr : ra) {
            this.getRelationshipAttributes().add(new RelationshipAttribute(attr.getIdRelationshipAttribute(), attr.getRelationAttributeDefinition(), attr.getRelationship(), attr.getTarget()));
        }
    }

    public RelationshipWeb(long id, Relationship r) {
        this(r);
        this.setId(id);
    }

    public RelationshipWeb(long id, Relationship r, List<RelationshipAttribute> ra) {
        this(r, ra);
        this.setId(id);
    }

    public RelationshipWeb(ConceptSMTKWeb concept, long id, Relationship r) {
        this(id, r);
        super.setSourceConcept(concept);
    }

    public RelationshipWeb(ConceptSMTKWeb concept, long id, Relationship r, List<RelationshipAttribute> ra) {
        this(id, r, ra);
        super.setSourceConcept(concept);
    }

    @Override
    public void setTarget(Target target) {
        if (target != null) {
            super.setTarget(target);
        }
    }

    public boolean hasBeenModified() {
        return hasBeenModified;
    }

    public void setModified(boolean hasBeenModified) {
        this.hasBeenModified = hasBeenModified;
    }


    public RelationshipAttribute getAttributeById(long idRelationshipAttribute){
        for (RelationshipAttribute attribute : getRelationshipAttributes()) {
            if(attribute.getIdRelationshipAttribute() == idRelationshipAttribute)
                return attribute;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        Relationship r = (Relationship) o;

        RelationshipWeb relationshipWeb = new RelationshipWeb(r, r.getRelationshipAttributes());

        boolean result = true;

        result = (this.getSourceConcept().getId() == relationshipWeb.getSourceConcept().getId() && this.getRelationshipDefinition().getId() == relationshipWeb.getRelationshipDefinition().getId() &&
                  this.getTarget().equals(relationshipWeb.getTarget()));

        for (RelationshipAttribute attribute : this.getRelationshipAttributes())
            result = result && relationshipWeb.getRelationshipAttributes().contains(attribute);

        return result;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + new Long(this.getSourceConcept().getId()).hashCode();
        result = 31 * result + new Long(this.getRelationshipDefinition().getId()).hashCode();
        result = 31 * result + new Long(this.getTarget().getId()).hashCode();
        return result;
    }


    public RelationshipAttribute getAttribute(RelationshipAttributeDefinition definition) {
        for (RelationshipAttribute attribute : getRelationshipAttributes()) {
            if (definition.equals(attribute.getRelationAttributeDefinition()))
                return attribute;
        }

        return null;
    }

    @Override
    public int compareTo(RelationshipWeb o) {
        return this.getOrder() - o.getOrder();
    }
}
