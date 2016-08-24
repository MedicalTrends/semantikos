package cl.minsal.semantikos.model.audit;

import cl.minsal.semantikos.model.ConceptSMTK;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class ConceptAuditAction {

    /** The concept subject of the change */
    private ConceptSMTK subjectConcept;

    /** The kind of change happened */
    private AuditActionType auditActionType;
}
