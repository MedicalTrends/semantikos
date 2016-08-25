package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Diego Soto
 */
public class ConceptSMTKWeb extends ConceptSMTK {

    List<RelationshipWeb> relationshipsWeb = new ArrayList<RelationshipWeb>();;

    public ConceptSMTKWeb(Category category, Description favouriteDescription, IState initialState) {
        super(category, initialState, favouriteDescription);
        for (Relationship relationship : this.getRelationships()) {
            this.addRelationshipWeb(new RelationshipWeb(relationship, false));
        }
    }

    public ConceptSMTKWeb(String conceptID, Category category, boolean isToBeReviewed, boolean isToBeConsulted, IState state, boolean isFullyDefined, boolean isPublished, Description... descriptions) {
        super(conceptID, category, isToBeReviewed, isToBeConsulted, state, isFullyDefined, isPublished, descriptions);
        for (Relationship relationship : this.getRelationships()) {
            this.addRelationshipWeb(new RelationshipWeb(relationship, false));
        }
    }

    public ConceptSMTKWeb(ConceptSMTK conceptSMTK) {
        this(conceptSMTK.getConceptID(), conceptSMTK.getCategory(), conceptSMTK.isToBeReviewed(), conceptSMTK.isToBeConsulted(), conceptSMTK.getState(),
             conceptSMTK.isFullyDefined(), conceptSMTK.isPublished(), conceptSMTK.getDescriptions().toArray(new Description[conceptSMTK.getDescriptions().size()]));
        this.setId(conceptSMTK.getId());
    }

    /**
     * Este método restorna todas ls descripciones que no son FSN o Preferidas.
     *
     * @return Una lista con todas las descripciones no FSN o Preferidas.
     */
    public List<Description> getDescriptionsButFSNandFavorite() {

        List<Description> otherDescriptions = new ArrayList<Description>();
        DescriptionType fsnType = DescriptionTypeFactory.getInstance().getFSNDescriptionType();
        DescriptionType favoriteType = DescriptionTypeFactory.getInstance().getFavoriteDescriptionType();

        for (Description description : getDescriptions()) {
            if (!description.getDescriptionType().equals(fsnType) && !description.getDescriptionType().equals(favoriteType)) {
                otherDescriptions.add(description);
            }
        }

        return otherDescriptions;
    }

    /**
     * Este método es responsable de retornar todas las relaciones de este concepto que son de un cierto tipo de
     * relación.
     *
     * @param relationshipDefinition El tipo de relación al que pertenecen las relaciones a retornar.
     *
     * @return Una <code>java.util.List</code> de relaciones de tipo <code>relationshipDefinition</code>.
     */
    public List<RelationshipWeb> getRelationshipsWebByRelationDefinition(RelationshipDefinition relationshipDefinition) {
        List<RelationshipWeb> someRelationships = new ArrayList<RelationshipWeb>();
        for (RelationshipWeb relationship : relationshipsWeb) {
            if (relationship.getRelationshipDefinition().equals(relationshipDefinition)) {
                someRelationships.add(relationship);
            }
        }
        return someRelationships;
    }

    /**
     * Este método es responsable de retornar todas las relaciones válidas de este concepto y que son de un cierto tipo
     * de
     * relación.
     *
     * @param relationshipDefinition El tipo de relación al que pertenecen las relaciones a retornar.
     *
     * @return Una <code>java.util.List</code> de relaciones de tipo <code>relationshipDefinition</code>.
     */
    public List<RelationshipWeb> getValidRelationshipsWebByRelationDefinition(RelationshipDefinition relationshipDefinition) {
        List<RelationshipWeb> someRelationships = new ArrayList<RelationshipWeb>();
        for (RelationshipWeb relationship : relationshipsWeb) {
            if (relationship.getRelationshipDefinition().equals(relationshipDefinition) &&
                    (relationship.getValidityUntil() == null || relationship.getValidityUntil().after(new Timestamp(System.currentTimeMillis())))) {
                someRelationships.add(relationship);
            }
        }
        return someRelationships;
    }

    public List<RelationshipWeb> getRelationshipsWeb() {
        return relationshipsWeb;
    }

    public void setRelationshipsWeb(List<Relationship> relationships) {
        //this.setRelationships(relationships);
        for (Relationship relationship : relationships) {
            this.addRelationshipWeb(new RelationshipWeb(relationship, false));
        }
    }

    /**
     * Este método es responsable de agregar una relación web al concepto.
     *
     * @param relationshipWeb La relación que es agregada.
     */
    public void addRelationshipWeb(RelationshipWeb relationshipWeb) {
        //super.addRelationship(relationshipWeb);
        this.getRelationshipsWeb().add(relationshipWeb);
    }

    /**
     * Este método es responsable de remover una relación web del concepto.
     *
     * @param relationshipWeb La relación que es removida.
     */
    public void removeRelationshipWeb(RelationshipWeb relationshipWeb) {
        super.getRelationships().remove(relationshipWeb);
        this.getRelationshipsWeb().remove(relationshipWeb);
    }

    @Override
    public String toString() {

        return super.toString();
    }

}
