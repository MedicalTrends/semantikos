package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.ws.response.TargetResponse;

/**
 * Created by Development on 2016-10-14.
 *
 */
public class TargetMapper {

    public static TargetResponse map(Target target) {
        if ( target != null ) {
            TargetResponse res = new TargetResponse();

            res.setId(target.getId());
            res.setTargetTypeResponse(TargetTypeMapper.map(target.getTargetType()));

            return res;
        }

        return null;
    }

}
