package cl.minsal.semantikos.model.helpertables;

/**
 * @author Andrés Farías on 9/6/16.
 */
public enum ConditionalOperator {

    GREATER_OR_GREATER_THAN(">"), EQUALS("=");

    private String representation;

    ConditionalOperator(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
