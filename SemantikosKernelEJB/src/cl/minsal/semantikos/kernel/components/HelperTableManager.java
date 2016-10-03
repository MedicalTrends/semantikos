package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @author Andrés Farías
 */
public interface HelperTableManager {

    /**
     * Este método es responsable de proveer una lista de objetos que representan las Tablas Auxiliares.
     *
     * @return Una lista de <code>HelperTable</code> disponibles.
     */
    public Collection<HelperTable> getHelperTables();

    /**
     * Este método es responsable de recuperar los registros de una cierta tabla.
     *
     * @param helperTable La tabla cuyos registros se desea recuperar.
     * @param columnNames El nombre de las columnas que se desea recuperar.
     *
     * @return Una lista de registros de la tabla <code>helperTable</code> con las columnas <code>columnNames</code>
     * indicadas.
     */
    public List<HelperTableRecord> getAllRecords(@NotNull HelperTable helperTable, List<String> columnNames);

    /**
     * Este método es responsable de recuperar todos los registros de una tabla auxiliar que se encuentran vigentes.
     *
     * @param helperTable La tabla cuyos registros se desea recuperar.
     * @param columnNames El nombre de las columnas que se desea recuperar.
     *
     * @return Una lista de registros de la tabla <code>helperTable</code> con las columnas <code>columnNames</code>
     * indicadas que se encuentran vigentes en este momento.
     */
    public List<HelperTableRecord> getValidRecords(@NotNull HelperTable helperTable, List<String> columnNames);

    List<HelperTableRecord> searchValidRecords(@NotNull HelperTable helperTable, List<String> columnNames, String query);

    /**
     * @return
     */
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable);

    HelperTableRecord getRecord(int recordId);

    /**
     * Este método es responsable de recuperar una tabla dado su identificador.
     *
     * @param id El ID de la tabla
     *
     * @return La tabla auxiliar.
     */
    public HelperTable findHelperTableByID(long id);
}
