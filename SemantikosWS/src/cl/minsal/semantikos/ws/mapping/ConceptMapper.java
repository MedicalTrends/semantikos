package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.ws.response.ConceptResponse;
import cl.minsal.semantikos.ws.response.DescriptionResponse;

import java.util.ArrayList;
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
            res.setId(conceptSMTK.getId());
            res.setObservation(conceptSMTK.getObservation());
            res.setToBeConsulted(conceptSMTK.isToBeConsulted());
            res.setToBeReviewed(conceptSMTK.isToBeReviewed());
            res.setValidUntil(MappingUtil.toDate(conceptSMTK.getValidUntil()));

            try {
                Description preferedDescription = conceptSMTK.getDescriptionFavorite();
                res.setPreferedDescription(DescriptionMapper.map(preferedDescription));
            } catch (Exception ignored) {}

            try {
                Description fsnDescription = conceptSMTK.getDescriptionFSN();
                res.setFsnDescription(DescriptionMapper.map(fsnDescription));
            } catch (Exception ignored) {}

            return res;
        } else {
            return null;
        }
    }

    public static ConceptSMTK map(ConceptResponse conceptResponse) {
        throw new IllegalStateException("Not implemented yet");
    }

}
