package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.model.relationships.TargetDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta enumeración almacena todas las tablas auxiliares existentes.
 */
public class HelperTableFactory {

    private HelperTable atcHT;
    private List<HelperTable> helperTables = new ArrayList<>();

    private static final HelperTableFactory singletonInstance = new HelperTableFactory();

    private HelperTableFactory() {
        createHelperTables();
    }

    private void createHelperTables() {
        helperTables.add(createATC());
    }

    public List<HelperTable> getHelperTables() {
        return helperTables;
    }

    /**
     * Este método es responsable de crear una Tabla Auxiliar ATC.
     *
     * @return Una tabla ATC.
     */
    private HelperTable createATC() {
        HelperTableColumn idColumn = new HelperTableColumn("ID", true, false, false);
        HelperTableColumn codigoATCColumn = new HelperTableColumn("CODIGO_ATC", false, true, true);
        HelperTableColumn descripcionATCColumn = new HelperTableColumn("DESCRIPCION_ATC", false, true, true);

        HelperTableColumn[] columns = {idColumn, codigoATCColumn, descripcionATCColumn};
        this.atcHT = new HelperTable("ATC", "Tabla de códigos ATC", "HELPER_TABLE_ATC", columns);

        return this.atcHT;
    }

    public HelperTable getHelperTableATC() {
        return atcHT;
    }

    public static HelperTableFactory getInstance(){
        return singletonInstance;
    }
}
