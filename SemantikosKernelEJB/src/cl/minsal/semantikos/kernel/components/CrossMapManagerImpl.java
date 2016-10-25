package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.relationships.CrossMap;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.businessrules.CrossMapCreationBR;
import cl.minsal.semantikos.model.businessrules.CrossMapRemovalBR;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Andrés Farías on 8/30/16.
 */
@Stateless
public class CrossMapManagerImpl implements CrossMapManagerInterface {

    @EJB
    private AuditManager auditManager;

    @Override
    public CrossMap createCrossMap(CrossMap crossMap, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapCreationBR().applyRules(crossMap, user);

        /* TODO: Se realiza la creación */

        /* Se registra en el historial */
        if (crossMap.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapCreation(crossMap, user);
        }

        /* Se retorna la instancia creada */
        return crossMap;
    }

    @Override
    public CrossMap removeCrossMap(CrossMap crossMap, User user) {

        /* Se aplican las reglas de negocio */
        new CrossMapRemovalBR().applyRules(crossMap, user);

        /* TODO: Se realiza la eliminación */

        /* Se registra en el historial */
        if (crossMap.getSourceConcept().isModeled()) {
            auditManager.recordCrossMapRemoval(crossMap, user);
        }

        /* Se retorna la instancia creada */
        return crossMap;
    }
}
