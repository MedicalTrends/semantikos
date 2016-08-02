package cl.minsal.semantikos.model.helpertables;

import java.util.*;

/**
 * Esta enumeración almacena todas las tablas auxiliares existentes.
 */
public class HelperTableFactory {

    private HelperTable atcHT;
    private Map<String, HelperTable> helperTables = new HashMap<>();

    private static final HelperTableFactory singletonInstance = new HelperTableFactory();

    private HelperTableFactory() {
        createHelperTables();
    }

    private void createHelperTables() {
        HelperTable atcHelperTable = createATC();
        helperTables.put(atcHelperTable.getTablaName(), atcHelperTable);
    }

    public Collection<HelperTable> getHelperTables() {
        return helperTables.values();
    }

    /**
     * Este método es responsable de crear una Tabla Auxiliar ATC.
     *
     * @return Una tabla ATC.
     */
    private HelperTable createATC() {
        HelperTableColumn idColumn = new HelperTableColumn("id", true, false, false);
        HelperTableColumn codigoATCColumn = new HelperTableColumn("codigo_atc", false, true, true);
        HelperTableColumn descripcionATCColumn = new HelperTableColumn("descripcion_atc", false, true, true);

        HelperTableColumn[] columns = {idColumn, codigoATCColumn, descripcionATCColumn};
        this.atcHT = new HelperTable((long) 1, "ATC", "Tabla de códigos ATC", "HELPER_TABLE_ATC", columns);

        return this.atcHT;
    }

    public HelperTable getHelperTableATC() {
        return atcHT;
    }

    public static HelperTableFactory getInstance() {
        return singletonInstance;
    }

    /**
     * Este método es responsable de retornar la tabla auxiliar cuyo ID coincide con el parámetro.
     *
     * @param idHelperTable Identificador único de la tabla auxiliar que se desea recuperar.
     *
     * @return Una instancia de la tabla.
     *
     * @throws java.lang.IllegalArgumentException Si no existe una tabla con dicho ID.
     */
    public HelperTable getHelperTable(long idHelperTable) throws IllegalArgumentException {

        switch ((int) idHelperTable) {
            case 1:
                return this.atcHT;
        }

        throw new IllegalArgumentException("No existe tabla auxiliar con ID = " + idHelperTable);
    }

    /**
     * Este método es responsable de retornar una Tabla Auxiliar a partir de su nombre.
     * @param tableName El nombre de la tabla auxiliar que se desea recuperar.
     * @return La tabla auxiliar de nombre <code>tableName</code>.
     */
    public HelperTable getHelperTable(String tableName) {
        return null;
    }
}