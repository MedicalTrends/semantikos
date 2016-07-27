package cl.minsal.semantikos.model.factories;

import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta enumeración almacena todas las tablas auxiliares existentes.
 */
public class HelperTableFactory {

    public List<HelperTable> getHelperTables(){

        List<HelperTable> helperTables = new ArrayList<>();

        HelperTableColumn idColumn = new HelperTableColumn("ID", true, false, false);
        HelperTableColumn codigoATCColumn = new HelperTableColumn("CODIGO_ATC", false, true, true);
        HelperTableColumn descripcionATCColumn = new HelperTableColumn("DESCRIPCION_ATC", false, true, true);

        HelperTableColumn[] columns = {idColumn, codigoATCColumn, descripcionATCColumn};
        helperTables.add(new HelperTable("ATC", "Tabla de códigos ATC", "HELPER_TABLE_ATC", columns));

        return helperTables;
    }
}
