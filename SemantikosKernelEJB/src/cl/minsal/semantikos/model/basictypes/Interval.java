package cl.minsal.semantikos.model.basictypes;

/**
 * Esta clase representa un intervalo de elementos ordenables (comparables).
 */
public abstract class Interval<T extends Comparable> {

    /** Limite inferior del intervalo */
    protected T lowerBoundary;

    /** Límite superior del intervalo */
    protected T upperBoundary;

    protected Interval() {
    }

    protected Interval(T bottomBoundary, T upperBoundary) {
        this.lowerBoundary = bottomBoundary;
        this.upperBoundary = upperBoundary;
    }

    public T getLowerBoundary() {
        return lowerBoundary;
    }

    public void setLowerBoundary(T lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }

    public T getUpperBoundary() {
        return upperBoundary;
    }

    public void setUpperBoundary(T upperBoundary) {
        this.upperBoundary = upperBoundary;
    }

    /**
     * This is responsible for determining if an element belongs to this interval.
     *
     * @param anElement The element to be checked to belong in the interval.
     *
     * @return <code>true</code> if it belongs to the interval and <code>false</code> otherwise.
     */
    public abstract boolean contains(T anElement);
}