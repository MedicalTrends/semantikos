package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Andrés Farías
 */
public interface HelperTableManagerInterface {

    /**
     * Este método es responsable de proveer una lista de objetos que representan las Tablas Auxiliares.
     *
     * @return Una lista de <code>HelperTable</code> disponibles.
     */
    public Collection<HelperTable> getHelperTables();

    /**
     *
     * @param helperTable
     * @param columnNames
     * @return
     */
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable, String[] columnNames);

    /**
     *
     * @param helperTable
     * @return
     */
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable);
}
