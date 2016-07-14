package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
public class User {

    String username;
    String name;
    String email;

    List<Profile> profiles;
    List<Permission> permissions;
    List<Group> groups;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Profile> getProfiles() {
        if(profiles==null)
            profiles= new ArrayList<Profile>();
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Group> getGroups() {
        if(groups==null)
            groups= new ArrayList<Group>();
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }


    public List<Permission> getPermissions() {
        if(permissions==null)
            permissions= new ArrayList<Permission>();
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
