package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * @author Andrés Farías
 */
@Local
public interface HelperTableDAO {

    /**
     * Este método es responsable de recuperar un registro desde una tabla auxiliar <code>helperTable</code>.
     *
     * @param helperTable La tabla auxiliar desde la cual se recupera el registro.
     * @param idRecord    El ID del registro a recuperar en la tabla <code>helperTable</code>.
     *
     * @return Un registro constituido como un mapping entre nombres de columna y valores.
     */
    public Map<String, String> getRecord(HelperTable helperTable, long idRecord);

    /**
     * Este método es responsable de buscar registros en una tabla auxiliar que contengan el patrón en alguna de sus
     * columnas buscables.
     *
     * @param helperTable La tabla en la cual buscar.
     * @param pattern     El patrón a buscar en los registros.
     *
     * @return Los registros que satisfacen el criterio de búsquda.
     */
    public List<Map<String, String>> findRecordsByPattern(HelperTable helperTable, String pattern);

    /**
     * Este método es responsable de recuperar todos los registros de una tabla auxiliar, recuperando sólo las columnas
     * indicadas.
     *
     * @param helperTable La tabla cuyos registros son recuperados.
     * @param columnNames Las columnas de los registros a recuperar (más el ID).
     *
     * @return Una lista de registros (en forma de mappings) de la tabla indicada.
     */
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable, String[] columnNames);

    /**
     * Este método es responsable de recuperar todos los registros de una tabla auxiliar, recuperando sólo las columnas
     * indicadas como <i>mostrables</i> (showables == <code>true</code>)en la tabla.
     *
     * @param helperTable La tabla cuyos registros son recuperados.
     *
     * @return Una lista de registros (en forma de mappings) de la tabla indicada.
     */
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable);
}
