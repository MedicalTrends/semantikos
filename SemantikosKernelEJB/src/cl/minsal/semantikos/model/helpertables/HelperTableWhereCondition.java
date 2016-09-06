package cl.minsal.semantikos.model.helpertables;

/**
 * @author Andrés Farías on 9/6/16.
 */
public class HelperTableWhereCondition<T> {

    /* La columna sobre la cual se define la condición */
    private HelperTableColumn helperTableColumn;

    private ConditionalOperator conditionalOperator;

    private Object value;

    public HelperTableWhereCondition(HelperTableColumn helperTableColumn, ConditionalOperator conditionalOperator, Object value) {
        this.helperTableColumn = helperTableColumn;
        this.conditionalOperator = conditionalOperator;
        this.value = value;
    }

    @Override
    public String toString() {
        return helperTableColumn.getColumnName() + " " + conditionalOperator.toString() + " " + value;
    }
}
