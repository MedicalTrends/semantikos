package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetType;

/**
 * @author Andrés Farías
 */
public class CrossMap implements Target{

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.CrossMap;
    }
}
