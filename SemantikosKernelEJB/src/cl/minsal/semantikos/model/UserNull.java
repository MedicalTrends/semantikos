package cl.minsal.semantikos.model;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Andrés Farías
 */
public class UserNull implements IUser {

    private static final long NULL_ID = -1;

    /** The singleton instance */
    private static final IUser instance = new UserNull();

    private UserNull() {
    }

    public static IUser getInstance() {
        return instance;
    }

    @Override
    public long getIdUser() {
        return NULL_ID;
    }

    @Override
    public String getUsername() {
        return "null";
    }

    @Override
    public String getName() {
        return "Null";
    }

    @Override
    public String getEmail() {
        return "null_user@minsal.cl";
    }

    @Override
    public List<Profile> getProfiles() {
        return emptyList();
    }

    @Override
    public List<Group> getGroups() {
        return emptyList();
    }

    @Override
    public List<Permission> getPermissions() {
        return emptyList();
    }

    @Override
    public String getLastName() {
        return "User";
    }

    @Override
    public String getSecondLastName() {
        return "Null";
    }

    @Override
    public String getPassword() {
        return "nulnulnul";
    }

    @Override
    public String getPasswordHash() {
        return "nulnulnul";
    }

    @Override
    public String getPasswordSalt() {
        return "nulnulnul";
    }

    @Override
    public boolean addProfile(Profile aProfile) {
        return false;
    }

    @Override
    public boolean isNullUser() {
        return true;
    }
}
