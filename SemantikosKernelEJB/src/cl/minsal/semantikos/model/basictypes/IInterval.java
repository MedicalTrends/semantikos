package cl.minsal.semantikos.model.basictypes;

/**
 * Created by andres on 7/21/16.
 */
public interface IInterval<T extends Comparable> {

    /**
     * This is responsible for determining if an element belongs to this interval.
     *
     * @param anElement The element to be checked to belong in the interval.
     *
     * @return <code>true</code> if it belongs to the interval and <code>false</code> otherwise.
     */
    public boolean contains(T anElement);
}