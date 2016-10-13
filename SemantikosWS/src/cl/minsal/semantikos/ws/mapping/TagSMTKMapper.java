package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.TagSMTK;
import cl.minsal.semantikos.ws.response.TagSMTKResponse;

/**
 * Created by Development on 2016-10-11.
 *
 */
public class TagSMTKMapper {

    public static TagSMTKResponse map(TagSMTK tagSMTK) {
        if ( tagSMTK != null ) {
            TagSMTKResponse res = new TagSMTKResponse();

            res.setName(tagSMTK.getName());
            res.setId(tagSMTK.getId());

            return res;
        } else {
            return null;
        }
    }

    public static TagSMTK map(TagSMTKResponse tagSMTKResponse) {
        throw new IllegalStateException("Not implemented yet");
    }

}
