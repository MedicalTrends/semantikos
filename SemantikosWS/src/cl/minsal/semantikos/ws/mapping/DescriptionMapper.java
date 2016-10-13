package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.ws.response.DescriptionResponse;

/**
 * Created by Development on 2016-10-13.
 *
 */
public class DescriptionMapper {

    public static DescriptionResponse map(Description description) {
        if ( description != null ) {
            DescriptionResponse res = new DescriptionResponse();

            res.setId(description.getId());
            res.setDescriptionID(description.getDescriptionId());
            res.setTerm(description.getTerm());

            return res;
        } else {
            return null;
        }
    }

    public static Description map(DescriptionResponse descriptionResponse) {
        throw new IllegalStateException("Not implemented yet");
    }

}
