package cl.minsal.semantikos.model.browser;

import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
public class ConceptQueryFilter {

    RelationshipDefinition definition;

    List<Target> targets;

    boolean multiple;

    public RelationshipDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(RelationshipDefinition definition) {
        this.definition = definition;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

}
