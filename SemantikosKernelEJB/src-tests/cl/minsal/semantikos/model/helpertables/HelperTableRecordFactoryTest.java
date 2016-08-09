package cl.minsal.semantikos.model.helpertables;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HelperTableRecordFactoryTest {

    /** La instancia a probar */
    private HelperTableRecordFactory helperTableRecordFactory = HelperTableRecordFactory.getInstance();

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Este test verifica la creación local de un simple Record.
     *
     * @throws Exception
     */
    @Test
    public void createHelperTableDummyRecordsTest() throws Exception {
        JSONHelperTableRecords singleColumnDummyRecord = createHelperTableDummyRecords();
        assertEquals(singleColumnDummyRecord.getRecords().size(), 2);
    }

    /**
     * Este test permite verificar que un JSON que representa dos registros de una tabla (generado en la BDD) es
     * parseado a la clase intermedia correctamente.
     *
     * @throws Exception
     */
    @Test
    public void transformJSON2Records() throws Exception {
        JSONHelperTableRecords twoRecords = mapper.readValue(createJSONRecords(), JSONHelperTableRecords.class);

        assertTrue(twoRecords.getTableName().equalsIgnoreCase("helper_table_atc"));
        assertEquals(twoRecords.getRecords().size(), 2);
    }

    /**
     * Este test permite verificar que un JSON que representa un registro único de una tabla auxiliar es parseado
     * correctamente a la clase intermedia.
     *
     * @throws Exception
     */
    @Test
    public void transformJSON2IntermediateRecord() throws Exception {
        JSONHelperTableRecord aRecord = mapper.readValue(createJSONRocord(), JSONHelperTableRecord.class);

        assertTrue(aRecord.getTableName().equalsIgnoreCase("helper_table_atc"));
        assertEquals(aRecord.getFields().size(), 3);
    }

    @Test
    public void transformJSON2SingleRecord() throws Exception {
        String jsonRocord = createJSONRocord();
        HelperTableRecord recordFromJSON = this.helperTableRecordFactory.createRecordFromJSON(jsonRocord);
        assertEquals(3, recordFromJSON.getFields().size());
    }





    private String createJSONRecords() {
        return "{\"tableName\":\"helper_table_atc\",\"records\":[{\"id\":1,\"codigo_atc\":\"atc1\",\"descripcion_atc\":\"Esta es una descripción ATC\"}, \n" +
                " {\"id\":2,\"codigo_atc\":\"atc2\",\"descripcion_atc\":\"Esta es otra descripción ATC\"}]}";
    }


    private String createJSONRocord() {
        return "{\"tableName\":\"helper_table_atc\",\"fields\":{\"id\":1,\"codigo_atc\":\"atc1\",\"descripcion_atc\":\"Esta es una descripción ATC\"}}";
    }

    private JSONHelperTableRecord createDummyRecord() {
        Map<String, String> records = new HashMap<>();
        records.put("id", "1");
        records.put("cod", "A");

        JSONHelperTableRecord jsonHelperTableRecord = new JSONHelperTableRecord();
        jsonHelperTableRecord.setFields(records);
        jsonHelperTableRecord.setTableName("ACT");

        return jsonHelperTableRecord;
    }

    private JSONHelperTableRecords createHelperTableDummyRecords() {

        Map<String, String> record1 = new HashMap<>();
        record1.put("id", "1");
        record1.put("cod", "A");

        Map<String, String> record2 = new HashMap<>();
        record2.put("id", "2");
        record2.put("cod", "B");

        List<Map<String, String>> records = Arrays.asList(record1, record2);

        JSONHelperTableRecords jsonHelperTableRecord = new JSONHelperTableRecords();
        jsonHelperTableRecord.setRecords(records);
        jsonHelperTableRecord.setTableName("ACT");

        return jsonHelperTableRecord;
    }

    private String createJSONManyRecords() {
        return "{\"tableName\":\"helper_table_dci\",\"records\":[{\"id\":1,\"description\":\"descripcion dci 1\",\"creation_date\":null,\"user_register\":1,\"is_valid\":true,\"delete_date\":null}, \n" +
                " {\"id\":2,\"description\":\"descripcion dci 2\",\"creation_date\":null,\"user_register\":1,\"is_valid\":true,\"delete_date\":null}]}";
    }

    @Test
    public void transformJSON3SingleRecord() throws Exception {
        String jsonRocord = createJSONManyRecords();
        List<HelperTableRecord> recordFromJSON = this.helperTableRecordFactory.createRecordsFromJSON(jsonRocord);
        assertEquals(2, recordFromJSON.size());
    }
}