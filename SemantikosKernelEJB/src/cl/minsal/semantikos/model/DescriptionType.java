package cl.minsal.semantikos.model;

/**
 * @author Diego Soto.
 */
public class DescriptionType {

    /** Identificador unico */
    private long id;

    /* Nombre del tipo de descripción */
    private String name;

    /* Descripción (opcional) del tipo de descripcion */
    private String description;

    /**
     * Constructor completo para construir una entidad normal.
     *
     * @param id          Identificador único del Description Type.
     * @param name        Nombre del tipo de descripcion
     * @param description Descripción del tipo de descripcion.
     */
    public DescriptionType(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long idTipoDescripcion) {
        this.id = idTipoDescripcion;
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

    @Override
    public String toString() {
        return this.name + " - " + this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionType that = (DescriptionType) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
