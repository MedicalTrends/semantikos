package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.HelperTableDAO;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableFactory;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collection;
import java.util.List;

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

    @EJB
    private HelperTableDAO helperTableDAO;

    @Override
    public Collection<HelperTable> getHelperTables() {

        /* Esto se resuelve con delegación sobre el Factory, mientras las tablas estén en duro */
        return HelperTableFactory.getInstance().getHelperTables();
    }

    @Override
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable, String[] columnNames) {
        return helperTableDAO.getAllRecords(helperTable, columnNames);
    }

    @Override
    public List<HelperTableRecord> getAllRecords(HelperTable helperTable) {
        return helperTableDAO.getAllRecords(helperTable);
    }
}
