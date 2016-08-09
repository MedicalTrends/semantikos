package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
public class User {

    private long idUser;
    private String username;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;

    private String rut;

    private String password;
    private String passwordHash;
    private String passwordSalt;

    private List<Profile> profiles;
    private List<Permission> permissions;
    private List<Group> groups;

    private Date lastLogin;
    private Date lastPasswordChange;
    private boolean locked;
    private int failedLoginAttempts;

    private String lastPasswordHash1;
    private String lastPasswordSalt1;
    private String lastPasswordHash2;
    private String lastPasswordSalt2;
    private String lastPasswordHash3;
    private String lastPasswordSalt3;
    private String lastPasswordHash4;
    private String lastPasswordSalt4;




    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public String getLastPasswordHash1() {
        return lastPasswordHash1;
    }

    public void setLastPasswordHash1(String lastPasswordHash1) {
        this.lastPasswordHash1 = lastPasswordHash1;
    }

    public String getLastPasswordSalt1() {
        return lastPasswordSalt1;
    }

    public void setLastPasswordSalt1(String lastPasswordSalt1) {
        this.lastPasswordSalt1 = lastPasswordSalt1;
    }

    public String getLastPasswordHash2() {
        return lastPasswordHash2;
    }

    public void setLastPasswordHash2(String lastPasswordHash2) {
        this.lastPasswordHash2 = lastPasswordHash2;
    }

    public String getLastPasswordSalt2() {
        return lastPasswordSalt2;
    }

    public void setLastPasswordSalt2(String lastPasswordSalt2) {
        this.lastPasswordSalt2 = lastPasswordSalt2;
    }

    public String getLastPasswordHash3() {
        return lastPasswordHash3;
    }

    public void setLastPasswordHash3(String lastPasswordHash3) {
        this.lastPasswordHash3 = lastPasswordHash3;
    }

    public String getLastPasswordSalt3() {
        return lastPasswordSalt3;
    }

    public void setLastPasswordSalt3(String lastPasswordSalt3) {
        this.lastPasswordSalt3 = lastPasswordSalt3;
    }

    public String getLastPasswordHash4() {
        return lastPasswordHash4;
    }

    public void setLastPasswordHash4(String lastPasswordHash4) {
        this.lastPasswordHash4 = lastPasswordHash4;
    }

    public String getLastPasswordSalt4() {
        return lastPasswordSalt4;
    }

    public void setLastPasswordSalt4(String lastPasswordSalt4) {
        this.lastPasswordSalt4 = lastPasswordSalt4;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && ( String.valueOf(idUser) != null )
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
}
