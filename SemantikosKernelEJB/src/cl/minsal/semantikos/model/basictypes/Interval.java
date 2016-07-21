package cl.minsal.semantikos.model.basictypes;

/**
 * Esta clase representa un intervalo. El intervalo se define como un límite inferior y uno superior.
 */
public class Interval<T extends Comparable> implements IInterval<T> {

    /** Limite inferior del intervalo */
    private T bottomBoundary;

    /** Límite superior del intervalo */
    private T upperBoundary;

    public Interval(T bottomBoundary, T upperBoundary) {
        this.bottomBoundary = bottomBoundary;
        this.upperBoundary = upperBoundary;
    }

    public T getBottomBoundary() {
        return bottomBoundary;
    }

    public void setBottomBoundary(T bottomBoundary) {
        this.bottomBoundary = bottomBoundary;
    }

    public T getUpperBoundary() {
        return upperBoundary;
    }

    public void setUpperBoundary(T upperBoundary) {
        this.upperBoundary = upperBoundary;
    }

    @Override
    public boolean contains(T anElement) {
        return ((anElement.compareTo(this.upperBoundary) <= 0) &&
                (anElement.compareTo(this.bottomBoundary) >= 0));
    }
}
