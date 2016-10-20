package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.ws.response.ConceptResponse;
import cl.minsal.semantikos.ws.response.DescriptionResponse;
import cl.minsal.semantikos.ws.response.RelationshipResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Development on 2016-10-13.
 *
 */
public class ConceptMapper {

    public static ConceptResponse map(ConceptSMTK conceptSMTK) {
        if ( conceptSMTK != null ) {
            ConceptResponse res = new ConceptResponse();

            res.setPublished(conceptSMTK.isPublished());
            res.setModeled(conceptSMTK.isModeled());
            res.setConceptId(conceptSMTK.getConceptID());
            res.setFullyDefined(conceptSMTK.isFullyDefined());
            res.setObservation(conceptSMTK.getObservation());
            res.setToBeConsulted(conceptSMTK.isToBeConsulted());
            res.setToBeReviewed(conceptSMTK.isToBeReviewed());
            res.setValidUntil(MappingUtil.toDate(conceptSMTK.getValidUntil()));

            return res;
        } else {
            return null;
        }
    }

    public static ConceptResponse appendDescriptions(ConceptResponse conceptResponse, ConceptSMTK conceptSMTK) {
        if ( conceptResponse != null
                && conceptSMTK != null
                && conceptSMTK.getDescriptions() != null ) {
            List<DescriptionResponse> descriptions = new ArrayList<>();
            for ( Description description : conceptSMTK.getDescriptions() ) {
                descriptions.add(DescriptionMapper.map(description));
            }
            Collections.sort(descriptions);
            conceptResponse.setDescriptions(descriptions);
        }

        return conceptResponse;
    }

    public static ConceptResponse appendRelationships(ConceptResponse conceptResponse, ConceptSMTK conceptSMTK) {
        if ( conceptResponse != null
                && conceptSMTK != null
                && conceptSMTK.getRelationships() != null ) {
            List<RelationshipResponse> relationshipResponses = new ArrayList<>();
            for (Relationship relationship : conceptSMTK.getRelationships()) {
                relationshipResponses.add(RelationshipMapper.map(relationship));
            }
            conceptResponse.setRelationships(relationshipResponses);
        }

        return conceptResponse;
    }


}
