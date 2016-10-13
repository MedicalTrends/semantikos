package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.ws.response.RefSetResponse;

/**
 * Created by Development on 2016-10-13.
 *
 */
public class RefSetMapper {

    public static RefSetResponse map(RefSet refSet) {
        if ( refSet != null ) {
            RefSetResponse res = new RefSetResponse();
            res.setId(refSet.getId());
            res.setName(refSet.getName());
            res.setValidityUntil(MappingUtil.toDate(refSet.getValidityUntil()));
            res.setCreationDate(MappingUtil.toDate(refSet.getCreationDate()));
            res.setInstitution(InstitutionMapper.map(refSet.getInstitution()));
            return res;
        } else {
            return null;
        }
    }

    public static RefSet map(RefSetResponse refSetResponse) {
        throw new IllegalStateException("Not implemented yet");
    }

}
