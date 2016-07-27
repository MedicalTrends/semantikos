package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrés Farías
 */
@Stateless
public class HelperTableDAOImpl implements HelperTableDAO {

    private static final Logger logger = LoggerFactory.getLogger(HelperTableDAOImpl.class);

    @Override
    public Map<String, String> getRecord(HelperTable helperTable, long idRecord) {

        Map<String, String> record = new HashMap<>();
        ConnectionBD connectionBD = new ConnectionBD();
        String selectRecord = "SELECT * FROM " + helperTable.getTablaName() + " WHERE ID=?";
        try (Connection connection = connectionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectRecord);) {

            /* Se prepara y realiza la consulta */
            preparedStatement.setLong(1, idRecord);
            ResultSet resultSet = preparedStatement.executeQuery();

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

                Map<String, String> record = new HashMap<String, String>();
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
    public List<Map<String, String>> getAllRecords(HelperTable helperTable, String[] columnNames) {
        List<Map<String, String>> records = new ArrayList<>();
        ConnectionBD connectionBD = new ConnectionBD();

        // TODO: Crear esta función y las tablas.
        String selectRecord = "{call semantikos.get_all_records_from_helper_table(?)}";
        try (Connection connection = connectionBD.getConnection();
             CallableStatement preparedStatement = connection.prepareCall(selectRecord);) {

            /* Se prepara y realiza la consulta */
            preparedStatement.setString(1, helperTable.getTablaName());
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Por cada elemento del resultset se extrae un registro */
            while (resultSet.next()) {

                Map<String, String> record = new HashMap<String, String>();

                /* Se recuperan los valores de las columnas de la tabla auxiliar indicados */
                for (String columnName : columnNames) {
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
    public List<Map<String, String>> getAllRecords(HelperTable helperTable) {
        List<Map<String, String>> records = new ArrayList<>();
        ConnectionBD connectionBD = new ConnectionBD();

        // TODO: Crear esta función y las tablas.
        String selectRecord = "{call semantikos.get_all_records_from_helper_table(?)}";
        try (Connection connection = connectionBD.getConnection();
             CallableStatement preparedStatement = connection.prepareCall(selectRecord);) {

            /* Se prepara y realiza la consulta */
            preparedStatement.setString(1, helperTable.getTablaName());
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Por cada elemento del resultset se extrae un registro */
            while (resultSet.next()) {

                Map<String, String> record = new HashMap<String, String>();

                /* Se recuperan los valores de las columnas de la tabla auxiliar indicados */
                for (HelperTableColumn column : helperTable.getShowableColumns()) {
                    String columnName = column.getColumnName();
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

}
