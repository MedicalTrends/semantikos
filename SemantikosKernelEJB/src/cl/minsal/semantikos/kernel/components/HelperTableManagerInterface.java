package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.helpertables.HelperTable;

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
    public List<HelperTable> getHelperTables();

    /**
     *
     * @param helperTable
     * @param columnNames
     * @return
     */
    public List<Map<String, String>> getAllRecords(HelperTable helperTable, String[] columnNames);

    /**
     *
     * @param helperTable     
     * @return
     */
    public List<Object> getAllRecords(HelperTable helperTable);


}
