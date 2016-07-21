package cl.minsal.semantikos.model;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 08-07-16.
 */
public class BasicTypeDefinition {

    private long id;

    private String name;

    /** Descripcion del tipo: "¿Es pedible?" */
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
