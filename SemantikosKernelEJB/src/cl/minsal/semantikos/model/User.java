package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Francisco Mendez
 */
public class User implements IUser {

    long idUser;
    String username;
    String name;
    String lastName;
    String secondLastName;
    String email;

    String password;
    String passwordHash;
    String passwordSalt;

    List<Profile> profiles;
    List<Permission> permissions;
    List<Group> groups;

    /**
     * Constructor base para inicializar los objetos que lo requieren.
     */
    public User() {
        this.profiles = new ArrayList<>();
        this.permissions = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    @Override
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public List<Profile> getProfiles() {
        if (profiles == null)
            profiles = new ArrayList<Profile>();
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public List<Group> getGroups() {
        if (groups == null)
            groups = new ArrayList<Group>();
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }


    @Override
    public List<Permission> getPermissions() {
        if (permissions == null)
            permissions = new ArrayList<Permission>();
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof IUser) && (String.valueOf(idUser) != null)
                ? String.valueOf(idUser).equals(String.valueOf(((User) other).idUser))
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(idUser) != null)
                ? (this.getClass().hashCode() + String.valueOf(idUser).hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        //return String.format("ExampleEntity[%d, %s]", idDescriptionType, glosa);
        return getUsername();
    }

    /**
     * Este método es responsable de agregar un perfil al usuario. No es buena práctica devolver el objeto de la
     * estructura interna para hacerlo directamente.
     *
     * @return <code>true</code> si se agregó el perfile y <code>false</code> sino.
     */
    @Override
    public boolean addProfile(Profile aProfile) {
        return this.profiles.add(aProfile);
    }

    @Override
    public boolean isNullUser() {
        return false;
    }
}
