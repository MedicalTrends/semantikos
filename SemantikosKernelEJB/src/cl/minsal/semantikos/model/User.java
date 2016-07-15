package cl.minsal.semantikos.model;

/**
 * Created by root on 08-07-16.
 */
public class User {
    private long idUser;
    private String nameUser;
    private String token;

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (idUser != that.idUser) return false;
        if (nameUser != null ? !nameUser.equals(that.nameUser) : that.nameUser != null)
            return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idUser ^ (idUser >>> 32));
        result = 31 * result + (nameUser != null ? nameUser.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
