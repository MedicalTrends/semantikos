package cl.minsal.semantikos.ws.service;

import cl.minsal.semantikos.kernel.components.RefSetManager;
import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.ws.mapping.RefSetMapper;
import cl.minsal.semantikos.ws.response.RefSetResponse;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Development on 2016-10-11.
 *
 */
@WebService
public class RefSetService {

    @EJB
    private RefSetManager refSetManager;

    @WebMethod(operationName = "listaRefSet")
    @WebResult(name = "refSet")
    public List<RefSetResponse> listaRefSet() {
        List<RefSetResponse> res = new ArrayList<>();
        List<RefSet> refSets = this.refSetManager.getAllRefSets();
        mapResults(res, refSets);
        return res;
    }

    @WebMethod(operationName = "descripcionesDeRefSet")
    @WebResult(name = "refSet")
    public List<RefSetResponse> descripcionesDeRefSet(
            @XmlElement(required = true)
            @WebParam(name = "nombreRefSet")
                    String refSetName,
            @XmlElement(required = true)
            @WebParam(name = "numeroPagina")
                    Integer pageNumber,
            @XmlElement(required = true)
            @WebParam(name = "tamanoPagina")
                    Integer pageSize
    ) {
        List<RefSetResponse> res = new ArrayList<>();
        List<RefSet> refSets = this.refSetManager.findRefsetsByName(refSetName);
        mapResults(res, refSets);
        return res;
    }

    private void mapResults(List<RefSetResponse> res, List<RefSet> refSets) {
        if ( refSets != null ) {
            for ( RefSet refSet : refSets ) {
                res.add(RefSetMapper.map(refSet));
            }
        }
    }

}
