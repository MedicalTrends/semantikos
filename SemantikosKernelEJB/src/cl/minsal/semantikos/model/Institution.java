package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrés Farías on 9/20/16.
 */
public class Institution extends PersistentEntity {

    /** BR-RefSet-003: Una Institución puede tener cero o más Usuarios Administradores de RefSets. */
    private List<User> administrators;

    public Institution() {
        this.administrators = new ArrayList<>();
    }

    public List<User> getAdministrators() {
        return administrators;
    }
}
