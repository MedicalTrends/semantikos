package cl.minsal.semantikos.ws.service;

import cl.minsal.semantikos.kernel.daos.CategoryDAO;
import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.ws.fault.NotFoundFault;
import cl.minsal.semantikos.ws.mapping.CategoryMapper;
import cl.minsal.semantikos.ws.mapping.ConceptMapper;
import cl.minsal.semantikos.ws.response.CategoryResponse;
import cl.minsal.semantikos.ws.response.ConceptResponse;
import cl.minsal.semantikos.ws.response.ConceptsByCategoryResponse;
import cl.minsal.semantikos.ws.response.PaginationResponse;

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
public class ConceptsWebService {

    @EJB
    private ConceptDAO conceptDAO;

    @EJB
    private CategoryDAO categoryDAO;

    @WebMethod(operationName = "conceptosPorCategoria")
    @WebResult(name = "conceptosPorCategoria")
    public ConceptsByCategoryResponse conceptosPorCategoria(
            @XmlElement(required = true)
            @WebParam(name = "idCateogria")
                    Long categoryId,
            @XmlElement(required = true)
            @WebParam(name = "numeroPagina")
                    Integer pageNumber,
            @XmlElement(required = true)
            @WebParam(name = "tamanoPagina")
                    Integer pageSize
    ) throws NotFoundFault {
        Category category;

        try {
            category = this.categoryDAO.getCategoryById(categoryId);
        } catch (Exception e) {
            throw new NotFoundFault("No se encuentra la categoria");
        }

        if (category == null) {
            throw new NotFoundFault("No se encuentra la categoria");
        }

        Integer total = this.conceptDAO.countModeledConceptBy(categoryId);
        List<ConceptSMTK> concepts = this.conceptDAO.getModeledConceptBy(categoryId, pageSize, pageNumber);

        ConceptsByCategoryResponse res = new ConceptsByCategoryResponse();

        res.setCategoryResponse(CategoryMapper.map(category));

        List<ConceptResponse> conceptResponses = new ArrayList<>();
        for ( ConceptSMTK conceptSMTK : concepts ) {
            conceptResponses.add(ConceptMapper.map(conceptSMTK));
        }
        res.setConceptResponses(conceptResponses);

        PaginationResponse paginationResponse = new PaginationResponse();

        paginationResponse.setTotalCount(total);
        paginationResponse.setCurrentPage(pageNumber);
        paginationResponse.setPageSize(pageSize);
        if ( !conceptResponses.isEmpty() ) {
            paginationResponse.setShowingFrom(pageNumber * pageSize);
            paginationResponse.setShowingTo(paginationResponse.getShowingFrom() + conceptResponses.size() - 1);
        }

        res.setPaginationResponse(paginationResponse);

        return res;
    }

}
