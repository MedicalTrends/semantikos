package cl.minsal.semantikos.model.helpertables;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * @author Andrés Farías
 */
public class HelperTableRecordFactory {

    /** The Singleton instance of this class */
    private static final HelperTableRecordFactory singleton = new HelperTableRecordFactory();

    private ObjectMapper mapper = new ObjectMapper();

    private HelperTableRecordFactory() {
    }

    /**
     * Método único para obtener la instancia de esta clase.
     *
     * @return La instancia singleton de esta clase.
     */
    public static HelperTableRecordFactory getInstance() {
        return singleton;
    }

    /**
     * Este método es responsable de crear un HelperTable Record a partir de un objeto JSON.
     *
     * @param jsonExpression El objeto JSON a partir del cual se crea el objeto. El formato JSON será:
     *                       <code>{"TableName":"helper_table_atc","records":[{"id":1,"codigo_atc":"atc1"}</code>
     *
     * @return Un objeto fresco de tipo <code>HelperTableRecord</code> creado a partir del objeto JSON.
     *
     * @throws IOException Arrojada si hay un problema.
     */
    public HelperTableRecord createRecordFromJSON(String jsonExpression) throws IOException {
        JSONHelperTableRecord jsonHelperTableRecord = mapper.readValue(jsonExpression, JSONHelperTableRecord.class);

        HelperTable helperTable = HelperTableFactory.getInstance().getHelperTable(jsonHelperTableRecord.getTableName());
        return new HelperTableRecord(helperTable, jsonHelperTableRecord.getFields());
    }

    /**
     * Este método es responsable de crear un HelperTable Record a partir de un objeto JSON.
     *
     * @param jsonExpression El objeto JSON a partir del cual se crea el objeto. El formato JSON será:
     *                       <code>{"TableName":"helper_table_atc","records":[{"id":1,"codigo_atc":"atc1"}</code>
     *
     * @return Un objeto fresco de tipo <code>HelperTableRecord</code> creado a partir del objeto JSON.
     *
     * @throws IOException Arrojada si hay un problema.
     */
    public List<HelperTableRecord> createHelperRecordsFromJSON(String jsonExpression) throws IOException {

        JSONHelperTableRecords jsonHelperTableRecord = mapper.readValue(jsonExpression, JSONHelperTableRecords.class);
        HelperTable helperTable = HelperTableFactory.getInstance().getHelperTable(jsonHelperTableRecord.getTableName());

        List<HelperTableRecord> records = new ArrayList<>();
        for (Map<String, String> fields : jsonHelperTableRecord.getRecords()) {
            HelperTableRecord helperTableRecord = new HelperTableRecord(helperTable, fields);
            records.add(helperTableRecord);
        }

        return records;
    }

    public List<HelperTable> createHelperTablesFromJSON(String jsonExpression) throws IOException {
        HelperTable[] jsonHelperTables = mapper.readValue(jsonExpression, HelperTable[].class);

        return Arrays.asList(jsonHelperTables);
    }


    public HelperTable createHelperTableFromJSON(String jsonExpression) throws IOException {
        HelperTableJSON jsonHelperTable = mapper.readValue(jsonExpression, HelperTableJSON.class);

        return new HelperTable(jsonHelperTable.getName(), jsonHelperTable.getDescription(), jsonHelperTable.getTablaName(), jsonHelperTable.getColumns());
    }
}

/**
 * Esta clase tiene como propósito dar una representación simple de un record para ser transformado automáticamente
 * desde JSON.
 */
class JSONHelperTableRecord {

    /** El nombre de la tabla auxiliar */
    private String tableName;

    /** La llave primaria del registro */
    private long id;

    private Map<String, String> fields;

    public JSONHelperTableRecord() {
        this.fields = new HashMap<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}

/**
 * Esta clase tiene como propósito dar una representación simple de varios registros para ser transformado
 * automáticamente desde JSON.
 */
class JSONHelperTableRecords {

    /** El nombre de la tabla auxiliar */
    private String tableName;

    /** La llave primaria del registro */
    private long id;

    private List<Map<String, String>> records;

    public JSONHelperTableRecords() {
        this.records = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public List<Map<String, String>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, String>> records) {
        this.records = records;
    }
}

/**
 * Esta clase tiene como propósito dar una representación simple de varios registros para ser transformado
 * automáticamente desde JSON.
 */
class HelperTableJSON {

    /** Un nombre legible por humanos para la Tabla Auxiliar */
    private String name;

    /* Una breve descripción de la tabla auxiliar */
    private String description;

    /** El nombre de la tabla física */
    private String tablaName;

    /** El nombre de las columnas que posee la tabla física */
    private Collection<HelperTableColumn> columns;


    public HelperTableJSON() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTablaName() {
        return tablaName;
    }

    public void setTablaName(String tablaName) {
        this.tablaName = tablaName;
    }

    public Collection<HelperTableColumn> getColumns() {
        return columns;
    }

    public void setColumns(Collection<HelperTableColumn> columns) {
        this.columns = columns;
    }
}

