package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.kernel.components.RelationshipManager;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.relationships.Relationship;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.List;

import static cl.minsal.semantikos.model.relationships.TargetType.SnomedCT;

/**
 * @author Andrés Farías
 */
@Singleton
public class ConceptStateBusinessRulesContainer implements BusinessRulesContainer {

    @EJB
    private RelationshipManager relationshipManager;

    @Override
    public void apply(ConceptSMTK conceptSMTK, IUser IUser) throws BusinessRuleException {

        if (conceptSMTK.isFullyDefined()) {
            validateIsFullyDefined(conceptSMTK);
        }
    }

    /**
     * Este método tienen como responsabilidad validar que el concepto está completamente definido. Esto significa que
     * no debe existir otro concepto que tenga las mismas relaciones a conceptos Snomed CT.
     *
     * @param conceptSMTK El concepto sobre el cual se quiere hacer la validación.
     *
     * @throws BusinessRuleException Se arroja si viola una o más reglas de negocio.
     */
    protected void validateIsFullyDefined(ConceptSMTK conceptSMTK) throws BusinessRuleException {

        /* Se recuperan todas las relaciones con RelationshipType que tengan destino un concepto SNOMED CT. */
        List<Relationship> sctRelationships = conceptSMTK.getRelationshipsTo(SnomedCT);

        /* Para cada relación a SCT, se verifica si existe un concepto SMTK que tenga las mismas relaciones */
        for (Relationship sctRelationship : sctRelationships) {

            /* Se recuperan todas las relaciones del mismo tipo de relación y que se dirigen al mismo concepto SCT */
            List<Relationship> relationshipsLike = relationshipManager.getRelationshipsLike(sctRelationship.getRelationshipDefinition(), sctRelationship.getTarget());
            for (Relationship relationshipCandidate : relationshipsLike) {
                ConceptSMTK candidateConcept = relationshipCandidate.getSourceConcept();

                /* Si el concepto candidato (el origen de la relación) posee las mismas relaciones SCT, entonces no es completamente definido */
                if (candidateConcept.contains(sctRelationships)){
                    throw new BusinessRuleException("El concepto no es completamente definido.");
                }
            }
        }
    }
}
