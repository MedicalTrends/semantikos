package cl.minsal.semantikos.ws.service;

import cl.minsal.semantikos.kernel.daos.RefSetDAO;
import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.ws.mapping.RefSetMapper;
import cl.minsal.semantikos.ws.response.RefSetResponse;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Development on 2016-10-11.
 *
 */
@WebService
public class RefSetService {

    @EJB
    private RefSetDAO refSetDAO;

    @WebMethod(operationName = "listaRefSet")
    @WebResult(name = "refSet")
    public List<RefSetResponse> listaRefSet() {
        List<RefSetResponse> res = new ArrayList<>();
        List<RefSet> refSets = this.refSetDAO.getReftsets();

        if ( refSets != null ) {
            for ( RefSet refSet : refSets ) {
                res.add(RefSetMapper.map(refSet));
            }
        }

        return res;
    }

}
