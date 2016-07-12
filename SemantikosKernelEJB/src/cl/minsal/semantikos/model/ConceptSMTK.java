package cl.minsal.semantikos.model;

import cl.minsal.semantikos.kernel.DAO.implementation.CategoryDAOImpl;
import cl.minsal.semantikos.kernel.DAO.implementation.DescriptionDAOImpl;
import cl.minsal.semantikos.kernel.domain.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa al Concepto Semantikos.
 * @author Diego Soto.
 */
public class ConceptSMTK {

    /** El famoso ConceptID */
    private long id;

    /** El valor de negocio del concept_id */
    private long conceptID;

    /** La categoría del concepto */
    private Category category;

    /** Si debe ser revisado */
    private boolean isToBeReviewed;

    /** Si debe ser consultado? */
    private boolean isToBeConsultated;

    private Long idEstadoConcepto;

    /** Este campo establece si el concepto está completamente definido */
    private boolean isFullyDefined;

    /** Determina si el concepto está publicado o no */
    private boolean isPublished;

    /** Las descripciones asociadas al concepto */
    private List<Description> descriptions = new ArrayList<Description>();;

    public ConceptSMTK() {
        this.setToBeConsultated(false);
        this.setToBeReviewed(false);
    }

    public ConceptSMTK(Category category, Description fsn) {
        this();

        this.addDescription(fsn);
        this.setCategory(category);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isToBeReviewed() {
        return isToBeReviewed;
    }

    public void setToBeReviewed(boolean toBeReviewed) {
        this.isToBeReviewed = toBeReviewed;
    }

    public boolean isToBeConsultated() {
        return isToBeConsultated;
    }

    public void setToBeConsultated(boolean toBeConsultated) {
        this.isToBeConsultated = toBeConsultated;
    }

    public Long getIdEstadoConcepto() {
        return idEstadoConcepto;
    }

    public void setIdEstadoConcepto(Long idEstadoConcepto) {
        this.idEstadoConcepto = idEstadoConcepto;
    }

    public boolean isFullyDefined() {
        return isFullyDefined;
    }

    public void setFullyDefined(boolean fullyDefined) {

        // TODO: Implementar las reglas de negocio invocando al Concept Manager.
        this.isFullyDefined = fullyDefined;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        this.isPublished = published;
    }

    public void addDescription(Description description) {
        this.descriptions.add(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConceptSMTK that = (ConceptSMTK) o;

        if (id != that.id) return false;
        if (isFullyDefined != that.isFullyDefined) return false;
        if (isPublished != that.isPublished) return false;
        if (isToBeConsultated != that.isToBeConsultated) return false;
        if (isToBeReviewed != that.isToBeReviewed) return false;
        if (descriptions != null ? !descriptions.equals(that.descriptions) : that.descriptions != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (idEstadoConcepto != null ? !idEstadoConcepto.equals(that.idEstadoConcepto) : that.idEstadoConcepto != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (isToBeReviewed ? 1 : 0);
        result = 31 * result + (isToBeConsultated ? 1 : 0);
        result = 31 * result + (idEstadoConcepto != null ? idEstadoConcepto.hashCode() : 0);
        result = 31 * result + (isFullyDefined ? 1 : 0);
        result = 31 * result + (isPublished ? 1 : 0);
        result = 31 * result + (descriptions != null ? descriptions.hashCode() : 0);
        return result;
    }
}
