package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.audit.AuditableEntity;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Una categoría puede ser el sujeto de una acción de auditoría.
 */

public class Category extends PersistentEntity implements TargetDefinition, AuditableEntity {

    /** Identificador único de la categoría */
    private long idCategory;

    private String name;
    private String nameAbreviated;
    private boolean restriction;
    private Long tagSemantikos;
    private boolean isValid;
    private String color;

    private List<RelationshipDefinition> relationshipDefinitions;

    public Category() {
        super();
    }

    @Override
    public long getId() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAbreviated() {
        return nameAbreviated;
    }

    public void setNameAbreviated(String nameAbreviated) {
        this.nameAbreviated = nameAbreviated;
    }

    public boolean isRestriction() {
        return restriction;
    }

    public void setRestriction(boolean restriction) {
        this.restriction = restriction;
    }

    public Long getTagSemantikos() {
        return tagSemantikos;
    }

    public void setTagSemantikos(Long tagSemantikos) {
        this.tagSemantikos = tagSemantikos;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<RelationshipDefinition> getRelationshipDefinitions() {
        return relationshipDefinitions;
    }

    public void setRelationshipDefinitions(List<RelationshipDefinition> relationshipDefinitions) {
        this.relationshipDefinitions = relationshipDefinitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (name != null ? !name.equals(category.name) : category.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public boolean isHasRelationshipDefinitions() {
        return !relationshipDefinitions.isEmpty();
    }

    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public boolean isSMTKType() {
        return true;
    }

    @Override
    public boolean isHelperTable() {
        return false;
    }

    @Override
    public boolean isSnomedCTType() {
        return false;
    }

    @Override
    public boolean isCrossMapType() {
        return false;
    }
}
