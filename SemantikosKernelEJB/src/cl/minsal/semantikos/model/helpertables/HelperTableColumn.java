package cl.minsal.semantikos.model.helpertables;

/**
 * @author Andres Farías on 7/27/16.
 */
public class HelperTableColumn {

    private String name;
    private String type;
    private HelperTable foreignKeyTarget;

    /**
     * This is the default and only constructor for HelperTableColumn.
     *
     * @param name The column  name.
     * @param type The column type (int,float,string,date,fk).
     */
    public HelperTableColumn(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public HelperTableColumn(String name, String type, HelperTable foreignKeyTarget) {
        this.name = name;
        this.type = type;
        this.foreignKeyTarget = foreignKeyTarget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HelperTable getForeignKeyTarget() {
        return foreignKeyTarget;
    }

    public void setForeignKeyTarget(HelperTable foreignKeyTarget) {
        this.foreignKeyTarget = foreignKeyTarget;
    }
}
