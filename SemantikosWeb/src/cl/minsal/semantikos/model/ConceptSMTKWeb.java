package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Diego Soto
 */
public class ConceptSMTKWeb extends ConceptSMTK {

    //Atributos básicos del concepto que son pasados a la vista

    //Descripciones que son pasadas a la vista
    List<DescriptionWeb> descriptionsWeb = new ArrayList<DescriptionWeb>();
    //Relaciones que son pasadas a la vista
    List<RelationshipWeb> relationshipsWeb = new ArrayList<RelationshipWeb>();


    //Este es el constructor mínimo
    public ConceptSMTKWeb(ConceptSMTK conceptSMTK) {

        // Crear un nuevo concepto con su información básica
        super(conceptSMTK.getConceptID(), conceptSMTK.getCategory(), conceptSMTK.isToBeReviewed(), conceptSMTK.isToBeConsulted(), conceptSMTK.isModeled(),
                conceptSMTK.isFullyDefined(), conceptSMTK.isPublished(), conceptSMTK.getObservation(), conceptSMTK.getTagSMTK());

        // Agregar descripciones y relaciones
        if(conceptSMTK.isPersistent()){
            this.setId(conceptSMTK.getId());
            // Si el concepto esta persistido clonar las descripciones con su id
            for (Description description : conceptSMTK.getValidDescriptions())
                addDescriptionWeb(new DescriptionWeb(description.getId(), description));
            // Si el concepto esta persistido clonar las relaciones con su id
            for (Relationship relationship : conceptSMTK.getValidRelationships())
                addRelationshipWeb(new RelationshipWeb(relationship.getId(), relationship));
            for (Tag tag: conceptSMTK.getTags()) {
                addTag(tag);
            }
        }
        else{
            // Si el concepto no esta persistido clonar las descripciones sin su id
            for (Description description : conceptSMTK.getValidDescriptions()) {
                addDescriptionWeb(new DescriptionWeb(description));
            }
        }
    }

    public ConceptSMTKWeb init(Category category, String term){

        /* Valores iniciales para el concepto */
        Description fsnDescription = new Description(this, term, DescriptionTypeFactory.getInstance().getFSNDescriptionType());
        fsnDescription.setCaseSensitive(false);
        //fsn.setDescriptionId(descriptionManager.generateDescriptionId());

        Description favouriteDescription = new Description(this, term, DescriptionTypeFactory.getInstance().getFavoriteDescriptionType());
        favouriteDescription.setCaseSensitive(false);
        //favouriteDescription.setDescriptionId(descriptionManager.generateDescriptionId());

        Description[] descriptions = {favouriteDescription, fsnDescription};

        String observation = "";

        // TODO: Diego
        TagSMTK tagSMTK = new TagSMTK(category.getTagSemantikos().getId(), category.getTagSemantikos().getName());

        ConceptSMTK conceptSMTK = new ConceptSMTK(null, category, true, true, false, false, false, observation, tagSMTK, descriptions);

        return new ConceptSMTKWeb(conceptSMTK);

    }

    public List<DescriptionWeb> getDescriptionsWeb() {
        return descriptionsWeb;
    }

    public List<RelationshipWeb> getRelationshipsWeb() {
        return relationshipsWeb;
    }

    public void setDescriptionsWeb(List<DescriptionWeb> descriptionsWeb) {
        this.descriptionsWeb = descriptionsWeb;
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
    public DescriptionWeb getValidDescriptionFSN() {
        for (DescriptionWeb descriptionWeb : getDescriptionsWeb()) {
            DescriptionType descriptionType = descriptionWeb.getDescriptionType();
            if (descriptionType.getName().equalsIgnoreCase("FSN") && descriptionWeb.isValid()) {
                return descriptionWeb;
            }
        }
        /* En este punto, no se encontró una descripción preferida, y se arroja una excepción */
        throw new BusinessRuleException("Concepto sin descripción FSN");
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
    public DescriptionWeb getValidDescriptionFavorite() {
        for (DescriptionWeb descriptionWeb : getDescriptionsWeb()) {
            if (descriptionWeb.getDescriptionType().getName().equalsIgnoreCase("preferida") && descriptionWeb.isValid()) {
                return descriptionWeb;
            }
        }

        /* En este punto, no se encontró una descripción preferida, y se arroja una excepción */
        throw new BusinessRuleException("Concepto sin descripción preferida");
    }

    /**
     * Este método restorna todas ls descripciones que no son FSN o Preferidas.
     *
     * @return Una lista con todas las descripciones no FSN o Preferidas.
     */
    public List<DescriptionWeb> getValidDescriptionsButFSNandFavorite() {

        List<DescriptionWeb> otherDescriptions = new ArrayList<DescriptionWeb>();
        DescriptionType fsnType = DescriptionTypeFactory.getInstance().getFSNDescriptionType();
        DescriptionType favoriteType = DescriptionTypeFactory.getInstance().getFavoriteDescriptionType();

        for (DescriptionWeb descriptionWeb : getDescriptionsWeb()) {
            if (!descriptionWeb.getDescriptionType().equals(fsnType) && !descriptionWeb.getDescriptionType().equals(favoriteType) && descriptionWeb.isValid()) {
                otherDescriptions.add(descriptionWeb);
            }
        }

        return otherDescriptions;
    }


    /**
     * Este metodo revisa que las relaciones cumplan el lower_boundary del
     * relationship definition, en caso de no cumplir la condicion se retorna falso.
     *
     * @return
     */
    public boolean isValid() {

        for (RelationshipDefinition relationshipDef : this.getCategory().getRelationshipDefinitions()) {

            List<Relationship> relationships = this.getValidRelationshipsByRelationDefinition(relationshipDef);

            if (relationships.size() < relationshipDef.getMultiplicity().getLowerBoundary())
                return false;
            /*
            for (Relationship relationship : relationships) {
                if(relationship.getTarget().)
            }
            */
        }

        return true;
    }

    /**
     * Este método determina si existen instancias de relaciones asociadas a una definición de relación
     *
     * @param relationshipDefinition El tipo de relación al que pertenecen las relaciones a retornar.
     *
     * @return Un <code>java.lang.boolean</code>
     */
    public boolean hasRelationships(RelationshipDefinition relationshipDefinition) {
        return !getRelationshipsByRelationDefinition(relationshipDefinition).isEmpty();
    }

    @Override
    public String toString() {

        return super.toString();
    }

    public String validateDescription(Description description) {
        if (description.getDescriptionType().getName().equalsIgnoreCase("FSN")) {
            if (description == null || description.getTerm().length() == 0) {
                return "Falta el FSN al concepto";
            }
        }
        return "";
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
            if (relationship.getRelationshipDefinition().equals(relationshipDefinition) && relationship.isValid()) {
                someRelationships.add(relationship);
            }
        }
        return someRelationships;
    }

    public void setRelationshipsWeb(List<Relationship> relationships) {
        super.setRelationships(relationships);
        for (Relationship relationship : this.getValidRelationships())
            this.relationshipsWeb.add(new RelationshipWeb(relationship.getId(), relationship));
    }

    /**
     * Este método es responsable de agregar una relación web al concepto.
     *
     * @param relationship La relación que es agregada.
     */
    public void addRelationshipWeb(Relationship relationship) {
        super.addRelationship(relationship);
        if(relationship.isPersistent())
            this.relationshipsWeb.add(new RelationshipWeb(relationship.getId(), relationship));
        else
            this.relationshipsWeb.add(new RelationshipWeb(relationship));
    }

    /**
     * Este método es responsable de agregar un tag al concepto.
     *
     * @param tag El tag que es agregado.
     */
    public void addTag(Tag tag) {
        super.addTag(tag);
    }


    /**
     * Este método es responsable de remover una descripción a un concepto.
     *
     * @param description La descripción que es removida.
     */
    public void removeDescriptionWeb(Description description) {
        this.getDescriptions().remove(description);
        this.descriptionsWeb.remove(new DescriptionWeb(description.getId(), description));
    }


    public void addDescriptionWeb(DescriptionWeb descriptionWeb) {
        this.addDescription(descriptionWeb);
        this.descriptionsWeb.add(descriptionWeb);
    }

    /**
     * Este método es responsable de remover una relación a un concepto.
     *
     * @param relationship La relación que es removida.
     */
    public void removeRelationshipWeb(Relationship relationship) {
        this.getRelationships().remove(relationship);
        this.relationshipsWeb.remove(new RelationshipWeb(relationship.getId(), relationship));
    }



}