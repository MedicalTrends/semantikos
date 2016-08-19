package cl.minsal.semantikos.model.relationships;

/**
 * Created by root on 08-07-16.
 */
public class RelationshipAttributeDefinition {

    private long idRelationAttributeDefinition;
    private Long idRelationshipDefinition;
    private Long idTargetDefinition;
    private String name;
    private String lowerBoundary;
    private String upperBoundary;

    //TODO: terminar funcion postgresql y atributos del objeto

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLowerBoundary() {
        return lowerBoundary;
    }

    public void setLowerBoundary(String lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }

    public String getUpperBoundary() {
        return upperBoundary;
    }

    public void setUpperBoundary(String upperBoundary) {
        this.upperBoundary = upperBoundary;
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
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lowerBoundary != null ? !lowerBoundary.equals(that.lowerBoundary) : that.lowerBoundary != null)
            return false;
        if (upperBoundary != null ? !upperBoundary.equals(that.upperBoundary) : that.upperBoundary != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRelationAttributeDefinition ^ (idRelationAttributeDefinition >>> 32));
        result = 31 * result + (idRelationshipDefinition != null ? idRelationshipDefinition.hashCode() : 0);
        result = 31 * result + (idTargetDefinition != null ? idTargetDefinition.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lowerBoundary != null ? lowerBoundary.hashCode() : 0);
        result = 31 * result + (upperBoundary != null ? upperBoundary.hashCode() : 0);
        return result;
    }
}
