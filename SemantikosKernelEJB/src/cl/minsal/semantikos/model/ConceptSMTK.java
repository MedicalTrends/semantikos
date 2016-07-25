package cl.minsal.semantikos.model;

import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

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

    /** Descriptor fsn **/
    private Description fsn;

    /** Descriptor preferido **/
    private Description preferido;

    /** Otros descriptores */
    private List<Description> otherDescriptions = new ArrayList<Description>();

    public ConceptSMTK(long id, long conceptID, Category category, boolean isToBeReviewed, boolean isToBeConsultated, State state, boolean isFullyDefined, boolean isPublished, List<Description> otherDescriptions) {
        this.id = id;
        this.conceptID = conceptID;
        this.category = category;
        this.isToBeReviewed = isToBeReviewed;
        this.isToBeConsultated = isToBeConsultated;
        this.state = state;
        this.isFullyDefined = isFullyDefined;
        this.isPublished = isPublished;
        this.otherDescriptions = otherDescriptions;
    }

    /**
     * El constructor privado con las inicializaciones de los campos por defecto.
     * TODO: Crear el test unitario para validar los estados iniciales por defecto.
     */
    private ConceptSMTK() {

        /* El concepto parte con su estado inicial */
        this.state = ConceptStateMachine.getInstance().getInitialState();

        this.otherDescriptions = new ArrayList<Description>();

        this.isFullyDefined = false;
        this.isPublished = false;
        this.isToBeConsultated = false;
        this.isToBeReviewed = false;
    }

    public ConceptSMTK(Category category, Description fsn) {
        this();

        fsn.setId(1);
        this.setFsn(fsn);
        fsn.setTerm(fsn.getTerm()+"("+category.getName()+")");
        fsn.setId(2);
        this.setPreferido(fsn);
        this.setCategory(category);
    }

    public ConceptSMTK(Category category, Description fsn, Description preferido) {
        this();

        this.setFsn(fsn);
        this.setPreferido(preferido);
        this.setCategory(category);
    }

    public ConceptSMTK(Category category, Description fsn, Description preferido, State state) {
        this();

        this.setFsn(fsn);
        this.setPreferido(preferido);
        this.setCategory(category);
        this.setState(state);
    }

    public Description getFsn() {
        return fsn;
    }

    public void setFsn(Description fsn) {
        this.fsn = fsn;
    }

    public Description getPreferido() {
        return preferido;
    }

    public void setPreferido(Description preferido) {
        this.preferido = preferido;
    }

    public List<Description> getOtherDescriptions() {
        return otherDescriptions;
    }

    public void setOtherDescriptions(List<Description> otherDescriptions) {
        this.otherDescriptions = otherDescriptions;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConceptID() {
        return conceptID;
    }

    public void setConceptID(long conceptID) {
        this.conceptID = conceptID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

        description.setId(otherDescriptions.size()+1);
        this.otherDescriptions.add(description);

    }

    public void removeDescription(Description description) { this.otherDescriptions.remove(description); }

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
        if (!otherDescriptions.equals(that.otherDescriptions)) return false;
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
        result = 31 * result + otherDescriptions.hashCode();
        return result;
    }


    public Description getDescriptionFavorite(){
        for (int i = 0; i < otherDescriptions.size(); i++) {
            if(otherDescriptions.get(i).getDescriptionType().getIdDescriptionType()==2){
                return otherDescriptions.get(i);
            }
        }
        return null;
    }

    public int countDescription(){
        return otherDescriptions.size();
    }
}
