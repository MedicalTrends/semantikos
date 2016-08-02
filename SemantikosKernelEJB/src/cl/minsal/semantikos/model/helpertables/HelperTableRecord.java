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
    private long id;

    public HelperTableRecord() {
    }

    /**
     * El constructor básico que requiere ambos campos.
     *
     * @param helperTable La tabla auxiliar en cuestión.
     * @param id La llave primaria del registro de la tabla auxiliar.
     */
    public HelperTableRecord(HelperTable helperTable, long id) {
        this.helperTable = helperTable;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
