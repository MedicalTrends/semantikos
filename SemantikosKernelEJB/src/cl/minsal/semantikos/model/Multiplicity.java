package cl.minsal.semantikos.model;

/**
 * Esta clase representa una multiplicidad a lo UML: [1-2] [0-100] [0-*]
 */
public class Multiplicity {

    /** Limite inferior */
    private int lowerBoundary;

    /** Limite superior */
    private int upperBoundary;

    public int getUpperBoundary() {
        return upperBoundary;
    }

    public void setUpperBoundary(int upperBoundary) {
        this.upperBoundary = upperBoundary;
    }

    public int getLowerBoundary() {
        return lowerBoundary;
    }

    public void setLowerBoundary(int lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }
}
