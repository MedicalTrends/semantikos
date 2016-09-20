package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.RefSet;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

import static cl.minsal.semantikos.model.ProfileFactory.ADMINISTRATOR_REFSETS_PROFILE;

/**
 * @author Andrés Farías on 9/20/16.
 */
public class RefSetUpdateBR {

    public void validatePreConditions(RefSet refSet, User user) {
        brRefSet001(user);
        brRefSet002(refSet, user);
    }


    /**
     * BR-RefSet-001: Los RefSets son administrados por usuarios que tienen perfil de Administrador de RefSets.
     *
     * @param user El usuario que realiza la operación.
     */
    public void brRefSet001(User user) {
        if (user.getProfiles().equals(ADMINISTRATOR_REFSETS_PROFILE)) {
            throw new BusinessRuleException("Los RefSets puede ser actualizados sólo por usuario con el perfil " + ADMINISTRATOR_REFSETS_PROFILE);
        }
    }

    /**
     * BR-RefSet-002: sólo pueden modificarlos los Administradores que están asociados a dicha Institución.
     *
     * @param refSet El RefSet que se desea modificar.
     * @param user   El usuario que realiza la operación.
     */
    public void brRefSet002(RefSet refSet, User user) {

    }
}
