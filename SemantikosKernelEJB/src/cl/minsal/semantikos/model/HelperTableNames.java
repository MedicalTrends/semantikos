package cl.minsal.semantikos.model;

/**
 * TODO: Esta clase tiene como objetivo representar ...
 */
public class HelperTableNames {

    private long idAccesoryTableName;
    private String tableAccesoryName;
    private String pkColumnName;

    public long getIdAccesoryTableName() {
        return idAccesoryTableName;
    }

    public void setIdAccesoryTableName(long idAccesoryTableName) {
        this.idAccesoryTableName = idAccesoryTableName;
    }

    public String getTableAccesoryName() {
        return tableAccesoryName;
    }

    public void setTableAccesoryName(String tableAccesoryName) {
        this.tableAccesoryName = tableAccesoryName;
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HelperTableNames that = (HelperTableNames) o;

        if (idAccesoryTableName != that.idAccesoryTableName) return false;
        if (tableAccesoryName != null ? !tableAccesoryName.equals(that.tableAccesoryName) : that.tableAccesoryName != null)
            return false;
        if (pkColumnName != null ? !pkColumnName.equals(that.pkColumnName) : that.pkColumnName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idAccesoryTableName ^ (idAccesoryTableName >>> 32));
        result = 31 * result + (tableAccesoryName != null ? tableAccesoryName.hashCode() : 0);
        result = 31 * result + (pkColumnName != null ? pkColumnName.hashCode() : 0);
        return result;
    }
}
