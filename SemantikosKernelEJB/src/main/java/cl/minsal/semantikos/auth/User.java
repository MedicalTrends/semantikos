package cl.minsal.semantikos.auth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
public class User {

    String username;
    String name;
    String email;

    List<String> roles;
    List<String> groups;


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

    public List<String> getRoles() {
        if(roles==null)
            roles= new ArrayList<String>();
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGroups() {
        if(groups==null)
            groups= new ArrayList<String>();
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
