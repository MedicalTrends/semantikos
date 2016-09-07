package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.helpertables.HelperTableWhereCondition;

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
     * indicadas como <i>mostrables</i> (showables == <code>true</code>)en la tabla.
     *
     * @param helperTable La tabla cuyos registros son recuperados.
     *
     * @return Una lista de registros (en forma de mappings) de la tabla indicada.
     */
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable);

    /**
     * Este método es responsable de recuperar todos los registros de una tabla auxiliar, recuperando sólo las columnas
     * indicadas.
     *
     * @param helperTable La tabla cuyos registros son recuperados.
     * @param columnNames Las columnas de los registros a recuperar (más el ID).
     *
     * @return Una lista de registros (en forma de mappings) de la tabla indicada.
     */
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable, List<String> columnNames);

    /**
     * Este método es responsable de recuperar todos lso registros de una tabla auxiliar que cumplen una cierta
     * condición respecto a sus columnas.
     *
     * @param helperTable     La tabla cuyos registros son recuperados.
     * @param columnNames     Las columnas de los registros a recuperar (más el ID).
     * @param whereConditions Condiciones que deben satisfacer los registros.
     *
     * @return Una lista de registros (en forma de mappings) de la tabla indicada que cumplen las condiciones.
     */
    public List<HelperTableRecord> getRecords(HelperTable helperTable, List<String> columnNames, List<HelperTableWhereCondition> whereConditions);

    /**
     * Este método es responsable de recuperar un registro desde una tabla auxiliar/plana.
     *
     * @param idHelperTableRecord El ID del HelperTableRecord.
     *
     * @return Un registro (<code>HelperTableRecord</code>).
     */
    public HelperTableRecord getHelperTableRecordFromId(long idHelperTableRecord);
}
