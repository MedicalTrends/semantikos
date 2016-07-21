package cl.minsal.semantikos.model.basictypes;

/**
 * Created by andres on 7/21/16.
 */
public class OpenInterval<T extends Comparable> implements IInterval<T> {

    @Override
    public boolean contains(T anElement) {
        return true;
    }
}
