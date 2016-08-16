package cl.minsal.semantikos.kernel.daos;

/**
 * Created by des01c7 on 16-08-16.
 */
public interface RelationshipAttributeDAO {
    public long  createRelationshipAttribute(long idRelationshipAttributeDefinition, long idRelationship, long idDestiny);
}
