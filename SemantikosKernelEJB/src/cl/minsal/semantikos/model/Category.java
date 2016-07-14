package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.CategoryRelationship;

import java.util.List;

/**
 * Created by root on 08-07-16.
 */

public class Category {
    private long idCategory;
    private String nombre;
    private String nombreAbreviado;
    private boolean restriccion;
    private Long tagSemantikos;
    private boolean vigente;
    private Integer color;

    private List<CategoryRelationship> categoryRelationships;

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreAbreviado() {
        return nombreAbreviado;
    }

    public void setNombreAbreviado(String nombreAbreviado) {
        this.nombreAbreviado = nombreAbreviado;
    }

    public boolean isRestriccion() {
        return restriccion;
    }

    public void setRestriccion(boolean restriccion) {
        this.restriccion = restriccion;
    }

    public Long getTagSemantikos() {
        return tagSemantikos;
    }

    public void setTagSemantikos(Long tagSemantikos) {
        this.tagSemantikos = tagSemantikos;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public List<CategoryRelationship> getCategoryRelationships() {
        return categoryRelationships;
    }

    public void setCategoryRelationships(List<CategoryRelationship> categoryRelationships) {
        this.categoryRelationships = categoryRelationships;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category that = (Category) o;

        if (idCategory != that.idCategory) return false;
        if (restriccion != that.restriccion) return false;
        if (vigente != that.vigente) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (nombreAbreviado != null ? !nombreAbreviado.equals(that.nombreAbreviado) : that.nombreAbreviado != null)
            return false;
        if (tagSemantikos != null ? !tagSemantikos.equals(that.tagSemantikos) : that.tagSemantikos != null)
            return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idCategory ^ (idCategory >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (nombreAbreviado != null ? nombreAbreviado.hashCode() : 0);
        result = 31 * result + (restriccion ? 1 : 0);
        result = 31 * result + (tagSemantikos != null ? tagSemantikos.hashCode() : 0);
        result = 31 * result + (vigente ? 1 : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

}
