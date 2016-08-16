package cl.minsal.semantikos.kernel.daos;

import javax.ejb.Local;

/**
 * Created by des01c7 on 16-08-16.
 */
@Local
public interface RelationshipDAO {
    public long createRelationship(long idSource, long idTarget, long idRelationshipDefinition);
}
