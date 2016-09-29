package cl.minsal.semantikos.model.browser;

import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
public class ConceptQueryFilter {

    TargetDefinition definition;

    List<Target> targets;

    public TargetDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(TargetDefinition definition) {
        this.definition = definition;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

}
