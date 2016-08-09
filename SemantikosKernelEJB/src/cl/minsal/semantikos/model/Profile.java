package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
public class Profile {

    long idProfile;
    String name;
    String description;

    List<Permission> permissions;

    public long getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(long idProfile) {
        this.idProfile = idProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        return idProfile == profile.idProfile;

    }

    @Override
    public int hashCode() {
        return (int) (idProfile ^ (idProfile >>> 32));
    }
}
