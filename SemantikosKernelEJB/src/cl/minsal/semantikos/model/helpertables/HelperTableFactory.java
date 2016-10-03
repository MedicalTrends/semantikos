package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.kernel.daos.HelperTableDAO;
import cl.minsal.semantikos.kernel.daos.HelperTableDAOImpl;

import java.util.*;

/**
 * Esta enumeración almacena todas las tablas auxiliares existentes.
 */
public class HelperTableFactory {

    private static final HelperTableFactory singletonInstance = new HelperTableFactory();

    private HelperTableFactory() {
    }

    public static HelperTableFactory getInstance() {
        return singletonInstance;
    }

    /**
     * Este método es responsable de retornar una Tabla Auxiliar a partir de su nombre.
     * @param tableName El nombre de la tabla auxiliar que se desea recuperar.
     * @return La tabla auxiliar de nombre <code>tableName</code>.
     */
    public HelperTable getHelperTable(String tableName) {

        throw new IllegalArgumentException("No existe tabla auxiliar de nombre '" + tableName + ".");
    }
}