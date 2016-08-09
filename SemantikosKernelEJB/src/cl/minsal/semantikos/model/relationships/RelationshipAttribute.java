package cl.minsal.semantikos.model.relationships;

import javax.persistence.Basic;
import javax.persistence.Column;

/**
 * Created by root on 08-07-16.
 */
public class RelationshipAttribute {
    private Long idRelationshipAttribute;
    private Long idRelationAttributeDefinition;
    private Long idRelationships;
    private Long idDestiny;

    public Long getIdRelationshipAttribute() {
        return idRelationshipAttribute;
    }

    public void setIdRelationshipAttribute(Long idRelationshipAttribute) {
        this.idRelationshipAttribute = idRelationshipAttribute;
    }

    public Long getIdRelationAttributeDefinition() {
        return idRelationAttributeDefinition;
    }

    public void setIdRelationAttributeDefinition(Long idRelationAttributeDefinition) {
        this.idRelationAttributeDefinition = idRelationAttributeDefinition;
    }

    @Basic
    @Column(name = "id_relationships")
    public Long getIdRelationships() {
        return idRelationships;
    }

    public void setIdRelationships(Long idRelationships) {
        this.idRelationships = idRelationships;
    }

    @Basic
    @Column(name = "id_destiny")
    public Long getIdDestiny() {
        return idDestiny;
    }

    public void setIdDestiny(Long idDestiny) {
        this.idDestiny = idDestiny;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationshipAttribute that = (RelationshipAttribute) o;

        if (idRelationshipAttribute != null ? !idRelationshipAttribute.equals(that.idRelationshipAttribute) : that.idRelationshipAttribute != null)
            return false;
        if (idRelationAttributeDefinition != null ? !idRelationAttributeDefinition.equals(that.idRelationAttributeDefinition) : that.idRelationAttributeDefinition != null)
            return false;
        if (idRelationships != null ? !idRelationships.equals(that.idRelationships) : that.idRelationships != null)
            return false;
        if (idDestiny != null ? !idDestiny.equals(that.idDestiny) : that.idDestiny != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRelationshipAttribute != null ? idRelationshipAttribute.hashCode() : 0;
        result = 31 * result + (idRelationAttributeDefinition != null ? idRelationAttributeDefinition.hashCode() : 0);
        result = 31 * result + (idRelationships != null ? idRelationships.hashCode() : 0);
        result = 31 * result + (idDestiny != null ? idDestiny.hashCode() : 0);
        return result;
    }
}
