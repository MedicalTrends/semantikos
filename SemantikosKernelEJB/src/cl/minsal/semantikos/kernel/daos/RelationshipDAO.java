package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;
import cl.minsal.semantikos.model.relationships.Relationship;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías
 */
@Local
public interface RelationshipDAO {

    /**
     * Este método es responsable de persistir una relación asociada a un concepto.
     *
     * @param relationship La relación que se desea persistir.
     */
    public void persist(Relationship relationship);

    /**
     * Este método es responsable de obtener todas las relaciones que tienen como target un concepto SNOMED CT en
     * particular.
     *
     * @param conceptSCT El concepto SNOMED al cual apuntan las relaciones que se desea buscar.
     *
     * @return Todas las relaciones que tienen como destino el concepto SCT de identificador <code>id</code>
     */
    List<Relationship> getRelationshipsToCSTConcept(ConceptSCT conceptSCT);

    /**
     * Este método es responsable de obtener la relación con id <code>idRelationship</code> desde la BD.
     *
     * @param idRelationship El Identificador único en la base de datos.
     *
     * @return Un relación fresca creada desde la base de datos.
     */
    Relationship getRelationshipByID(long idRelationship);

    public List<Relationship> getRelationshipsLike(RelationshipDefinition relationshipDefinition, Target target);
}
