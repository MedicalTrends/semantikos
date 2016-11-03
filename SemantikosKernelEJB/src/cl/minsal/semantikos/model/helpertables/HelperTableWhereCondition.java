package cl.minsal.semantikos.model.helpertables;

/**
 * @author Andrés Farías on 9/6/16.
 */
public class HelperTableWhereCondition {

    /* La columna sobre la cual se define la condición */
    protected HelperTableColumn helperTableColumn;

    protected ConditionalOperator conditionalOperator;

    protected Object value;

    public HelperTableWhereCondition(HelperTableColumn helperTableColumn, ConditionalOperator conditionalOperator, Object value) {
        this.helperTableColumn = helperTableColumn;
        this.conditionalOperator = conditionalOperator;
        this.value = value;
    }

    @Override
    public String toString() {
        return helperTableColumn.getName() + " " + conditionalOperator.toString() + " " + value;
    }
}
