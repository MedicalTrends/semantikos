package cl.minsal.semantikos.model.relationships;

/**
 * Created by root on 08-07-16.
 */
public class RelationshipAttributeDefinition {

    private long idRelationAttributeDefinition;
    private Long idRelationshipDefinition;
    private Long idTargetDefinition;
    private String nombre;
    private String multiplicityFrom;
    private String multiplicityTo;

    public long getIdRelationAttributeDefinition() {
        return idRelationAttributeDefinition;
    }

    public void setIdRelationAttributeDefinition(long idRelationAttributeDefinition) {
        this.idRelationAttributeDefinition = idRelationAttributeDefinition;
    }

    public Long getIdRelationshipDefinition() {
        return idRelationshipDefinition;
    }

    public void setIdRelationshipDefinition(Long idRelationshipDefinition) {
        this.idRelationshipDefinition = idRelationshipDefinition;
    }

    public Long getIdTargetDefinition() {
        return idTargetDefinition;
    }

    public void setIdTargetDefinition(Long idTargetDefinition) {
        this.idTargetDefinition = idTargetDefinition;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMultiplicityFrom() {
        return multiplicityFrom;
    }

    public void setMultiplicityFrom(String multiplicityFrom) {
        this.multiplicityFrom = multiplicityFrom;
    }

    public String getMultiplicityTo() {
        return multiplicityTo;
    }

    public void setMultiplicityTo(String multiplicityTo) {
        this.multiplicityTo = multiplicityTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationshipAttributeDefinition that = (RelationshipAttributeDefinition) o;

        if (idRelationAttributeDefinition != that.idRelationAttributeDefinition) return false;
        if (idRelationshipDefinition != null ? !idRelationshipDefinition.equals(that.idRelationshipDefinition) : that.idRelationshipDefinition != null)
            return false;
        if (idTargetDefinition != null ? !idTargetDefinition.equals(that.idTargetDefinition) : that.idTargetDefinition != null)
            return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (multiplicityFrom != null ? !multiplicityFrom.equals(that.multiplicityFrom) : that.multiplicityFrom != null)
            return false;
        if (multiplicityTo != null ? !multiplicityTo.equals(that.multiplicityTo) : that.multiplicityTo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRelationAttributeDefinition ^ (idRelationAttributeDefinition >>> 32));
        result = 31 * result + (idRelationshipDefinition != null ? idRelationshipDefinition.hashCode() : 0);
        result = 31 * result + (idTargetDefinition != null ? idTargetDefinition.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (multiplicityFrom != null ? multiplicityFrom.hashCode() : 0);
        result = 31 * result + (multiplicityTo != null ? multiplicityTo.hashCode() : 0);
        return result;
    }
}
