package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static cl.minsal.semantikos.model.helpertables.HelperTable.SYSTEM_COLUMN_DESCRIPTION;

/**
 * Esta clase representa una relación hacia un concepto Snomed-CT.
 *
 * @author Andrés Farías created on 9/27/16.
 */
public class SnomedCTRelationship extends Relationship {

    public static final String ES_UN_MAPEO_DE = "es un mapeo de";
    public static final String ES_UN = "es un";

    public SnomedCTRelationship(ConceptSMTK sourceConcept, ConceptSCT conceptSCT, RelationshipDefinition relationshipDefinition, List<RelationshipAttribute> relationshipAttributes) {
        super(sourceConcept, conceptSCT, relationshipDefinition, relationshipAttributes);
    }

    public SnomedCTRelationship(@NotNull long id, @NotNull ConceptSMTK sourceConcept, @NotNull ConceptSCT conceptSCT, @NotNull RelationshipDefinition relationshipDefinition, Timestamp validityUntil, List<RelationshipAttribute> relationshipAttributes) {
        super(id, sourceConcept, conceptSCT, relationshipDefinition, validityUntil, relationshipAttributes);
    }

    /**
     * Este método, muy importante en el modelo de negocio, permite determinar si <em>esta</em> relación es definitoria
     * (<em>definitional</em> en inglés) o de atributo.
     * <p> La definición qu ese ha considerado consiste en que la relación tiene un atributo a una tabla auxiliar
     * llamada TIPO_RELACION, con un valor particular: "es un" o "es un mapeo de".</p>
     *
     * @return <code>true</code> si es definitoria, y <code>false</code> sino.
     */
    public boolean isDefinitional() {

        /* Se obtienen los atributos de la relación y se itera por ellos para buscar las del tipo que sirve*/
        List<RelationshipAttribute> relationshipAttributes = this.getRelationshipAttributes();
        for (RelationshipAttribute relationshipAttribute : relationshipAttributes) {

            /* El atributo debe ser de tipo Helper Table (en particular a la tabla de tipos de relaciones */
            if (relationshipAttribute.getRelationAttributeDefinition().getTargetDefinition().isHelperTable()) {
                HelperTableRecord snomedType = (HelperTableRecord) relationshipAttribute.getTarget();

                /* Sin preguntar si es la tabla correcta, se ve si su campo descripción tienen los valores requeridos */
                // TODO: Validar que el target es a la tabla correcta.
                Map<String, String> fields = snomedType.getFields();
                if (fields.containsKey(SYSTEM_COLUMN_DESCRIPTION)) {
                    return fields.get(SYSTEM_COLUMN_DESCRIPTION).equalsIgnoreCase(ES_UN)
                            || fields.get(SYSTEM_COLUMN_DESCRIPTION).equalsIgnoreCase(ES_UN_MAPEO_DE);
                }
            }
        }

        /* En cualquier otro caso de los considerados antes, no es definitoria */
        return false;
    }

    @Override
    public ConceptSCT getTarget() {
        return (ConceptSCT) super.getTarget();
    }
}
