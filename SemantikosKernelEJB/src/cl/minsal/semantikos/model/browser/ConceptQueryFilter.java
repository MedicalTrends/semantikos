package cl.minsal.semantikos.model.browser;

import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.BasicTypeType;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
public class ConceptQueryFilter {

    RelationshipDefinition definition;

    List<Target> targets = new ArrayList<Target>();

    boolean multiple;

    public ConceptQueryFilter(RelationshipDefinition definition) {
        this.definition = definition;
    }

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

    public List<Long> getHelperTableValues(){

        List<Long> helperTableValues = new ArrayList<>();

        if(definition.getTargetDefinition().isHelperTable()){
            for (Target target : targets) {
                HelperTableRecord helperTableRecord = (HelperTableRecord) target;
                helperTableValues.add(helperTableRecord.getId());
            }
        }

        return helperTableValues;
    }

    public List<String> getBasicTypeValues(){

        List<String> basicTypeValues = new ArrayList<>();

        if(definition.getTargetDefinition().isBasicType()){
            for (Target target : targets) {
                BasicTypeValue basicTypeValue = (BasicTypeValue) target;
                basicTypeValues.add(basicTypeValue.getValue().toString());
            }
        }

        return basicTypeValues;

    }

}
