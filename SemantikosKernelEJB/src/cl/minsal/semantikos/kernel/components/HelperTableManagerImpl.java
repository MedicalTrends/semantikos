package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.HelperTableDAO;
import cl.minsal.semantikos.model.helpertables.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cl.minsal.semantikos.model.helpertables.HelperTable.SYSTEM_COLUMN_VALIDITY_UNTIL;

/**
 * Este manager es responsable de proveer acceso a las distintas tablas auxiliares.
 * <p>
 * Las tablas auxiliares se han implementado de manera estática, es decir, se maneja una lista estática de las tablas
 * axiliares.
 * </p>
 *
 * @author Andrés Farías
 * @see cl.minsal.semantikos.kernel.components.HelperTableManagerInterface
 */
@Stateless
public class HelperTableManagerImpl implements HelperTableManagerInterface {

    private static final Logger logger = LoggerFactory.getLogger(HelperTableManagerImpl.class);

    @EJB
    private HelperTableDAO helperTableDAO;

    @Override
    public Collection<HelperTable> getHelperTables() {

        /* Esto se resuelve con delegación sobre el Factory, mientras las tablas estén en duro */
        return HelperTableFactory.getInstance().getHelperTablesByTableName();
    }

    @Override
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable, List<String> columnNames) {

        logger.debug("Se solicitan los registros de la tabla " + helperTable);

        /* A las columnas solicitadas se deben agregar las columnas de sistema: ID, DESCRIPTION, CREATION_DATE, VALID_UNTIL, y USER */
        List<String> allColumns = joinColumnsWithSystemColumns(columnNames);

        List<HelperTableRecord> allRecords = helperTableDAO.getAllRecords(helperTable, allColumns);
        logger.debug("Se recuperan " + allRecords.size() + " registros de la tabla " + helperTable);

        return allRecords;
    }

    @Override
    public List<HelperTableRecord> getValidRecords(@NotNull HelperTable helperTable, List<String> columnNames) {
        logger.debug("Se solicitan los registros vigentes de la tabla " + helperTable);

        /* A las columnas solicitadas se deben agregar las columnas de sistema: ID, DESCRIPTION, CREATION_DATE, VALID_UNTIL, y USER */
        List<String> allColumns = joinColumnsWithSystemColumns(columnNames);

        List<HelperTableWhereCondition> isValid = new ArrayList<>();
        isValid.add(new HelperTableValidityCondition(SYSTEM_COLUMN_VALIDITY_UNTIL, new java.util.Date()));
        List<HelperTableRecord> allRecords = helperTableDAO.getRecords(helperTable, allColumns, isValid);

        logger.debug("Se recuperan " + allRecords.size() + " registros vigentes de la tabla " + helperTable);

        return allRecords;
    }

    @Override
    public List<HelperTableRecord> searchValidRecords(@NotNull HelperTable helperTable, List<String> columnNames, String query) {
        logger.debug("Se solicitan los registros vigentes de la tabla " + helperTable);

        /* A las columnas solicitadas se deben agregar las columnas de sistema: ID, DESCRIPTION, CREATION_DATE, VALID_UNTIL, y USER */
        List<String> allColumns = joinColumnsWithSystemColumns(columnNames);

        List<HelperTableWhereCondition> isValid = new ArrayList<>();
        isValid.add(new HelperTableValidityCondition(SYSTEM_COLUMN_VALIDITY_UNTIL, new java.util.Date()));
        List<HelperTableRecord> allRecords = helperTableDAO.getRecords(helperTable, allColumns, isValid);

        List<HelperTableColumn> searchableColumns = new ArrayList<HelperTableColumn>();

        for ( HelperTableColumn column : helperTable.getColumns()) {

            if(column.isSearchable()){
                searchableColumns.add(column);
            }

        }
        isValid.add(new HelperTableSearchCondition(searchableColumns,query));

        logger.debug("Se recuperan " + allRecords.size() + " registros vigentes de la tabla " + helperTable);

        return allRecords;
    }

    private List<String> joinColumnsWithSystemColumns(List<String> columnNames ) {

        Collection<HelperTableColumn> systemColumns = HelperTable.getSystemColumns();
        List<String> allColumns = new ArrayList<>();
        allColumns.addAll(columnNames);
        for (HelperTableColumn systemColumn : systemColumns) {
            allColumns.add(systemColumn.getColumnName());
        }
        return allColumns;
    }

    @Override
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable) {
        return helperTableDAO.getAllRecords(helperTable);
    }
}
