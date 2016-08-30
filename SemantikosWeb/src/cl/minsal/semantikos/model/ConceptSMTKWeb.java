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

    //Respaldo de las descripciones originales
    List<Description> descriptionsBackup = new ArrayList<Description>();
    //Respaldo de las relaciones originales
    List<Relationship> relationshipsBackup = new ArrayList<Relationship>();

    //Este es el constructor mínimo
    public ConceptSMTKWeb(ConceptSMTK conceptSMTK) {
        super(conceptSMTK.getConceptID(), conceptSMTK.getCategory(), conceptSMTK.isToBeReviewed(), conceptSMTK.isToBeConsulted(), conceptSMTK.isModeled(),
              conceptSMTK.isFullyDefined(), conceptSMTK.isPublished(), conceptSMTK.getDescriptions().toArray(new Description[conceptSMTK.getDescriptions().size()]));
        if(conceptSMTK.isPersistent()){
            this.setId(conceptSMTK.getId());
            for (Description description : this.getDescriptions()) {
                // Si la descripcion está persistida dejar en el respaldo las originales
                this.descriptionsBackup.add(description);
            }
            for (Relationship relationship : this.getRelationships()) {
                // Si la relación está persistida dejar en el respaldo las originales
                this.relationshipsBackup.add(relationship);
            }
        }
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
        for (Description description : getDescriptions()) {
            DescriptionType descriptionType = description.getDescriptionType();
            if (descriptionType.getName().equalsIgnoreCase("FSN") &&
               (description.getValidityUntil() == null || description.getValidityUntil().after(new Timestamp(System.currentTimeMillis())))) {
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
        for (Description description : getDescriptions()) {
            if (description.getDescriptionType().getName().equalsIgnoreCase("preferida") &&
               (description.getValidityUntil() == null || description.getValidityUntil().after(new Timestamp(System.currentTimeMillis())))) {
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
    public List<Description> getValidDescriptionsButFSNandFavorite() {

        List<Description> otherDescriptions = new ArrayList<Description>();
        DescriptionType fsnType = DescriptionTypeFactory.getInstance().getFSNDescriptionType();
        DescriptionType favoriteType = DescriptionTypeFactory.getInstance().getFavoriteDescriptionType();

        for (Description description : getDescriptions()) {
            if (!description.getDescriptionType().equals(fsnType) && !description.getDescriptionType().equals(favoriteType) &&
                (description.getValidityUntil() == null || description.getValidityUntil().after(new Timestamp(System.currentTimeMillis())))) {
                otherDescriptions.add(description);
            }
        }

        return otherDescriptions;
    }



    public List<Pair<Description, Description>> getDescriptionsForUpdate() {

        List<Pair<Description, Description>> descriptionsForUpdate = new ArrayList<Pair<Description, Description>>();

        //Primero se buscan todas las descripciones persistidas originales
        for (Description initDescription : descriptionsBackup) {
            //Por cada descripción original se busca su descripcion vista correlacionada
            for (Description finalDescription : getDescriptions()) {
                //Si la descripcion correlacionada sufrio alguna modificación agregar
                if (initDescription.getId() == finalDescription.getId() && !initDescription.equals(finalDescription)) {
                    descriptionsForUpdate.add(new Pair(initDescription, finalDescription));
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


}
