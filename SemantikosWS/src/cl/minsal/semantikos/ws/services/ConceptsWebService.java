package cl.minsal.semantikos.ws.services;

import cl.minsal.semantikos.kernel.daos.CategoryDAO;
import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.ws.mapping.CategoryMapper;
import cl.minsal.semantikos.ws.response.CategoryResponse;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Development on 2016-10-11.
 *
 */
@WebService
public class ConceptsWebService {

    @EJB
    private ConceptDAO conceptDAO;

    @EJB
    private CategoryDAO categoryDAO;

    @WebMethod(operationName = "conceptosPorCategoria")
    @WebResult(name = "conceptosPorCategoria")
    public CategoryResponse example(
            @XmlElement(required = true)
            @WebParam(name = "key")
                    String key
    ) throws Exception {
        Category category = this.categoryDAO.getCategoryById(new Long(key));
        return CategoryMapper.map(category);
        //List<ConceptSMTK> concepts = this.conceptDAO.getModeledConceptBy(new Long(key), 10, 0);
    }

}
