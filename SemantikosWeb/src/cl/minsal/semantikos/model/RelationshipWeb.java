package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.audit.AuditableEntity;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import static cl.minsal.semantikos.kernel.daos.ConceptDAO.NON_PERSISTED_ID;

public class RelationshipWeb extends Relationship {

    public boolean hasBeenModified;

    public RelationshipWeb(Relationship r, boolean hasBeenModified){
        super(r.getSourceConcept(), r.getTarget(), r.getRelationshipDefinition());
        this.hasBeenModified = hasBeenModified;
    }

    public RelationshipWeb(long id, Relationship r, boolean hasBeenModified){
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
