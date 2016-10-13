package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.ws.response.DescriptionTypeResponse;

/**
 * Created by Development on 2016-10-13.
 *
 */
public class DescriptionTypeMapper {

    public static DescriptionTypeResponse map(DescriptionType descriptionType) {
        if ( descriptionType != null ) {
            DescriptionTypeResponse res = new DescriptionTypeResponse();

            res.setId(descriptionType.getId());
            res.setName(descriptionType.getName());
            res.setDescription(descriptionType.getDescription());

            return res;
        } else {
            return null;
        }
    }

    public static DescriptionType map(DescriptionTypeResponse descriptionTypeResponse) {
        throw new IllegalStateException("Not implemented yet");
    }

}
