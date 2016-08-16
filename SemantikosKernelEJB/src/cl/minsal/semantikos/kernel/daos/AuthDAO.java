package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 01-07-16.
 */

@Stateless
public class AuthDAO {


    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;

    public IUser getUserById(int id) {

        Query q = em.createNativeQuery("Select * from semantikos.user u where u.user_id = ? ");

        q.setParameter(1,id);

        IUser user = makeUserFromResult((Object[]) q.getSingleResult());

                return user;
    }

    private IUser makeUserFromResult(Object[] row) {

        User u = new User();

        u.setIdUser(((BigInteger)row[0]).longValue() );
        u.setUsername((String)row[1]);
        u.setPasswordHash((String)row[2]);
        u.setPasswordSalt((String)row[3]);
        u.setName((String)row[4]);
        u.setLastName((String)row[5]);
        u.setSecondLastName((String)row[6]);
        u.setEmail((String)row[7]);


        return u;

    }


    public void createUser(IUser u) throws UserExistsException{

        em.createNativeQuery("insert into semantikos.user ");


    }

    public List<IUser> getAllUsers() {

        ArrayList<IUser> users = new ArrayList<>();

        Query q = em.createNativeQuery("Select * from semantikos.smtk_user");

        for (Object row : q.getResultList()) {
            IUser user = makeUserFromResult((Object[]) row);
            users.add(user);
        }



        return users;

    }


    private class UserExistsException extends Exception {
    }
}
