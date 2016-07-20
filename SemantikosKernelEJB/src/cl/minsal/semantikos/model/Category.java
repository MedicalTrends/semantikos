package cl.minsal.semantikos.model;

import java.util.List;

/**
 * Created by root on 08-07-16.
 */

public class Category {
    private long idCategory;
    private String name;
    private String nameAbreviated;
    private boolean restriction;
    private Long tagSemantikos;
    private boolean isValid;
    private Integer color;

    private List<RelationshipDefinition> relationshipDefinitions;

    public long getIdCategory() {
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
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

        Category that = (Category) o;

        if (idCategory != that.idCategory) return false;
        if (restriction != that.restriction) return false;
        if (isValid != that.isValid) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameAbreviated != null ? !nameAbreviated.equals(that.nameAbreviated) : that.nameAbreviated != null)
            return false;
        if (tagSemantikos != null ? !tagSemantikos.equals(that.tagSemantikos) : that.tagSemantikos != null)
            return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idCategory ^ (idCategory >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nameAbreviated != null ? nameAbreviated.hashCode() : 0);
        result = 31 * result + (restriction ? 1 : 0);
        result = 31 * result + (tagSemantikos != null ? tagSemantikos.hashCode() : 0);
        result = 31 * result + (isValid ? 1 : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

}
