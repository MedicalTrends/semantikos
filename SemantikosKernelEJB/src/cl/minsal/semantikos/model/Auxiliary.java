package cl.minsal.semantikos.model;

/**
 * Created by root on 08-07-16.
 */
public class Auxiliary implements Target {
    private long idAuxiliary;
    private Long idAccesoryTableName;
    private Long pkValueInAccesoryTable;

    public long getIdAuxiliary() {
        return idAuxiliary;
    }

    public void setIdAuxiliary(long idAuxiliary) {
        this.idAuxiliary = idAuxiliary;
    }

    public Long getIdAccesoryTableName() {
        return idAccesoryTableName;
    }

    public void setIdAccesoryTableName(Long idAccesoryTableName) {
        this.idAccesoryTableName = idAccesoryTableName;
    }

    public Long getPkValueInAccesoryTable() {
        return pkValueInAccesoryTable;
    }

    public void setPkValueInAccesoryTable(Long pkValueInAccesoryTable) {
        this.pkValueInAccesoryTable = pkValueInAccesoryTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auxiliary that = (Auxiliary) o;

        if (idAuxiliary != that.idAuxiliary) return false;
        if (idAccesoryTableName != null ? !idAccesoryTableName.equals(that.idAccesoryTableName) : that.idAccesoryTableName != null)
            return false;
        if (pkValueInAccesoryTable != null ? !pkValueInAccesoryTable.equals(that.pkValueInAccesoryTable) : that.pkValueInAccesoryTable != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idAuxiliary ^ (idAuxiliary >>> 32));
        result = 31 * result + (idAccesoryTableName != null ? idAccesoryTableName.hashCode() : 0);
        result = 31 * result + (pkValueInAccesoryTable != null ? pkValueInAccesoryTable.hashCode() : 0);
        return result;
    }
}
