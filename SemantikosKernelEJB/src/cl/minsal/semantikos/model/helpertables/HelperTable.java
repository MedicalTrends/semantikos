package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 08-07-16.
 */
public class HelperTable implements TargetDefinition {

    /** Un nombre legible por humanos para la Tabla Auxiliar */
    private String name;

    /* Una breve descripción de la tabla auxiliar */
    private String description;

    /** El nombre de la tabla física */
    private String tablaName;

    /** Columna con la llave primaria de los registros de la tabla auxiliar */
    private String pkColumnName;

    /** El nombre de las columnas que posee la tabla física */
    private HelperTableColumn[] columns;

    public HelperTable(String name, String description, String tablaName, HelperTableColumn[] columns) {
        this.name = name;
        this.description = description;
        this.tablaName = tablaName;
        this.columns = columns;

        /* El nombre por defecto de la columna de PK es ID */
        this.pkColumnName = "ID";
    }

    public HelperTableColumn[] getColumns() {
        return columns;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTablaName() {
        return tablaName;
    }

    /**
     * Este método es responsable de retornar aquellas columnas que han sido definidas como <i>mostrables</i>.
     * @return
     */
    public List<HelperTableColumn> getShowableColumns() {
        List<HelperTableColumn> showableColumns = new ArrayList<>();
        for (HelperTableColumn column : columns) {
            if (column.isShowable()) { showableColumns.add(column); }
        }

        return showableColumns;
    }

    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public boolean isSMTKType() {
        return false;
    }

    @Override
    public boolean isHelperTable() {
        return true;
    }
}
