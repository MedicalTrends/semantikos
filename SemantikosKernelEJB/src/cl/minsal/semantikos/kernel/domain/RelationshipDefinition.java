package cl.minsal.semantikos.kernel.domain;

/**
 * Created by root on 08-07-16.
 */
public class RelationshipDefinition {
    private long idRelationshipDefinition;
    private String nombre;
    private Integer multiplicityFrom;
    private Integer multiplicityTo;

    public long getIdRelationshipDefinition() {
        return idRelationshipDefinition;
    }

    public void setIdRelationshipDefinition(long idRelationshipDefinition) {
        this.idRelationshipDefinition = idRelationshipDefinition;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMultiplicityFrom() {
        return multiplicityFrom;
    }

    public void setMultiplicityFrom(Integer multiplicityFrom) {
        this.multiplicityFrom = multiplicityFrom;
    }

    public Integer getMultiplicityTo() {
        return multiplicityTo;
    }

    public void setMultiplicityTo(Integer multiplicityTo) {
        this.multiplicityTo = multiplicityTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationshipDefinition that = (RelationshipDefinition) o;

        if (idRelationshipDefinition != that.idRelationshipDefinition) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (multiplicityFrom != null ? !multiplicityFrom.equals(that.multiplicityFrom) : that.multiplicityFrom != null)
            return false;
        if (multiplicityTo != null ? !multiplicityTo.equals(that.multiplicityTo) : that.multiplicityTo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRelationshipDefinition ^ (idRelationshipDefinition >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (multiplicityFrom != null ? multiplicityFrom.hashCode() : 0);
        result = 31 * result + (multiplicityTo != null ? multiplicityTo.hashCode() : 0);
        return result;
    }
}
