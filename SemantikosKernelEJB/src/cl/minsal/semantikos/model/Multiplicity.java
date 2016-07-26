package cl.minsal.semantikos.model;

/**
 * Esta clase representa una multiplicidad a lo UML: [1-2] [0-100] [0-*]
 */
public class Multiplicity {

    public Multiplicity() {
    }

    /** Limite inferior */
    private int lowerBoundary;

    /** Limite superior */
    private int upperBoundary;

    /**
     * Constructor por defecto.
     *
     * @param lowerBoundary Límite inferior
     * @param upperBoundary Límite superior
     */
    public Multiplicity(int lowerBoundary, int upperBoundary) {
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
    }

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
