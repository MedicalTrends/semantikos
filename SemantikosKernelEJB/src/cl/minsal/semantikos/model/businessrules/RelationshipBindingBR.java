package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.relationships.Relationship;

import javax.validation.constraints.NotNull;

import static cl.minsal.semantikos.model.ProfileFactory.MODELER_PROFILE;

/**
 * @author Andrés Farías on 9/8/16.
 */
public class RelationshipBindingBR {

    public void verifyPreConditions(ConceptSMTK concept, Relationship relationship, User user) {
        brConceptCreation001(relationship, user);
    }

    /**
     * Este método es responsable de implementar la regla de negocio BR-CON-004.
     * ﻿BR-CON-004 Solo un usuario con rol Modelador puede realizar una relación Snomed CT.
     *
     * @param relationship La relación en cuestión.
     * @param user         El usuario que realiza la asociación.
     */
    private void brConceptCreation001(Relationship relationship, User user) {
        if (relationship.getRelationshipDefinition().getTargetDefinition().isSnomedCTType() && !user.getProfiles().contains(MODELER_PROFILE)) {
            throw new BusinessRuleException("Solo el usuario con rol Modelador puede agregar relaciones de tipo Snomed CT.");
        }
    }

    /**
     * Este método gatilla todas las acciones relacionadas con la asociación de relaciones.
     *
     * @param relationship La relación que se asoció.
     * @param conceptDAO   El DAO para realizar las accioens.
     */
    public void postActions(Relationship relationship, @NotNull ConceptDAO conceptDAO) {
        postActionRelationshipBinding001(relationship, conceptDAO);
    }

    /**
     * <p>Este método implementa la post-acción definida por la regla de negocio BR-CON-003.</p>
     * ﻿<p>BR-CON-003: Concepto pasa de Borrador a Modelado cuando se mapea a SnomedCT.</p>
     *
     * @param relationship La relación que se agregó al concepto.
     * @param conceptDAO   El DAO para actualizar el estado.
     */
    private void postActionRelationshipBinding001(Relationship relationship, ConceptDAO conceptDAO) {

        ConceptSMTK sourceConcept = relationship.getSourceConcept();
        boolean isSnomedCTType = relationship.getRelationshipDefinition().getTargetDefinition().isSnomedCTType();

        if (isSnomedCTType && !sourceConcept.isModeled()) {
            sourceConcept.setModeled(true);
            conceptDAO.update(sourceConcept);
        }
    }
}
