package cl.minsal.semantikos.util;

import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.relationships.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 01-09-16.
 */
public class ConceptUtils {

    public static List<Pair<Description, Description>> getModifiedDescriptions(List<DescriptionWeb> initDescriptions, List<DescriptionWeb> finalDescriptions) {

        List<Pair<Description, Description>> descriptionsForUpdate = new ArrayList<Pair<Description, Description>>();// Si la relación está persistida dejar en el respaldo las originales

        //Primero se buscan todas las descripciones persistidas originales
        for (DescriptionWeb initDescription : initDescriptions) {
            //Por cada descripción original se busca su descripcion vista correlacionada
            for (DescriptionWeb finalDescription : finalDescriptions) {
                //Si la descripcion correlacionada sufrio alguna modificación agregar el par (init, final)
                if (initDescription.getId() == finalDescription.getId() && !finalDescription.equals(initDescription) /*finalDescription.hasBeenModified()*/) {
                    descriptionsForUpdate.add(new Pair(initDescription, finalDescription));
                }
            }
        }
        return descriptionsForUpdate;
    }

    public static List<Description> getNewDescriptions(List<DescriptionWeb> initDescriptions, List<DescriptionWeb> finalDescriptions) {

        List<Description> descriptionsForPersist = new ArrayList<Description>();

        for (DescriptionWeb descriptionWeb : finalDescriptions) {
            if(!descriptionWeb.isPersistent())
                descriptionsForPersist.add(descriptionWeb);
        }
        return descriptionsForPersist;
    }

    public static List<Description> getDeletedDescriptions(List<DescriptionWeb> initDescriptions, List<DescriptionWeb> finalDescriptions) {

        List<Description> descriptionsForDelete = new ArrayList<Description>();
        boolean isDescriptionFound;

        //Primero se buscan todas las descripciones persistidas originales
        for (Description initDescription : initDescriptions) {
            isDescriptionFound = false;
            //Por cada descripción original se busca su descripcion vista correlacionada
            for (DescriptionWeb finalDescription : finalDescriptions) {
                //Si la descripcion correlacionada no es encontrada, significa que fué eliminada
                if (initDescription.getId() == finalDescription.getId()) {
                    isDescriptionFound = true;
                    break;
                }
            }
            if(!isDescriptionFound)
                descriptionsForDelete.add(initDescription);
        }
        return  descriptionsForDelete;
    }


    public static List<Pair<Relationship, Relationship>> getModifiedRelationships(List<RelationshipWeb> initRelationships, List<RelationshipWeb> finalRelationships) {

        List<Pair<Relationship, Relationship>> relationshipsForUpdate = new ArrayList<Pair<Relationship, Relationship>>();// Si la relación está persistida dejar en el respaldo las originales

        //Primero se buscan todas las descripciones persistidas originales
        for (RelationshipWeb initRelationship : initRelationships) {
            //Por cada descripción original se busca su descripcion vista correlacionada
            for (RelationshipWeb finalRelationship : finalRelationships) {
                //Si la descripcion correlacionada sufrio alguna modificación agregar el par (init, final)
                if (initRelationship.getId() == finalRelationship.getId() && !finalRelationship.equals(initRelationship) /*finalDescription.hasBeenModified()*/) {
                    relationshipsForUpdate.add(new Pair(initRelationship, finalRelationship));
                }
            }
        }
        return relationshipsForUpdate;
    }

    public static List<Relationship> getNewRelationships(List<RelationshipWeb> initRelationships, List<RelationshipWeb> finalRelationships) {

        List<Relationship> relationshipsForPersist = new ArrayList<Relationship>();

        for (RelationshipWeb relationshipWeb : finalRelationships) {
            if(!relationshipWeb.isPersistent())
                relationshipsForPersist.add(relationshipWeb);
        }
        return relationshipsForPersist;
    }

    public static List<Relationship> getDeletedRelationships(List<RelationshipWeb> initRelationships, List<RelationshipWeb> finalRelationships) {

        List<Relationship> relationshipsForDelete = new ArrayList<Relationship>();
        boolean isRelationshipFound;

        //Primero se buscan todas las descripciones persistidas originales
        for (Relationship initRelationship : initRelationships) {
            isRelationshipFound = false;
            //Por cada descripción original se busca su descripcion vista correlacionada
            for (RelationshipWeb finalRelationship : finalRelationships) {
                //Si la descripcion correlacionada no es encontrada, significa que fué eliminada
                if (initRelationship.getId() == finalRelationship.getId()) {
                    isRelationshipFound = true;
                }
            }
            if(!isRelationshipFound)
                relationshipsForDelete.add(initRelationship);
        }
        return relationshipsForDelete;
    }

}
