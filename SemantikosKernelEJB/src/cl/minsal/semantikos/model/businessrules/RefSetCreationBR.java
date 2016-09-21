package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.model.Profile;
import cl.minsal.semantikos.model.ProfileFactory;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;

/**
 * @author Andrés Farías on 9/20/16.
 */
public class RefSetCreationBR {

    public void validatePreConditions(User user) {
        brRefSet01(user);
    }

    /**
     * BR-RefSet-001: Sólo usuarios con el perfil Administrador de RefSets pueden crear RefSets.
     *
     * @param user El usuario que realiza la creación.
     */
    private void brRefSet01(User user) {
        Profile currentProfile = user.getCurrentProfile();
        if (!currentProfile.equals(ProfileFactory.ADMINISTRATOR_REFSETS_PROFILE)) {
            throw new BusinessRuleException("El RefSet no puede ser creado por un usuario con el perfil " + currentProfile);
        }
    }
}
