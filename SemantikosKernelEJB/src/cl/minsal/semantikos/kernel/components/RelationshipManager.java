package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.User;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.snomedct.ConceptSCT;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Andres Farias
 * @version 1.0
 * @created 17-ago-2016 12:23:53
 */
@Local
public interface RelationshipManager {

    /**
     * Este método es responsable de crear una relación de cualquier tipo, respetando
     * los tipos de orígenes posibles y tipos de destino.
     *
     * @param origin       El origen de la relación, que puede ser un concepto o una relación.
     * @param target       El destino de la relación
     * @param relationType El tipo de relación que se quiere crear.
     * @param isValid      Determina si es vigente o no.
     * @param user         El usuario que crea la Relación.
     */
    public Relationship createRelationship(ConceptSMTK origin, Target target, RelationshipDefinition relationType, boolean isValid, User user);

    /**
     * Este método es responsable de buscar una relación, dadas su categoría de origen
     * y destino
     *
     * @param sourceCategory  La categoría a la que pertenecen los conceptos origen.
     * @param destinyCategory Este método busca todas aquellas relaciones que
     *                        tienen como origen un Concepto que pertenece a la categoría origen especificada,
     *                        y como destino un concepto que pertenece a la categoría destino especificada.
     */
    public Relationship[] findRelationByCategories(Category sourceCategory, Category destinyCategory);

    /**
     * Este método es responsable de recuperar todas las relaciones que tienen como
     * origen un concepto dado.
     *
     * @param id El identificador único (BDD) del concepto SMTK.
     */
    public Relationship[] findRelationsByOriginConcept(long id);

    /**
     * Este método es responsable de recuperar todas las relaciones que tienen como
     * destino un concepto Snomed CT dado.
     *
     * @param conceptSCT El identificador único (BDD) del concepto CST.
     */
    public List<Relationship> findRelationsByTargetSCTConcept(ConceptSCT conceptSCT);

    /**
     * Este método es responsable de actualizar el valor de una relación, actualizando
     * su destino. El destino debe ser del mismo tipo que el valor original.
     *
     * @param idRelationship          Identificador de la relación cuyo valor de destino va
     *                                a cambiar.
     * @param idRelationshipAttribute El atributo (relación) de la relación que se
     *                                desea modificar.
     */
    public int updateRelationAttribute(int idRelationship, int idRelationshipAttribute);

    /**
     * Este método es responsable de actualizar una relación de un concepto.
     *
     * @param conceptSMTK          El concepto cuya relación se desea actualizar.
     * @param originalRelationship La relación original, antes de la actualización.
     * @param editedRelationship   La relación actualizada.
     * @param user                 El usuario que realiza la operación.
     */
    void updateRelationship(@NotNull ConceptSMTK conceptSMTK, @NotNull Relationship originalRelationship, @NotNull Relationship editedRelationship, @NotNull User user);

    /**
     * Este método es responsable de dejar no vigente la relación a partir de este momento.
     *
     * @param relationship Relación que se invalida.
     */
    public Relationship invalidate(Relationship relationship);

    /**
     * Este método es responsable de recuperar todas las relaciones de un cierto tipo (es un/es una, por ejemplo) y que
     * referencian un cierto destino <code>target</code>.
     *
     * @param relationshipDefinition El tipo de relación que se desea recuperar.
     * @param target                 El destino al cual deben apuntar las relaciones deseadas.
     *
     * @return Una lista de relaciones del tipo indicado que apuntan al <code>target</code> indicado.
     */
    public List<Relationship> getRelationshipsLike(RelationshipDefinition relationshipDefinition, Target target);

    /**
     * Este método es responsable de recuperar todas las relaciones del <code>concepto</code>.
     *
     * @param concept El concepto cuyas relaciones son recuperadas.
     *
     * @return Una lista de relaciones asociadas al concepto.
     */
    List<Relationship> getRelationshipsBySourceConcept(ConceptSMTK concept);
}