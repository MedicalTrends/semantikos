package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Profile;
import cl.minsal.semantikos.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Francisco Mendez on 01-07-16.
 */

@Stateless
public class AuthDAO {


    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    public User getUserById(long id) {

        Query q = em.createNativeQuery("SELECT * FROM semantikos.smtk_user u WHERE u.id = ? ");

        q.setParameter(1, id);


        Object sresult = q.getSingleResult();

        if (sresult == null)
            return null;

        User user = makeUserFromResult((Object[]) sresult);

        user.setProfiles(getUserProfiles(id));

        return user;
    }


    public User getUserByUsername(String username) {


        Query q = em.createNativeQuery("SELECT * FROM semantikos.smtk_user u WHERE u.username = ? ");

        q.setParameter(1, username);


        Object sresult = q.getSingleResult();

        if (sresult == null)
            return null;

        User user = makeUserFromResult((Object[]) sresult);

        user.setProfiles(getUserProfiles(user.getIdUser()));

        return user;
    }


    public List<Profile> getUserProfiles(Long userId) {

        Query q = em.createNativeQuery("SELECT p.* FROM semantikos.smtk_profile p, semantikos.smtk_user_profile up WHERE up.id_user = ? AND up.id_profile = p.id ");

        q.setParameter(1, userId);

        List<Profile> profiles = new ArrayList<Profile>();

        for (Object row : q.getResultList()) {
            Profile profile = makeProfileFromResult((Object[]) row);
            profiles.add(profile);
        }


        return profiles;

    }

    /**
     * Este método es responsable de crear un Profile a partir de una fila de un resultset.
     *
     * @param row La fila del resultset
     *
     * @return El Profile creado a partir de la fila.
     */
    private Profile makeProfileFromResult(Object[] row) {

        long idProfile = ((BigInteger) row[0]).longValue();
        String name = (String) row[1];
        String description = (String) row[2];

        return new Profile(idProfile, name, description);
    }


    private User makeUserFromResult(Object[] row) {

        User u = new User();

        u.setIdUser(((BigInteger) row[0]).longValue());
        u.setUsername((String) row[1]);
        u.setPasswordHash((String) row[2]);
        u.setPasswordSalt((String) row[3]);
        u.setName((String) row[4]);
        u.setLastName((String) row[5]);
        u.setSecondLastName((String) row[6]);
        u.setEmail((String) row[7]);

        u.setLocked((Boolean) row[8]);
        u.setFailedLoginAttempts((Integer) row[9]);

        u.setLastLogin((Timestamp) row[10]);
        u.setLastPasswordChange((Timestamp) row[11]);

        u.setLastPasswordHash1((String) row[12]);
        u.setLastPasswordHash2((String) row[13]);
        u.setLastPasswordHash3((String) row[14]);
        u.setLastPasswordHash4((String) row[15]);

        u.setLastPasswordSalt1((String) row[16]);
        u.setLastPasswordSalt2((String) row[17]);
        u.setLastPasswordSalt3((String) row[18]);
        u.setLastPasswordSalt4((String) row[19]);

        u.setRut((String) row[20]);

        return u;
    }


    public List<User> getAllUsers() {

        ArrayList<User> users = new ArrayList<>();

        Query q = em.createNativeQuery("SELECT * FROM semantikos.smtk_user");

        for (Object row : q.getResultList()) {
            User user = makeUserFromResult((Object[]) row);
            users.add(user);
        }


        return users;

    }

    public void createUser(User user) throws UserExistsException {

        try {
            if (getUserByUsername(user.getUsername()) != null)
                throw new UserExistsException();
        } catch (NoResultException e) {
            //es lo esperado, no hacer nada
        }


        Query q = em.createNativeQuery("INSERT INTO semantikos.smtk_user (id, username, name, last_name, second_last_name, email, locked, failed_login_attempts, rut) " +
                "                                                 VALUES (DEFAULT, ?  , ?   , ?        , ?               , ?    , ?     , ?                    , ? )");


        q.setParameter(1, user.getUsername());
        q.setParameter(2, user.getName());
        q.setParameter(3, user.getLastName());
        q.setParameter(4, user.getSecondLastName());
        q.setParameter(5, user.getEmail());
        q.setParameter(6, false);
        q.setParameter(7, 0);
        q.setParameter(8, user.getRut());

        q.executeUpdate();

    }


    public void updateUser(User user) {

        Query q = em.createNativeQuery("UPDATE semantikos.smtk_user " +
                "SET name = ?, " +
                "last_name = ?, " +
                "second_last_name = ?, " +
                "email = ?, " +
                "rut= ?  " +
                "WHERE id = ?");

        q.setParameter(1, user.getName());
        q.setParameter(2, user.getLastName());
        q.setParameter(3, user.getSecondLastName());
        q.setParameter(4, user.getEmail());
        q.setParameter(5, user.getRut());
        q.setParameter(6, user.getIdUser());

        q.executeUpdate();


        Query q2 = em.createNativeQuery("DELETE FROM semantikos.smtk_user_profile WHERE id_user = ?");
        q2.setParameter(1, user.getIdUser());
        q2.executeUpdate();


        for (Profile p : user.getProfiles()) {

            addProfileToUser(user, p);

        }


    }

    private void addProfileToUser(User user, Profile p) {
        Query q = em.createNativeQuery("INSERT INTO semantikos.smtk_user_profile (id_user, id_profile) VALUES (?, ?)");

        q.setParameter(1, user.getIdUser());
        q.setParameter(2, p.getId());

        q.executeUpdate();
    }

    public List<Profile> getAllProfiles() {
        ArrayList<Profile> profiles = new ArrayList<>();

        Query q = em.createNativeQuery("SELECT * FROM semantikos.smtk_profile");

        for (Object row : q.getResultList()) {
            Profile profile = makeProfileFromResult((Object[]) row);
            profiles.add(profile);
        }


        return profiles;
    }

    public void updateUserPasswords(User user) {


        Query q = em.createNativeQuery("UPDATE semantikos.smtk_user " +
                "SET last_password_change = ?, " +
                "password_hash = ?, " +
                "last_password_hash1 = ?, " +
                "last_password_hash2 = ?, " +
                "last_password_hash3 = ?, " +
                "last_password_hash4 = ?, " +
                "password_salt = ?, " +
                "last_password_salt1 = ?, " +
                "last_password_salt2 = ?, " +
                "last_password_salt3 = ?, " +
                "last_password_salt4 = ? " +
                "WHERE id = ?");

        q.setParameter(1, user.getLastPasswordChange());
        q.setParameter(2, user.getPasswordHash());
        q.setParameter(3, user.getLastPasswordHash1());
        q.setParameter(4, user.getLastPasswordHash2());
        q.setParameter(5, user.getLastPasswordHash3());
        q.setParameter(6, user.getLastPasswordHash4());
        q.setParameter(7, user.getPasswordSalt());
        q.setParameter(8, user.getLastPasswordSalt1());
        q.setParameter(9, user.getLastPasswordSalt2());
        q.setParameter(10, user.getLastPasswordSalt3());
        q.setParameter(11, user.getLastPasswordSalt4());
        q.setParameter(12, user.getIdUser());

        q.executeUpdate();

    }


    /* marca la ultima fecha de ingreso del usuario */
    public void markLogin(String username) {

        Query q = em.createNativeQuery("UPDATE semantikos.smtk_user " +
                "SET last_login = ?, failed_login_attempts =0 " +
                "WHERE username = ?");

        q.setParameter(1, new Date());
        q.setParameter(2, username);

        q.executeUpdate();

    }

    public void markLoginFail(String username) {
        Query q = em.createNativeQuery("UPDATE semantikos.smtk_user " +
                "SET failed_login_attempts = failed_login_attempts+1 " +
                "WHERE username = ?");


        q.setParameter(1, username);

        q.executeUpdate();
    }

    public void lockUser(String username) {
        Query q = em.createNativeQuery("UPDATE semantikos.smtk_user " +
                "SET locked = TRUE " +
                "WHERE username = ?");


        q.setParameter(1, username);

        q.executeUpdate();
    }

    public Profile getProfile(long id) {
        Query q = em.createNativeQuery("SELECT * FROM semantikos.smtk_profile p WHERE p.id = ? ");

        q.setParameter(1, id);

        Object sresult = q.getSingleResult();

        if (sresult == null)
            return null;

        Profile profile = makeProfileFromResult((Object[]) sresult);


        return profile;
    }

    public void unlockUser(String username) {
        Query q = em.createNativeQuery("UPDATE semantikos.smtk_user " +
                "SET locked = FALSE " +
                "WHERE username = ?");


        q.setParameter(1, username);

        q.executeUpdate();
    }


    public class UserExistsException extends Exception {
    }
}
