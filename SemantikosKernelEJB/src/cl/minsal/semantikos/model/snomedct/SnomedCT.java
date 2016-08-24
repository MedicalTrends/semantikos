package cl.minsal.semantikos.model.snomedct;

import cl.minsal.semantikos.model.relationships.TargetDefinition;

/**
 * Esta clase, representa la terminología internacional estándard SNOMED CT.
 * @author Andrés Farías
 */
public class SnomedCT implements TargetDefinition{
    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public boolean isSMTKType() {
        return false;
    }

    @Override
    public boolean isHelperTable() {
        return false;
    }

    @Override
    public boolean isSnomedCTType() {
        return true;
    }

    @Override
    public boolean isCrossMapType() {
        return false;
    }
}
