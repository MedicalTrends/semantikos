package cl.minsal.semantikos.model;

import java.util.List;

/**
 * @author Andrés Farías
 */
public interface IUser {

    public long getIdUser();

    String getUsername();

    String getName();

    String getEmail();

    List<Profile> getProfiles();

    List<Group> getGroups();

    List<Permission> getPermissions();

    String getLastName();

    String getSecondLastName();

    String getPassword();

    String getPasswordHash();

    String getPasswordSalt();

    boolean addProfile(Profile aProfile);

    /**
     * Este método es responsable de determinar si el usuario es un usuario nulo o no.
     * Este concepto es requerido solo por razones programáticas.
     *
     * @return <code>true</code> si el usuario es un tipo concreto y <code>false</code> si es un usuario nulo.
     */
    boolean isNullUser();
}
