package cl.minsal.semantikos.ws.service;

import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.daos.CategoryDAO;
import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.kernel.daos.DescriptionDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
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
    @EJB
    private DescriptionDAO descriptionDAO;
    @EJB
    private ConceptManager conceptManager;

    // REQ-WS-028
    @WebMethod(operationName = "conceptoPorIdDescripcion")
    @WebResult(name = "concepto")
    public ConceptResponse conceptoPorIdDescripcion(
            @XmlElement(required = true)
            @WebParam(name = "idDescripcion")
            Long descriptionId
    ) throws NotFoundFault {
        Description description = null;
        try {
            description = this.descriptionDAO.getDescriptionBy(descriptionId);
        } catch (Exception ignored) {}

        if ( description == null ) {
            throw new NotFoundFault("Descripcion no encontrada");
        }

        ConceptSMTK conceptSMTK = description.getConceptSMTK();

        if ( conceptSMTK == null ) {
            throw new NotFoundFault("Concepto no encontrado");
        }

        conceptManager.loadRelationships(conceptSMTK);
        ConceptResponse res = ConceptMapper.map(conceptSMTK);
        ConceptMapper.appendDescriptions(res, conceptSMTK);
        ConceptMapper.appendAttributes(res, conceptSMTK);
        ConceptMapper.appendRelationships(res, conceptSMTK);
        return res;
    }

    // REQ-WS-002
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
        Category category = null;

        try {
            category = this.categoryDAO.getCategoryById(categoryId);
        } catch (Exception ignored) {}

        if (category == null) {
            throw new NotFoundFault("No se encuentra la categoria");
        }

        Integer total = this.conceptDAO.countModeledConceptBy(categoryId);
        List<ConceptSMTK> concepts = this.conceptDAO.getModeledConceptBy(categoryId, pageSize, pageNumber);

        ConceptsByCategoryResponse res = new ConceptsByCategoryResponse();

        res.setCategoryResponse(CategoryMapper.map(category));

        List<ConceptResponse> conceptResponses = new ArrayList<>();
        for ( ConceptSMTK conceptSMTK : concepts ) {
            conceptManager.loadRelationships(conceptSMTK);
            ConceptResponse concept = ConceptMapper.map(conceptSMTK);
            ConceptMapper.appendDescriptions(concept, conceptSMTK);
            ConceptMapper.appendAttributes(concept, conceptSMTK);
            ConceptMapper.appendRelationships(concept, conceptSMTK);
            conceptResponses.add(concept);
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
