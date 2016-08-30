package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías on 8/30/16.
 */
public class HistoryRecordBL {

    /**
     * Este método aplica todas las reglas de negocio para validar si se puede realizar el registro.
     *
     * @param conceptSMTK El concepto sobre el cual se quiere registrar una acción en el historial.
     * @param user        El usuario que desea realizar la acción sobre el concepto.
     */
    public void validate(ConceptSMTK conceptSMTK, User user) {
        brAud001(conceptSMTK);
    }

    /**
     * BR-AUD-001: Cuando se realice una modificación de un Concepto Modelado, se guardará previamente en el sistema un
     * registro de la información actual del Concepto, para que sea mostrado en el listado de modificaciones del
     * Historial.
     *
     * @param conceptSMTK El concepto sobre el cual se realiza el registro en el historial.
     */
    private void brAud001(ConceptSMTK conceptSMTK) {
        if (conceptSMTK.isModeled())
            throw new BusinessRuleException("BT-AUD-001 No satisfecha: solo se pueden registrar acciones relativas a Conceptos que se encuentran Modelados");
    }
}
