package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.model.relationships.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Andrés Farías
 */
public class HelperTableRecord implements Target {

    private static final Logger logger = LoggerFactory.getLogger(HelperTableRecord.class);

    /** La llave primaria del registro */
    private long id;

    /** Un registro puede ser vigente o no */
    private boolean isValid;

    /** La tabla auxiliar referenciada */
    private HelperTable helperTable;

    /** Los campos de la tabla */
    private Map<String, String> fields;

    /**
     * Este constructor permite crear un objeto no persitido (aun) con valores para un registro de una tabla auxiliar.
     *
     * @param helperTable La tabla auxiliar a la que pertenece el registro.
     * @param fields      Los valores del registro.
     */
    public HelperTableRecord(HelperTable helperTable, Map<String, String> fields) {
        this.helperTable = helperTable;
        this.id = -1;
        this.fields = fields;
    }

    /**
     * El constructor básico que requiere ambos campos.
     *
     * @param helperTable La tabla auxiliar en cuestión.
     * @param id La llave primaria del registro de la tabla auxiliar.
     * @param id          La llave primaria del registro de la tabla auxiliar.
     */
    public HelperTableRecord(HelperTable helperTable, long id) {
        this.helperTable = helperTable;
        this.id = id;
    }

    /**
     * Este constructor vacio se provee para JSON.
     */
    public HelperTableRecord() {
        this(HelperTableFactory.getInstance().getHelperTableATC(), -1);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public HelperTable getHelperTable() {
        return helperTable;
    }

    public void setHelperTable(HelperTable helperTable) {
        this.helperTable = helperTable;
    }

    /**
     * Este método es responsable de retornar el valor asociado a una columna con el nombre <code>columnName</code>.
     *
     * @param columnName El nombre de la columna cuyo valor es requerido.
     *
     * @return El valor asociado a la columna <code>columnName</code>.
     *
     * @throws IllegalArgumentException Arrojada si el nombre de la columna no existe.
     */
    public String getValueColumn(String columnName) throws IllegalArgumentException {

        /* Si la columna no existe es lanzada la excepción */
        if (this.fields.containsKey(columnName)){
            String messageError = "Se solicita columan que no existe: " + columnName;
            logger.error(messageError);
            throw new IllegalArgumentException(messageError);
        }

        return this.fields.get(columnName);
    }
}
