package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableColumn;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.helpertables.HelperTableRecordFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @author Andrés Farías
 */
@Stateless
public class HelperTableDAOImpl implements HelperTableDAO {

    /** Logger de la clase */
    private static final Logger logger = LoggerFactory.getLogger(HelperTableDAOImpl.class);

    /** Nombre de la columna de Vigencia */
    public static final String IS_VALID_COLUMN_NAME = "is_valid";

    /** Nombre de la columna de ID */
    public static final String ID_COLUMN_NAME = "id";

    @Override
    public Map<String, String> getRecord(HelperTable helperTable, long idRecord) {

        Map<String, String> record = new HashMap<>();
        ConnectionBD connectionBD = new ConnectionBD();

        String selectRecord = "{call semantikos.get_record(?)}";
        try (Connection connection = connectionBD.getConnection();
             CallableStatement callableStatement = connection.prepareCall(selectRecord);) {

            /* Se prepara y realiza la consulta */
            callableStatement.setLong(1, idRecord);
            ResultSet resultSet = callableStatement.executeQuery();

            /* Se recuperan los valores de las columnas de la tabla auxiliar */
            for (HelperTableColumn helperTableColumn : helperTable.getColumns()) {
                String columnName = helperTableColumn.getColumnName();
                String columnValue = resultSet.getString(columnName);

                record.put(columnName, columnValue);
            }
        } catch (SQLException e) {
            logger.error("Error al realizar una transacción sobre las tablas auxiliares", e);
        }

        return record;
    }

    @Override
    public List<Map<String, String>> findRecordsByPattern(HelperTable helperTable, String pattern) {

        List<Map<String, String>> records = new ArrayList<>();
        ConnectionBD connectionBD = new ConnectionBD();

        // TODO: Crear esta función y las tablas.
        String selectRecord = "{call semantikos.find_records_by_pattern(?,?)}";
        try (Connection connection = connectionBD.getConnection();
             CallableStatement preparedStatement = connection.prepareCall(selectRecord);) {

            /* Se prepara y realiza la consulta */
            preparedStatement.setString(1, helperTable.getTablaName());
            preparedStatement.setString(2, pattern);
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Por cada elemento del resultset se extrae un registro */
            while (resultSet.next()) {

                Map<String, String> record = new HashMap<>();
                /* Se recuperan los valores de las columnas de la tabla auxiliar */
                for (HelperTableColumn helperTableColumn : helperTable.getColumns()) {
                    String columnName = helperTableColumn.getColumnName();
                    String columnValue = resultSet.getString(columnName);

                    record.put(columnName, columnValue);
                }

                records.add(record);
            }
        } catch (SQLException e) {
            logger.error("Error al realizar una consulta sobre las tablas auxiliares", e);
        }

        return records;
    }


    @Override
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable, String[] columnNames) {

        ConnectionBD connectionBD = new ConnectionBD();
        List<HelperTableRecord> helperTableRecords = new ArrayList<>();

        String selectRecord = "{call semantikos.get_all_records_from_helper_table(?,?)}";
        try (Connection connection = connectionBD.getConnection();
             CallableStatement call = connection.prepareCall(selectRecord);) {

            String[] columnsNames = helperTable.getShowableColumnsNames().toArray(new String[helperTable.getShowableColumnsNames().size()]);
            Array columnsNamesArray = connection.createArrayOf("text", columnsNames);

            /* Se prepara y realiza la consulta */
            call.setString(1, helperTable.getTablaName());
            call.setArray(2, columnsNamesArray);
            call.execute();

            ObjectMapper mapper = new ObjectMapper();
            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                String resultJSON = rs.getString(1);
                //records = mapper.readValue(resultJSON.toUpperCase(), Object[].class);
                helperTableRecords = HelperTableRecordFactory.getInstance().createRecordsFromJSON(resultJSON);
            }
        } catch (SQLException e) {
            logger.error("Hubo un error al acceder a la base de datos.", e);
            throw new EJBException(e);
        } catch (IOException e) {
            logger.error("Hubo un error procesar los restulados con JSON.", e);
            throw new EJBException(e);
        }

        return helperTableRecords;

    }

    @Override
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable) {
        return this.getAllRecords(helperTable, helperTable.getShowableColumnsNames().toArray(new String[helperTable.getShowableColumnsNames().size()]));
    }
}
