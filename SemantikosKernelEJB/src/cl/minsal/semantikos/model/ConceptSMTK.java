package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa al Concepto Semantikos.
 *
 * @author Diego Soto.
 */
public class ConceptSMTK {

    /** El valor que posee un CONCEPT_ID que no ha sido definido */
    public static final long CONCEPT_ID_UNDEFINED = -1;

    /** El famoso ConceptID */
    private long id;

    /** El valor de negocio del concept_id */
    private long conceptID = CONCEPT_ID_UNDEFINED;

    /** La categoría del concepto */
    private Category category;

    /** Si debe ser revisado */
    private boolean isToBeReviewed;

    /** Si debe ser consultado? */
    private boolean isToBeConsultated;

    /** El estado en que se encuentra el objeto */
    private State state;

    /** Este campo establece si el concepto está completamente definido */
    private boolean isFullyDefined;

    /** Determina si el concepto está publicado o no */
    private boolean isPublished;

    /** Las descripciones asociadas al concepto */
    private List<Description> descriptions;

    public ConceptSMTK(long id, long conceptID, Category category, boolean isToBeReviewed, boolean isToBeConsultated, State state, boolean isFullyDefined, boolean isPublished, List<Description> descriptions) {
        this.id = id;
        this.conceptID = conceptID;
        this.category = category;
        this.isToBeReviewed = isToBeReviewed;
        this.isToBeConsultated = isToBeConsultated;
        this.state = state;
        this.isFullyDefined = isFullyDefined;
        this.isPublished = isPublished;
        this.descriptions = descriptions;
    }

    /**
     * El constructor privado con las inicializaciones de los campos por defecto.
     * TODO: Crear el test unitario para validar los estados iniciales por defecto.
     */
    private ConceptSMTK() {

        /* El concepto parte con su estado inicial */
        this.state = ConceptStateMachine.getInstance().getInitialState();

        this.descriptions = new ArrayList<Description>();

        this.isFullyDefined = false;
        this.isPublished = false;
        this.isToBeConsultated = false;
        this.isToBeReviewed = false;
    }

    public ConceptSMTK(Category category, Description fsn) {
        this();

        this.addDescription(fsn);
        this.setCategory(category);
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setCategory(Category category) {
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

        if (conceptID != that.conceptID) return false;
        if (id != that.id) return false;
        if (isFullyDefined != that.isFullyDefined) return false;
        if (isPublished != that.isPublished) return false;
        if (isToBeConsultated != that.isToBeConsultated) return false;
        if (isToBeReviewed != that.isToBeReviewed) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (!descriptions.equals(that.descriptions)) return false;
        if (!state.equals(that.state)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (conceptID ^ (conceptID >>> 32));
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (isToBeReviewed ? 1 : 0);
        result = 31 * result + (isToBeConsultated ? 1 : 0);
        result = 31 * result + state.hashCode();
        result = 31 * result + (isFullyDefined ? 1 : 0);
        result = 31 * result + (isPublished ? 1 : 0);
        result = 31 * result + descriptions.hashCode();
        return result;
    }


    public Description getDescriptionFavorite(){
        for (int i = 0; i < descriptions.size(); i++) {
            if(descriptions.get(i).getDescriptionType().getIdDescriptionType()==2){
                return descriptions.get(i);
            }
        }
        return null;
    }
}
