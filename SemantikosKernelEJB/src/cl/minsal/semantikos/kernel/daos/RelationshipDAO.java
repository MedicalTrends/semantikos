package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.relationships.Relationship;

import javax.ejb.Local;

/**
 * @author Andrés Farías
 */
@Local
public interface RelationshipDAO {

    /**
     * Este método es responsable de persistir una relación asociada a un concepto.
     * @param relationship La relación que se desea persistir.
     */
    public void persist(Relationship relationship);
}
