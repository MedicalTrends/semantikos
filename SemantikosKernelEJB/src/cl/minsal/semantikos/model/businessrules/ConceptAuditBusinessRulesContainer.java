package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class ConceptAuditBusinessRulesContainer implements BusinessRulesContainer {

    public void apply(ConceptSMTK conceptSMTK, IUser IUser) throws BusinessRuleException {

        /* Se valida primero que el concepto cumpla las condiciones para ser auditado */
        brAud001isModeled(conceptSMTK);
    }

    /**
     * ﻿BR-AUD-001 Solo auditar Conceptos Modelados
     *
     * @param conceptSMTK El concepto cuyo estado se desea revisar.
     *
     * @throws BusinessRuleException Si el concepto no se encuentra <em>modelado</em>.
     */
    private void brAud001isModeled(ConceptSMTK conceptSMTK) throws BusinessRuleException {

        if (!conceptSMTK.isModeled()) {
            throw new BusinessRuleException(conceptSMTK, "El concepto no se encuentra en estado Modelado.");
        }
    }
}
