package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.model.PersistentEntity;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Andrés Farías
 */
public class HelperTable extends PersistentEntity implements TargetDefinition {


    /** Un nombre legible por humanos para la Tabla Auxiliar */
    private String name;

    /* Una breve descripción de la tabla auxiliar */
    private String description;

    /** El nombre de las columnas que posee la tabla física */
    private Collection<HelperTableColumn> columns;

    /**
     * Este constructor permite crear un objeto <code>HelperTable</code> que no ha sido persistido en la base de datos
     * (sin ID).
     */
    public HelperTable(String name, String description, @NotNull Collection<HelperTableColumn> columns) {
        this(-1, name, description, columns);
    }

    /**
     * Constructor completo. Tiene los principales valores por defecto.
     *
     * @param id          Identificador único de la tabla.
     * @param name        Nombre de la tabla
     * @param description Descripción de la tabla.
     * @param columns     Columnas de la tabla auxiliar.
     */
    public HelperTable(long id, String name, String description, @NotNull Collection<HelperTableColumn> columns) {
        super(id);
        this.name = name;
        this.description = description;
        this.columns = new ArrayList<>(columns);
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


    /**
     * Este método es responsable de retornar una lista con los nombres de todas las columnas.
     *
     * @return Un <code>java.util.List</code> con los nombres de las columnas.
     */
    public List<String> getAllColumnsNames() {


        List<String> columnNames = new ArrayList<>();
        for (HelperTableColumn column : columns) {
            columnNames.add(column.getName());
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


}
