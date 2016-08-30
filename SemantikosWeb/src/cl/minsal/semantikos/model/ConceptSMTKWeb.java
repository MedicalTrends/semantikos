package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.util.Pair;

import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Diego Soto
 */
public class ConceptSMTKWeb extends ConceptSMTK {

    List<RelationshipWeb> relationshipsWeb = new ArrayList<RelationshipWeb>();
    List<DescriptionWeb> descriptionsWeb = new ArrayList<DescriptionWeb>();

    public ConceptSMTKWeb(String conceptID, Category category, boolean isToBeReviewed, boolean isToBeConsulted, IState state, boolean isFullyDefined, boolean isPublished, Description... descriptions) {
        super(conceptID, category, isToBeReviewed, isToBeConsulted, state, isFullyDefined, isPublished, descriptions);
        for (Relationship relationship : this.getRelationships()) {
            if(relationship.isPersisted())
                this.addRelationshipWeb(new RelationshipWeb(relationship.getId(), relationship, false));
            else
                this.addRelationshipWeb(new RelationshipWeb(relationship, false));
        }
        for (Description description : this.getDescriptions()) {
            if(description.isPersisted())
                this.addDescriptionWeb(new DescriptionWeb(description.getId(), description, false));
            else
                this.addDescriptionWeb(new DescriptionWeb(description, false));
        }
    }

    public ConceptSMTKWeb(ConceptSMTK conceptSMTK) {
        this(conceptSMTK.getConceptID(), conceptSMTK.getCategory(), conceptSMTK.isToBeReviewed(), conceptSMTK.isToBeConsulted(), conceptSMTK.getState(),
             conceptSMTK.isFullyDefined(), conceptSMTK.isPublished(), conceptSMTK.getDescriptions().toArray(new Description[conceptSMTK.getDescriptions().size()]));
        this.setId(conceptSMTK.getId());
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
    public Description getValidDescriptionFSN() {
        for (DescriptionWeb description : descriptionsWeb) {
            DescriptionType descriptionType = description.getDescriptionType();
            if (descriptionType.getName().equalsIgnoreCase("FSN")) {
                return description;
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
    public Description getValidDescriptionFavorite() {
        for (DescriptionWeb description : descriptionsWeb) {
            if (description.getDescriptionType().getName().equalsIgnoreCase("preferida")) {
                return description;
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

        for ( DescriptionWeb description : descriptionsWeb) {
            if (!description.getDescriptionType().equals(fsnType) && !description.getDescriptionType().equals(favoriteType)) {
                otherDescriptions.add(description);
            }
        }

        return otherDescriptions;
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
            if(relationship.isPersisted())
                this.addRelationshipWeb(new RelationshipWeb(relationship.getId(), relationship, false));
            else
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


    public void addDescriptionWeb(DescriptionWeb descriptionWeb){

        this.descriptionsWeb.add(descriptionWeb);
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

    public boolean prepareForUpdate(){
        getRelationships().clear();
        Relationship relationship;
        for (RelationshipWeb rWeb : relationshipsWeb) {
            if(rWeb.isPersisted()) {
                relationship = new Relationship(rWeb.getId(), rWeb.getSourceConcept(), rWeb.getTarget(), rWeb.getRelationshipDefinition(), rWeb.getValidityUntil());
                relationship.setToBeUpdated(rWeb.isToBeUpdated());
                this.addRelationship(relationship);
            }
            else {
                relationship = new Relationship(rWeb.getSourceConcept(), rWeb.getTarget(), rWeb.getRelationshipDefinition());
                relationship.setToBeUpdated(rWeb.isToBeUpdated());
                this.addRelationship(relationship);
            }
        }
        return true;
    }

    public List<Pair<DescriptionWeb, DescriptionWeb>> getDescriptionsForUpdate(){

        List<Pair<DescriptionWeb, DescriptionWeb>> descriptionsForUpdate= new ArrayList<Pair<DescriptionWeb, DescriptionWeb>>();

        //Primero se buscan todas las descripciones persistidas originales y que han sufrido modificaciones
        for (DescriptionWeb oldDescriptionWeb : descriptionsWeb) {
            if(oldDescriptionWeb.hasBeenModified()) {
                //Por cada descripción original se busca su descripcion modificada correlacionada
                for(DescriptionWeb newDescriptionWeb: descriptionsWeb){
                    if(!newDescriptionWeb.hasBeenModified() && newDescriptionWeb.getId()==oldDescriptionWeb.getId()){
                        descriptionsForUpdate.add(new Pair(oldDescriptionWeb, newDescriptionWeb));
                    }
                }
            }
        }
        return descriptionsForUpdate;
    }

    /**
     * Este metodo revisa que las relaciones cumplan el lower_boundary del
     * relationship definition, en caso de no cumplir la condicion se retorna falso.
     *
     * @return
     */
    public boolean isValid() {

        for (RelationshipDefinition relationshipDef : this.getCategory().getRelationshipDefinitions()) {

            List<RelationshipWeb> relationships= this.getValidRelationshipsWebByRelationDefinition(relationshipDef);

            if(relationships.size()<relationshipDef.getMultiplicity().getLowerBoundary())
                return false;
            /*
            for (Relationship relationship : relationships) {
                if(relationship.getTarget().)
            }
            */
        }

        return true;
    }

    @Override
    public String toString() {

        return super.toString();
    }

    public void editDescription(DescriptionWeb description) {

        if(description.isPersisted() && !description.hasBeenModified()){

            boolean isDescriptionFound = false;

            for (DescriptionWeb descriptionWeb : descriptionsWeb) {
                if(descriptionWeb.equals(description)) {
                    descriptionWeb.setModified(true);
                    descriptionWeb.setToBeUpdated(true);
                    descriptionWeb.setValidityUntil(new Timestamp(System.currentTimeMillis()));
                    isDescriptionFound = true;
                }
            }

            if(isDescriptionFound)
                descriptionsWeb.add(new DescriptionWeb(description, false));
        }

    }

    public String validateDescription(Description description) {
        if(description.getDescriptionType().getName().equalsIgnoreCase("FSN")){
            if(description==null || description.getTerm().length()==0){
                return "Falta el FSN al concepto";
            }
        }
        return "";
    }
}
