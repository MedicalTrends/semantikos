package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.relationships.Target;

/**
 * Created by andres on 7/27/16.
 */
public class HelperTableRecord implements Target {

    /** La tabla auxiliar referenciada */
    private HelperTable helperTable;

    /** La llave primaria del registro */
    private long idRecord;

    /**
     * El constructor básico que requiere ambos campos.
     *
     * @param helperTable La tabla auxiliar en cuestión.
     * @param idRecord    La llave primaria del registro de la tabla auxiliar.
     */
    public HelperTableRecord(HelperTable helperTable, long idRecord) {
        this.helperTable = helperTable;
        this.idRecord = idRecord;
    }

}
