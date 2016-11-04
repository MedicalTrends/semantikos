package cl.minsal.semantikos.ws.service;

import cl.minsal.semantikos.kernel.components.CategoryManager;
import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.kernel.components.DescriptionManager;
import cl.minsal.semantikos.kernel.components.RefSetManager;
import cl.minsal.semantikos.kernel.daos.CategoryDAO;
import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.kernel.daos.DescriptionDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.ws.fault.IllegalInputFault;
import cl.minsal.semantikos.ws.fault.NotFoundFault;
import cl.minsal.semantikos.ws.mapping.CategoryMapper;
import cl.minsal.semantikos.ws.mapping.ConceptMapper;
import cl.minsal.semantikos.ws.response.*;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Development on 2016-10-11.
 *
 */
@WebService
public class ConceptsService {

    @EJB
    private CategoryManager categoryManager;
    @EJB
    private DescriptionDAO descriptionDAO;
    @EJB
    private ConceptManager conceptManager;
    @EJB
    private RefSetManager refSetManager;

    // REQ-WS-028
    // REQ-WS-007
    @WebMethod(operationName = "conceptoPorIdDescripcion")
    @WebResult(name = "concepto")
    public ConceptResponse conceptoPorIdDescripcion(
            @XmlElement(required = true)
            @WebParam(name = "idDescripcion")
            String descriptionId
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
        refSetManager.loadConceptRefSets(conceptSMTK);
        ConceptResponse res = ConceptMapper.map(conceptSMTK);
        ConceptMapper.appendDescriptions(res, conceptSMTK);
        ConceptMapper.appendAttributes(res, conceptSMTK);
        ConceptMapper.appendRelationships(res, conceptSMTK);
        ConceptMapper.appendCategory(res, conceptSMTK);
        ConceptMapper.appendRefSets(res, conceptSMTK);
        return res;
    }

    // REQ-WS-002
    @WebMethod(operationName = "conceptosPorCategoria")
    @WebResult(name = "conceptosPorCategoria")
    public ConceptsByCategoryResponse conceptosPorCategoria(
            @XmlElement(required = true)
            @WebParam(name = "nombreCategoria")
                    String categoryName,
            @XmlElement(required = false, defaultValue = "0")
            @WebParam(name = "numeroPagina")
                    Integer pageNumber,
            @XmlElement(required = false, defaultValue = "10")
            @WebParam(name = "tamanoPagina")
                    Integer pageSize
    ) throws NotFoundFault {
        Category category = null;
        try {
            category = this.categoryManager.getCategoryByName(categoryName);
        } catch (Exception ignored) {}

        if (category == null) {
            throw new NotFoundFault("No se encuentra la categoria");
        }

        Integer total = this.conceptManager.countModeledConceptBy(category);
        List<ConceptSMTK> concepts = this.conceptManager.findModeledConceptBy(category, pageSize, pageNumber);
        ConceptsByCategoryResponse res = new ConceptsByCategoryResponse();
        res.setCategoryResponse(CategoryMapper.map(category));

        List<ConceptResponse> conceptResponses = new ArrayList<>();
        for ( ConceptSMTK conceptSMTK : concepts ) {
            conceptManager.loadRelationships(conceptSMTK);
            refSetManager.loadConceptRefSets(conceptSMTK);
            ConceptResponse concept = ConceptMapper.map(conceptSMTK);
            ConceptMapper.appendDescriptions(concept, conceptSMTK);
            ConceptMapper.appendAttributes(concept, conceptSMTK);
            ConceptMapper.appendRelationships(concept, conceptSMTK);
            ConceptMapper.appendRefSets(concept, conceptSMTK);
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

    @WebMethod(operationName = "listaCategorias")
    @WebResult(name = "categoria")
    public List<CategoryResponse> listaCategorias() {
        List<CategoryResponse> res = new ArrayList<>();
        List<Category> categories = this.categoryManager.getCategories();
        if ( categories != null ) {
            for ( Category category : categories ) {
                res.add(CategoryMapper.map(category));
            }
        }
        return res;
    }

    // REQ-WS-001
    // REQ-WS-006
    @WebMethod(operationName = "buscarTermino")
    @WebResult(name = "buscarTermino")
    public TermSearchResponse buscarTermino(
            @XmlElement(required = true)
            @WebParam(name = "termino")
                    String term,
            @XmlElement(required = false)
            @WebParam(name = "nombreCategoria")
                    List<String> categoryNames,
            @XmlElement(required = false)
            @WebParam(name = "nombreRefSet")
                    List<String> refSetNames,
            @XmlElement(required = false, defaultValue = "0")
            @WebParam(name = "numeroPagina")
                    Integer pageNumber,
            @XmlElement(required = false, defaultValue = "10")
            @WebParam(name = "tamanoPagina")
                    Integer pageSize
    ) throws IllegalInputFault {
        if ( (categoryNames == null && refSetNames == null)
                || (categoryNames.isEmpty() && refSetNames.isEmpty())) {
            throw new IllegalInputFault("Debe ingresar por lo menos una Categoría o un RefSet");
        }
        return null;
    }

    // REQ-WS-004
    @WebMethod(operationName = "buscarTruncatePerfect")
    @WebResult(name = "buscarTruncatePerfect")
    public TermSearchResponse buscarTruncatePerfect(
            @XmlElement(required = true)
            @WebParam(name = "termino")
                    String term,
            @XmlElement(required = false)
            @WebParam(name = "nombreCategoria")
                    List<String> categoryNames,
            @XmlElement(required = false)
            @WebParam(name = "nombreRefSet")
                    List<String> refSetNames,
            @XmlElement(required = false, defaultValue = "0")
            @WebParam(name = "numeroPagina")
                    Integer pageNumber,
            @XmlElement(required = false, defaultValue = "10")
            @WebParam(name = "tamanoPagina")
                    Integer pageSize
    ) throws IllegalInputFault {
        if ( (categoryNames == null && refSetNames == null)
                || (categoryNames.isEmpty() && refSetNames.isEmpty())) {
            throw new IllegalInputFault("Debe ingresar por lo menos una Categoría o un RefSet");
        }
        return null;
    }

    // REQ-WS-003
    @WebMethod(operationName = "codificacionDeNuevoTermino")
    @WebResult(name = "codificacionDeNuevoTermino")
    public NewTermResponse codificacionDeNuevoTermino(
            @XmlElement(required = true)
            @WebParam(name = "establecimiento")
                    String institutionName,
            @XmlElement(required = true)
            @WebParam(name = "idConcepto")
                    String conceptId,
            @XmlElement(required = true)
            @WebParam(name = "termino")
                    String term,
            @XmlElement(required = false, defaultValue = "Preferida")
            @WebParam(name = "tipoDescripcion")
                    String descriptionTypeName,
            @XmlElement(required = false, defaultValue = "false")
            @WebParam(name = "esSensibleAMayusculas")
                    Boolean isCaseSensitive,
            @XmlElement(required = false) // TODO: Necesario? para que se ocupa?
            @WebParam(name = "email")
                    String email,
            @XmlElement(required = false) // TODO: Necesario? para que se ocupa?
            @WebParam(name = "observacion")
                    String observation,
            @XmlElement(required = false) // TODO: Necesario? para que se ocupa?
            @WebParam(name = "profesional")
                    String professional,
            @XmlElement(required = false) // TODO: Necesario? para que se ocupa?
            @WebParam(name = "profesion")
                    String profesion,
            @XmlElement(required = false) // TODO: Necesario? para que se ocupa?
            @WebParam(name = "especialidad")
                    String specialty
    ) {
        return null;
    }

    // REQ-WS-005
    @WebMethod(operationName = "obtenerTerminosPedibles")
    @WebResult(name = "obtenerTerminosPedibles")
    public TermSearchResponse obtenerTerminosPedibles(
            @XmlElement(required = false)
            @WebParam(name = "nombreCategoria")
                    List<String> categoryNames,
            @XmlElement(required = false)
            @WebParam(name = "nombreRefSet")
                    List<String> refSetNames,
            @XmlElement(required = true)
            @WebParam(name = "pedible")
                    String requestable,
            @XmlElement(required = false, defaultValue = "0")
            @WebParam(name = "numeroPagina")
                    Integer pageNumber,
            @XmlElement(required = false, defaultValue = "10")
            @WebParam(name = "tamanoPagina")
                    Integer pageSize
    ) throws IllegalInputFault {
        if ( (categoryNames == null && refSetNames == null)
                || (categoryNames.isEmpty() && refSetNames.isEmpty())) {
            throw new IllegalInputFault("Debe ingresar por lo menos una Categoría o un RefSet");
        }
        return null;
    }

}
