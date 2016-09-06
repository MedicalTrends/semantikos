package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 02-09-16.
 */
@ManagedBean(name = "validatorBean")
@ViewScoped
public class ValidatorBean {

    String uiState = "";

    /**
     * Este metodo revisa que las relaciones cumplan el lower_boundary del
     * relationship definition, en caso de no cumplir la condicion se retorna falso.
     *
     * @return
     */
    public void validateRequired(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String msg = "Debe ingresar un término";

        //component.getParent().getAttributes().

        if (value.toString().trim().equals(""))
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    /**
     * Este metodo revisa que las relaciones cumplan el lower_boundary del
     * relationship definition, en caso de no cumplir la condicion se retorna falso.
     *
     * @return
     */
    public void validateRelationships(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String msg = "Faltan relaciones para los elementos marcados";

        ConceptSMTK concept = (ConceptSMTK) component.getAttributes().get("concept");

        RelationshipDefinition relationshipDefinition = (RelationshipDefinition) component.getAttributes().get("relationshipDefinition");

        if(concept == null || relationshipDefinition == null)
            return;

        if(concept.getValidRelationshipsByRelationDefinition(relationshipDefinition).size()<relationshipDefinition.getMultiplicity().getLowerBoundary()){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

    }

    public String getUiState() {
        return uiState;
    }

    public void setUiState(String uiState) {
        this.uiState = uiState;
    }
}
