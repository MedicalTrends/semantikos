package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.Multiplicity;

import javax.validation.constraints.NotNull;

/**
 * @author Andrés Farías on 10/5/16.
 */
public class RelationshipDefinitionWeb extends RelationshipDefinition implements Comparable<RelationshipDefinitionWeb> {

    /** El identificador del composite que se quiere usar en las vistas */
    private long compositeID;

    /** Establece el orden o posición */
    private int order;

    /** Establece el estilo para el estado de error */
    private String uiState = "";

    public RelationshipDefinitionWeb(long id, String name, String description, TargetDefinition targetDefinition, Multiplicity multiplicity, long compositeID, int order) {
        super(id, name, description, targetDefinition, multiplicity);

        this.compositeID = compositeID;
        this.order = order;
    }

    public long getCompositeID() {
        return compositeID;
    }

    public int getOrder() {
        return order;
    }

    public String getUiState() {
        return uiState;
    }

    public void setUiState(String uiState) {
        this.uiState = uiState;
    }

    @Override
    public int compareTo(@NotNull RelationshipDefinitionWeb relationshipDefinitionWeb) {
        return this.order - relationshipDefinitionWeb.order;
    }
}
