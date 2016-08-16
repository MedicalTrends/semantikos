package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Esta clase representa al Concepto Semantikos.
 *
 * @author Diego Soto.
 */
public class ConceptSMTK implements Target {

    /** El valor que posee un CONCEPT_ID que no ha sido definido */
    public static final String CONCEPT_ID_UNDEFINED = "-1";

    /** El famoso ConceptID */
    private long id;

    /** El valor de negocio del concept_id */
    private String conceptID = CONCEPT_ID_UNDEFINED;

    /** La categoría del concepto */
    private Category category;

    /** Si debe ser revisado */
    private boolean isToBeReviewed;

    /** Si debe ser consultado? */
    private boolean isToBeConsulted;

    /** El estado en que se encuentra el objeto */
    private State state;

    /** Este campo establece si el concepto está completamente definido */
    private boolean isFullyDefined;

    /** Determina si el concepto está publicado o no */
    private boolean isPublished;

    /** Otros descriptores */
    private List<Description> descriptions;

    /** Relaciones * */
    private List<Relationship> relationships;

    /** The concept's labels */
    private List<Label> labels;

    /**
     * El constructor privado con las inicializaciones de los campos por defecto.
     */
    public ConceptSMTK() {

        /* El concepto parte con su estado inicial */
        this.state = ConceptStateMachine.getInstance().getInitialState();

        this.descriptions = new ArrayList<>();
        this.relationships = new ArrayList<>();
        this.labels = new ArrayList<>();

        this.isFullyDefined = false;
        this.isPublished = false;
        this.isToBeConsulted = false;
        this.isToBeReviewed = false;
    }

    public ConceptSMTK(Category category) {
        this();
        this.category = category;
    }

    public ConceptSMTK(Category category, @NotNull Description... descriptions) {
        this(category);
        this.descriptions.addAll(Arrays.asList(descriptions));
    }

    public ConceptSMTK(Category category, State state, Description... descriptions) {
        this(category, descriptions);
        this.state = state;
    }

    public ConceptSMTK(long id, String conceptID, Category category, boolean isToBeReviewed, boolean isToBeConsulted, State state, boolean isFullyDefined, boolean isPublished, Description... descriptions) {
        this(category, state, descriptions);

        this.id = id;
        this.conceptID = conceptID;
        this.isToBeReviewed = isToBeReviewed;
        this.isToBeConsulted = isToBeConsulted;
        this.isFullyDefined = isFullyDefined;
        this.isPublished = isPublished;
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

    public String getConceptID() {
        return conceptID;
    }

    public void setConceptID(String conceptID) {
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

    public boolean isToBeConsulted() {
        return isToBeConsulted;
    }

    public void setToBeConsulted(boolean toBeConsulted) {
        this.isToBeConsulted = toBeConsulted;
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

    public List<Label> getLabels() {
        return labels;
    }

    /**
     * Este método es responsable de agregar una etiqueta al concepto.
     *
     * @param label La etiqueta a ser agregada.
     *
     * @return <code>true</code> si se agrega y <code>false</code> si no.
     */
    public boolean addLabel(@NotNull Label label) {
        return this.labels.add(label);
    }

    /**
     * Este método es responsable de agregar una etiqueta al concepto.
     *
     * @param description La descripción a ser agregada.
     */
    public void addDescription(Description description) {
        this.descriptions.add(description);
    }

    /**
     * Este método es responsable de agregar una etiqueta al concepto.
     *
     * @param label La etiqueta a eliminar.
     *
     * @return <code>true</code> si se elimina y <code>false</code> si no.
     */
    public boolean removeLabel(@NotNull Label label) {
        return this.labels.remove(label);
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
            if (description.getDescriptionType().getName().equalsIgnoreCase("preferido")) {
                return description;
            }
        }

        /* En este punto, no se encontró una descripción preferida, y se arroja una excepción */
        throw new BusinessRuleException("Concepto sin descripción preferida");
    }


    /**
     * <p>
     * Este método es responsable de determinar si este concepto tiene una <i>descripción preferida</i>. Basados en la
     * regla de negocio que dice que un concepto debe siempre tener una y solo una descripción preferida.</p>
     *
     * @return <code>true</code> si el concepto tiene Descripción Preferida y <code>false</code> sino.
     */
    public boolean hasFavouriteDescription() {
        for (Description description : descriptions) {
            DescriptionType favoriteDescriptionType = DescriptionTypeFactory.getInstance().getFavoriteDescriptionType();
            if (description.getDescriptionType().equals(favoriteDescriptionType)) {
                return true;
            }
        }

        /* En este punto, no se encontró una descripción preferida, y se arroja una excepción */
        return false;
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
            DescriptionType descriptionType = description.getDescriptionType();
            if (descriptionType.getName().equalsIgnoreCase("FSN")) {
                return description;
            }
        }

        /* En este punto, no se encontró una descripción preferida, y se arroja una excepción */
        throw new BusinessRuleException("Concepto sin descripción FSN");
    }

    @Override
    public String toString() {

        if (descriptions.isEmpty()) {
            return "ID=" + this.getId() + " - ConceptID=" + this.getConceptID();
        }

        if (this.hasFavouriteDescription()) {
            Description descriptionFavorite = getDescriptionFavorite();
            return "Preferida: " + descriptionFavorite.getTerm();
        }
        Description aDescription = this.descriptions.get(1);
        return aDescription.getDescriptionType().getName() + ": " + aDescription.getTerm();
    }

    /**
     * Este método es responsable de determinar si el concepto pertenece a una cierta categoría.
     *
     * @param category La categoría a la cual se desea determinar si pertenece o no.
     *
     * @return <code>true</code> si está asociado a dicha categoría y <code>false</code> sino.
     */
    public boolean belongsTo(Category category) {

        /* Si no tiene categoría, no pertenece a ninguna */
        if (this.category == null) {
            return false;
        }

        return this.category.equals(category);
    }
}
