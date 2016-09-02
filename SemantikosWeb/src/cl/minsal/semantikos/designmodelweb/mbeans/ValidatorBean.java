package cl.minsal.semantikos.designmodelweb.mbeans;

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

        String msg = "Error!!!!!!";

        List<Relationship> relationships = (List<Relationship>) component.getAttributes().get("relationships");

        RelationshipDefinition relationshipDefinition = (RelationshipDefinition) component.getAttributes().get("relationshipDefinition");

        if (relationships.size() < relationshipDefinition.getMultiplicity().getLowerBoundary()) {
            setUiState("ui-state-error");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        else
            setUiState("");
    }

    public String getUiState() {
        return uiState;
    }

    public void setUiState(String uiState) {
        this.uiState = uiState;
    }
}
