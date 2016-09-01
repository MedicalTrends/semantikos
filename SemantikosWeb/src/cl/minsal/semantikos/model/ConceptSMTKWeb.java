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
        super(conceptSMTK.getConceptID(), conceptSMTK.getCategory(), conceptSMTK.isToBeReviewed(), conceptSMTK.isToBeConsulted(), conceptSMTK.isModeled(),
              conceptSMTK.isFullyDefined(), conceptSMTK.isPublished(), conceptSMTK.getDescriptions().toArray(new Description[conceptSMTK.getDescriptions().size()]));
        if(conceptSMTK.isPersistent()){
            this.setId(conceptSMTK.getId());
            for (Description description : this.getValidDescriptions()) {
                // Si la descripcion está persistida, clonar la descripción y agregarla a la lista de descripciones web
                this.descriptionsWeb.add(new DescriptionWeb(description.getId(),description));
            }
            for (Relationship relationship : this.getValidRelationships()) {
                // Si la relación está persistida dejar en el respaldo las originales
                this.relationshipsWeb.add(new RelationshipWeb(relationship.getId(), relationship));
            }
        }
    }

    public List<DescriptionWeb> getDescriptionsWeb() {
        return descriptionsWeb;
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



    public List<Pair<Description, Description>> getDescriptionsForUpdate() {

        List<Pair<Description, Description>> descriptionsForUpdate = new ArrayList<Pair<Description, Description>>();

        //Primero se buscan todas las descripciones persistidas originales
        for (Description initDescription : getValidDescriptions()) {
            //Por cada descripción original se busca su descripcion vista correlacionada
            for (DescriptionWeb finalDescription : getDescriptionsWeb()) {
                //Si la descripcion correlacionada sufrio alguna modificación agregar el par (init, final)
                if (initDescription.getId() == finalDescription.getId() && !finalDescription.equals(initDescription) /*finalDescription.hasBeenModified()*/) {
                    descriptionsForUpdate.add(new Pair(initDescription, finalDescription));
                }
            }
        }
        return descriptionsForUpdate;
    }

    public List<Description> getDescriptionsForPersist() {

        List<Description> descriptionsForPersist = new ArrayList<Description>();

        for (DescriptionWeb descriptionWeb : getDescriptionsWeb()) {
            if(!descriptionWeb.isPersistent())
                descriptionsForPersist.add(descriptionWeb);
        }
        return descriptionsForPersist;
    }

    public List<Description> getDescriptionsForDelete() {

        List<Description> descriptionsForDelete = new ArrayList<Description>();
        boolean isDescriptionFound;

        //Primero se buscan todas las descripciones persistidas originales
        for (Description initDescription : getValidDescriptions()) {
            isDescriptionFound = false;
            //Por cada descripción original se busca su descripcion vista correlacionada
            for (DescriptionWeb finalDescription : getDescriptionsWeb()) {
                //Si la descripcion correlacionada no es encontrada, significa que fué eliminada
                if (initDescription.getId() == finalDescription.getId()) {
                    isDescriptionFound = true;
                }
            }
            if(!isDescriptionFound)
                descriptionsForDelete.add(initDescription);
        }
        return  descriptionsForDelete;
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

    public void addDescriptionWeb(DescriptionWeb descriptionWeb) {
        this.addDescription(descriptionWeb);
        this.descriptionsWeb.add(descriptionWeb);
    }

}