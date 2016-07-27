package cl.minsal.semantikos.model.helpertables;

/**
 * Created by andres on 7/27/16.
 */
public class HelperTableColumn {

    private String columnName;
    private final boolean isPK;
    private final boolean isSearchable;
    private final boolean isShown;

    /**
     * This is the default and only constructor for HelperTableColumn.
     *
     * @param columnName The column phisic name.
     * @param isPK Is it a PK?
     * @param isSearchable is it searchable?
     * @param isShown is to be shown or retrieved?
     */
    public HelperTableColumn(String columnName, boolean isPK, boolean isSearchable, boolean isShown) {
        this.columnName = columnName;
        this.isPK = isPK;
        this.isSearchable = isSearchable;
        this.isShown = isShown;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
