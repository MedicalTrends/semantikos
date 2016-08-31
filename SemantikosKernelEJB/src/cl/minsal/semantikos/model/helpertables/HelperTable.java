package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.kernel.daos.HelperTableDAOImpl;
import cl.minsal.semantikos.model.PersistentEntity;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by root on 08-07-16.
 */
public class HelperTable extends PersistentEntity implements TargetDefinition {

    /** Un nombre legible por humanos para la Tabla Auxiliar */
    private String name;

    /* Una breve descripción de la tabla auxiliar */
    private String description;

    /** El nombre de la tabla física */
    private String tablaName;

    /** Columna con la llave primaria de los registros de la tabla auxiliar */
    private String pkColumnName;

    /** El nombre de las columnas que posee la tabla física */
    private Collection<HelperTableColumn> columns;

    /**
     * Este constructor permite crear un objeto <code>HelperTable</code> que no ha sido persistido en la base de datos
     * (sin ID).
     */
    public HelperTable(String name, String description, String tablaName, Collection<HelperTableColumn> columns) {
        this(-1, name, description, tablaName, columns);
    }

    /**
     * Constructor completo. Tiene los principales valores por defecto.
     *
     * @param id          Identificador único de la tabla.
     * @param name        Nombre de la tabla
     * @param description Descripción de la tabla.
     * @param tablaName   Nombre de la Tabla en la Base de Datos.
     * @param columns     Columnas de la tabla auxiliar.
     */
    public HelperTable(long id, String name, String description, String tablaName, Collection<HelperTableColumn> columns) {
        super(id);
        this.name = name;
        this.description = description;
        this.tablaName = tablaName;
        this.columns = columns;

        /* El nombre por defecto de la columna de PK es ID */
        this.pkColumnName = HelperTableDAOImpl.ID_COLUMN_NAME;
    }

    public Collection<HelperTableColumn> getColumns() {
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
     *
     * @return Un objeto <code>java.util.List</code> con las columnas parametrizadas como mostrables para esa tabla.
     */
    public List<HelperTableColumn> getShowableColumns() {
        List<HelperTableColumn> showableColumns = new ArrayList<>();
        for (HelperTableColumn column : columns) {
            if (column.isShowable()) {
                showableColumns.add(column);
            }
        }

        return showableColumns;
    }

    /**
     * Este método es responsable de retornar una lista con los nombres de todas las columnas.
     *
     * @return Un <code>java.util.List</code> con los nombres de las columnas.
     */
    public List<String> getAllColumnsNames() {
        return extractColumnsName(columns);
    }

    /**
     * Este método es responsable de retornar una lista con los nombres las columnas dadas como parámetro.
     *
     * @param columns Las columnas cuyos nombres se desea obtener
     *
     * @return Un <code>java.util.List</code> con los nombres de las columnas.
     */
    public List<String> extractColumnsName(Collection<HelperTableColumn> columns) {
        List<String> columnNames = new ArrayList<>();
        for (HelperTableColumn column : columns) {
            columnNames.add(column.getColumnName());
        }

        return columnNames;
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

    @Override
    public boolean isSnomedCTType() {
        return false;
    }

    @Override
    public boolean isCrossMapType() {
        return false;
    }

    /**
     * Este método es responsable de retornar el nombre de las columnas mostrables.
     *
     * @return Un arreglo de <code>String</code> con los nombres de las columnas mostrables.
     */
    public List<String> getShowableColumnsNames() {

        List<String> showableColumnsNames = new ArrayList<>();
        for (HelperTableColumn showableColumn : getShowableColumns()) {
            showableColumnsNames.add(showableColumn.getColumnName());
        }

        return showableColumnsNames;
    }
}
