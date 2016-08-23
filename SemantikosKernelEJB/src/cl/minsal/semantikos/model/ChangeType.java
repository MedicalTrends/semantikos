package cl.minsal.semantikos.model;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class ChangeType {
    private int id;
    private String name;

    public ChangeType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
