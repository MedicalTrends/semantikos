package cl.minsal.semantikos.designer_modeler.designer;

import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.ConceptSMTKWeb;
import cl.minsal.semantikos.model.RelationshipWeb;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipAttribute;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.management.relation.Relation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by des01c7 on 14-10-16.
 */
@ManagedBean(name = "compositeAditionalBean")
@ViewScoped
public class CompositeAditional {
 //TODO: refactorizar
    @EJB
    private ConceptManager conceptManager;

    private Map<Long, List<Relationship>> relationships;

    @PostConstruct
    public void init(){
        relationships= new HashMap<>();
    }


    public String getCantidadMC(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);

        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==69){

                return relationshipWeb.getTarget().toString();
            }
        }

        return null;
    }

    public String getUnidadCantidadMC(ConceptSMTK conceptSMTK){

        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);
        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==69){
                for (RelationshipAttribute relationshipAttribute:relationshipWeb.getRelationshipAttributes()){
                    if(relationshipAttribute.getRelationAttributeDefinition().getId()==12){
                        HelperTableRecord helperRecord = (HelperTableRecord) relationshipAttribute.getTarget();
                        return helperRecord.getValueColumn("description");
                    }

                }

            }
        }

        return null;
    }




    public String getTipoMCCE(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);

        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==75){

                HelperTableRecord helperRecord = (HelperTableRecord) relationshipWeb.getTarget();
                return helperRecord.getValueColumn("description");
            }
        }

        return null;
    }

    public String getPackMultiCant(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);

        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==77){

                return relationshipWeb.getTarget().toString();
            }
        }

        return null;
    }

    public String getPackMultiUnidad(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);
        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==77){
                for (RelationshipAttribute relationshipAttribute:relationshipWeb.getRelationshipAttributes()){
                    if(relationshipAttribute.getRelationAttributeDefinition().getId()==16){
                        HelperTableRecord helperRecord = (HelperTableRecord) relationshipAttribute.getTarget();
                        return helperRecord.getValueColumn("description");
                    }

                }

            }
        }return null;
    }

    public String getVolumentTotalCant(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);

        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==93){

                return relationshipWeb.getTarget().toString();
            }
        }return null;
    }
    public String getVolumentTotalUnidad(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);
        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==93){
                for (RelationshipAttribute relationshipAttribute:relationshipWeb.getRelationshipAttributes()){
                    if(relationshipAttribute.getRelationAttributeDefinition().getId()==17){
                        HelperTableRecord helperRecord = (HelperTableRecord) relationshipAttribute.getTarget();
                        return helperRecord.getValueColumn("description");
                    }

                }

            }
        }return null;
    }



    public String getCantidadMCToPCCE(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);

        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==48){


                return getCantidadMC((ConceptSMTK) relationshipWeb.getTarget());
            }
        }return null;

    }

    public String getUnidadCantidadMCToPCCE(ConceptSMTK conceptSMTK){
        conceptSMTK.setRelationships(getRelationships(conceptSMTK));
        ConceptSMTKWeb conceptSMTKWeb= new ConceptSMTKWeb(conceptSMTK);

        for (RelationshipWeb relationshipWeb: conceptSMTKWeb.getValidPersistedRelationshipsWeb()) {
            if(relationshipWeb.getRelationshipDefinition().getId()==48){
                return getUnidadCantidadMC((ConceptSMTK) relationshipWeb.getTarget());
            }
        }return null;
    }


    public List<Relationship> getRelationships(ConceptSMTK conceptSMTK){

        if(!relationships.containsKey(conceptSMTK.getId())){
            List <Relationship> relationshipsList = conceptManager.loadRelationships(conceptSMTK);
            relationships.put(conceptSMTK.getId(),relationshipsList);
            return relationshipsList;
        }else{
            return relationships.get(conceptSMTK.getId());
        }

    }



}
