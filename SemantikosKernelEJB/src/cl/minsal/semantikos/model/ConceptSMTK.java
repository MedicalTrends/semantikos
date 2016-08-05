package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa al Concepto Semantikos.
 *
 * @author Diego Soto.
 */
public class ConceptSMTK implements Target {

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

    /** Otros descriptores */
    private List<Description> descriptions = new ArrayList<Description>();

    /** Relaciones * */
    private List<Relationship> relationships = new ArrayList<>();

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
    public ConceptSMTK() {

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

        fsn.setId(1);
        fsn.setTerm(fsn.getTerm() + "(" + category.getName() + ")");
        fsn.setId(2);
        this.setCategory(category);
    }

    public ConceptSMTK(Category category, Description fsn, Description preferido) {
        this();

        this.setCategory(category);
    }

    public ConceptSMTK(Category category, Description fsn, Description preferido, State state) {
        this();

        this.setCategory(category);
        this.setState(state);
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    /**
     * Este método es responsable de retornar todas las relaciones de este concepto que son de un cierto tipo de
     * relación.
     *
     * @param relationshipDefinition El tipo de relación al que pertenecen las relaciones a retornar.
     *
     * @return Una <code>java.util.List</code> de relaciones de tipo <code>relationshipDefinition</code>.
     */
    public List<Relationship> getRelationshipsByRelationDefinition(RelationshipDefinition relationshipDefinition) {
        List<Relationship> someRelationships = new ArrayList<>();
        for (Relationship relationship : relationships) {
            if (relationship.getRelationshipDefinition().equals(relationshipDefinition)) {
                someRelationships.add(relationship);
            }
        }

        return someRelationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
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

        description.setId(descriptions.size() + 1);
        this.descriptions.add(description);

    }

    public void removeDescription(Description description) {
        this.descriptions.remove(description);
    }

    public void addRelationship(Relationship relationship) {

        this.relationships.add(relationship);

    }

    /**
     * <p>
     * Este método es responsable de retornar la <i>descripción preferida</i>. Basados en la regla de negocio que dice
     * que un concepto debe siempre tener una y solo una descripción preferida.</p>
     * <p>
     * Si el concepto no tuviera descripción preferida, se retorna una descripción "sin tipo".
     * </p>
     *
     * @return La descripción preferida.
     */
    public Description getDescriptionFavorite() {
        for (Description description : descriptions) {
            if (description.getDescriptionType().getDescription().equalsIgnoreCase("Preferida")) {
                return description;
            }
        }

        /* En este punto, no se encontró una descripción preferida, y se arroja una excepción */
        throw new BusinessRuleException("Concepto sin descripción preferida");
    }

    /**
     * <p>
     * Este método es responsable de retornar la <i>descripción FSN</i>. Basados en la regla de negocio que dice
     * que un concepto debe siempre tener una y solo una descripción FSN.</p>
     * <p>
     * Si el concepto no tuviera descripción preferida, se retorna una descripción "sin tipo".
     * </p>
     *
     * @return La descripción preferida.
     */
    public Description getDescriptionFSN() {
        for (Description description : descriptions) {
            if (description.getDescriptionType().getDescription().equalsIgnoreCase("FSN")) {
                return description;
            }
        }

        /* En este punto, no se encontró una descripción preferida, y se arroja una excepción */
        throw new BusinessRuleException("Concepto sin descripción FSN");
    }

    @Override
    public String toString() {

        if (getDescriptionFavorite() == null) {
            return null;
        }
        return getDescriptionFavorite().getTerm();
    }
}
