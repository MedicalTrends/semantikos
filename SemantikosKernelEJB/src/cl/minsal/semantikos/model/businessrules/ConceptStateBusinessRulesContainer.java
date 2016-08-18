package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.kernel.components.RelationshipManager;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.relationships.Relationship;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Singleton
public class ConceptStateBusinessRulesContainer implements BusinessRulesContainer {

    @EJB
    private RelationshipManager relationshipManager;

    @Override
    public void apply(ConceptSMTK conceptSMTK, IUser IUser) throws BusinessRuleException {

        if (conceptSMTK.isFullyDefined()){
            validateIsFullyDefined(conceptSMTK);
        }
    }

    protected void validateIsFullyDefined(ConceptSMTK conceptSMTK) throws BusinessRuleException {

        /* Se recuperan todas las relaciones con RelationshipType que tengan destino un concepto SNOMED CT. */
        List<Relationship> sctRelationships = null; //FIXME: conceptSMTK.getRelationshipsByTargetDefinition(TargetDefinitionType.SCT);
        for (Relationship sctRelationship : sctRelationships) {
        // relationshipDAO.getRelationships()

        }
        // TODO: Terminar esto.
    }
}
