package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import java.util.List;

/**
 * Created by root on 08-07-16.
 */
public class TargetDefinition {
    private long idTargetDefinition;
    private String descripcion;
    private Long idCategory;
    private Long idAccesoryTableName;
    private Long idExternTableName;
    private Long idTargetType;
    private Long idBasicType;

    private List<CategoryRelationship> categoryRelationships;

    private TargetType targetType;

    private BasicTypeDefinition basicTypeDefinition;

    public long getIdTargetDefinition() {
        return idTargetDefinition;
    }

    public void setIdTargetDefinition(long idTargetDefinition) {
        this.idTargetDefinition = idTargetDefinition;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdAccesoryTableName() {
        return idAccesoryTableName;
    }

    public void setIdAccesoryTableName(Long idAccesoryTableName) {
        this.idAccesoryTableName = idAccesoryTableName;
    }

    public Long getIdExternTableName() {
        return idExternTableName;
    }

    public void setIdExternTableName(Long idExternTableName) {
        this.idExternTableName = idExternTableName;
    }

    public Long getIdTargetType() {
        return idTargetType;
    }

    public void setIdTargetType(Long idTargetType) {
        this.idTargetType = idTargetType;
    }

    public Long getIdBasicType() {
        return idBasicType;
    }

    public void setIdBasicType(Long idBasicType) {
        this.idBasicType = idBasicType;
    }

    public List<CategoryRelationship> getCategoryRelationships() {
        return categoryRelationships;
    }

    public void setCategoryRelationships(List<CategoryRelationship> categoryRelationships) {
        this.categoryRelationships = categoryRelationships;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public BasicTypeDefinition getBasicTypeDefinition() {
        return basicTypeDefinition;
    }

    public void setBasicTypeDefinition(BasicTypeDefinition basicTypeDefinition) {
        this.basicTypeDefinition = basicTypeDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetDefinition that = (TargetDefinition) o;

        if (idTargetDefinition != that.idTargetDefinition) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (idCategory != null ? !idCategory.equals(that.idCategory) : that.idCategory != null) return false;
        if (idAccesoryTableName != null ? !idAccesoryTableName.equals(that.idAccesoryTableName) : that.idAccesoryTableName != null)
            return false;
        if (idExternTableName != null ? !idExternTableName.equals(that.idExternTableName) : that.idExternTableName != null)
            return false;
        if (idTargetType != null ? !idTargetType.equals(that.idTargetType) : that.idTargetType != null) return false;
        if (idBasicType != null ? !idBasicType.equals(that.idBasicType) : that.idBasicType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTargetDefinition ^ (idTargetDefinition >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (idCategory != null ? idCategory.hashCode() : 0);
        result = 31 * result + (idAccesoryTableName != null ? idAccesoryTableName.hashCode() : 0);
        result = 31 * result + (idExternTableName != null ? idExternTableName.hashCode() : 0);
        result = 31 * result + (idTargetType != null ? idTargetType.hashCode() : 0);
        result = 31 * result + (idBasicType != null ? idBasicType.hashCode() : 0);
        return result;
    }
}
