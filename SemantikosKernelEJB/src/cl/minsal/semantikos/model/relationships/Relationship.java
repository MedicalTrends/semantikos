package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.kernel.daos.DAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.PersistentEntity;
import cl.minsal.semantikos.model.audit.AuditableEntity;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Una Relación es una asociación con significado entre 2 cosas.</p>
 * <p>
 * En el Modelo General de Terminologías los Conceptos pueden estar relacionados entre sí o con otras entidades,
 * entonces, una Relación es una asociación entre un Concepto Origen y un Objeto Destino.</p>
 * <p>Cada Relación agrega información sobre el Concepto Origen.</p>
 * Hay 2 Tipos de Relaciones:
 * •	Definitorias
 * •	De Atributos
 *
 * @author Andrés Farías
 */
public class Relationship extends PersistentEntity implements AuditableEntity {

    /** Identificador único de la base de datos */
    private long id;

    /** El concepto origen de esta relación */
    private ConceptSMTK sourceConcept;

    /** La definición de esta relación */
    private RelationshipDefinition relationshipDefinition;

    /** El elemento destino de esta relación */
    private Target target;

    /** La relación es Vigente (valida) hasta la fecha... */
    private Timestamp validityUntil;

    /** Indica si ha sufrido modificaciones que requieran un update */
    private boolean toBeUpdated = false;

    /** Lista de Atributos de la relacion **/
    private List<RelationshipAttribute> relationshipAttributes;

    /**
     * Este es el constructor mínimo con el cual se crean las Relaciones
     *
     * @param sourceConcept           El concepto origen de la relación.
     * @param target                  El valor de la relación (destino)
     * @param relationshipDefinition  Definición de la relación.
     * @param  relationshipAttributes Lista de Atributos
     */
    public Relationship(ConceptSMTK sourceConcept, Target target, RelationshipDefinition relationshipDefinition,List<RelationshipAttribute> relationshipAttributes) {

        /* No está persistido originalmente */
        this.id = NON_PERSISTED_ID;

        this.sourceConcept = sourceConcept;
        this.target = target;
        this.relationshipDefinition = relationshipDefinition;
        this.relationshipAttributes = relationshipAttributes;
    }

    /**
     * Igual al constructor mínimo, pero permite inicializar con el ID.
     *
     * @param id                     Identificador único de la relación.
     * @param sourceConcept          El concepto origen de la relación.
     * @param target                 El valor de la relación (destino)
     * @param relationshipDefinition Definición de la relación.
     * @param validityUntil          Fecha de vigencia hasta. Normalmente nulo si está vigente.
     */
    public Relationship(@NotNull long id, @NotNull ConceptSMTK sourceConcept, @NotNull Target target,
                        @NotNull RelationshipDefinition relationshipDefinition, Timestamp validityUntil,List<RelationshipAttribute> relationshipAttributes) {
        this(sourceConcept, target, relationshipDefinition,relationshipAttributes);

        /* Basic ID validation */
        if (id < 0) {
            throw new IllegalArgumentException("El ID de una relación no puede ser negativo o cero.");
        }
        this.id = id;
        this.validityUntil = validityUntil;
    }

    public ConceptSMTK getSourceConcept() {
        return sourceConcept;
    }

    public void setSourceConcept(ConceptSMTK sourceConcept) {
        this.sourceConcept = sourceConcept;
    }

    public RelationshipDefinition getRelationshipDefinition() {
        return relationshipDefinition;
    }

    public void setRelationshipDefinition(RelationshipDefinition relationshipDefinition) {
        this.relationshipDefinition = relationshipDefinition;
    }

    public Timestamp getValidityUntil() {
        return validityUntil;
    }

    public void setValidityUntil(Timestamp validityUntil) {
        this.validityUntil = validityUntil;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        if(target!=null){
            this.target = target;
        }
    }

    public boolean isToBeUpdated() {
        return toBeUpdated;
    }

    public void setToBeUpdated(boolean toBeUpdated) {
        this.toBeUpdated = toBeUpdated;
    }

    /**
     * Este método es responsable de determinar si la relación tiene un valor consistente con su definición.
     *
     * @return <code>true</code> si la relación es consistente con su definición o <code>false</code> si no.
     */
    public boolean isConsistent() {
        return isConsistent(this.relationshipDefinition, this.target);
    }

    /**
     * Este método es responsable de determinar si la relación tiene un valor consistente con su definición.
     *
     * @param relationshipDefinition La definición de la relación.
     * @param target                 El valor destino de la relación.
     *
     * @return <code>true</code> si la relación es consistente con su definición o <code>false</code> si no.
     */
    private static boolean isConsistent(RelationshipDefinition relationshipDefinition, Target target) {
        if (relationshipDefinition.getTargetDefinition().isBasicType()) {
            return (target instanceof BasicTypeValue);
        }

        return relationshipDefinition.getTargetDefinition().isHelperTable() && (target instanceof HelperTableRecord);
    }

    /**
     * Este método es responsable de determinar si la relación está bien definida, es decir, si posee un valor de
     * target y que éste es consistente con el Target Definition.
     *
     * @return <code>true</code> si está bien definida y <code>false</code> sino.
     */
    public boolean isWellDefined() {
        return target != null && this.isConsistent();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relationship that = (Relationship) o;

        if (id != that.id) return false;
        if (toBeUpdated != that.toBeUpdated) return false;
        if (!relationshipDefinition.equals(that.relationshipDefinition)) return false;
        if (!sourceConcept.equals(that.sourceConcept)) return false;
        if (!target.equals(that.target)) return false;
        if (validityUntil != null ? !validityUntil.equals(that.validityUntil) : that.validityUntil != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + sourceConcept.hashCode();
        result = 31 * result + relationshipDefinition.hashCode();
        result = 31 * result + target.hashCode();
        result = 31 * result + (validityUntil != null ? validityUntil.hashCode() : 0);
        result = 31 * result + (toBeUpdated ? 1 : 0);
        return result;
    }

    /**
     * Este método es responsable de retornar todas las relaciones de este concepto que son de un cierto tipo de
     * relación.
     *
     * @param relationshipAttributeDefinition El tipo de relación al que pertenecen las relaciones a retornar.
     *
     * @return Una <code>java.util.List</code> de relaciones de tipo <code>relationshipAttribute</code>.
     */
    public List<RelationshipAttribute> getAttributesByAttributeDefinition(RelationshipAttributeDefinition relationshipAttributeDefinition) {
        List<RelationshipAttribute> someAttributes = new ArrayList<>();
        for (RelationshipAttribute attribute : relationshipAttributes) {
            if (attribute.getRelationAttributeDefinition().equals(relationshipAttributeDefinition)) {
                someAttributes.add(attribute);
            }
        }
        return someAttributes;
    }

    /**
     * Este método es responsable de determinar si esta instancia tiene un ID de bdd distinto de nulo, y por ende se
     * entiende que se encuentra persistido.
     *
     * @return <code>true</code> si está persistida y <code>false</code>.
     */
    public boolean isPersisted() {
        return this.id != DAO.NON_PERSISTED_ID;
    }

    /**
     * Este método es responsable de determinar si esta relación es de tipo definitoria o atributo
     * @return <code>true</code> si es de atributo y <code>false</code>.
     */
    public boolean isAttribute() {
        return getRelationshipDefinition().isAttribute();
    }


    /**
     * Este método es responsable de determinar si esta relación es válida
     * @return <code>true</code> si es válida y <code>false</code> si no lo es.
     */
    public boolean isValid(){
        return (getValidityUntil() == null || getValidityUntil().after(new Timestamp(System.currentTimeMillis())));
    }

    public List<RelationshipAttribute> getRelationshipAttributes() {
        return relationshipAttributes;
    }

    public void setRelationshipAttributes(List<RelationshipAttribute> relationshipAttributes) {
        this.relationshipAttributes = relationshipAttributes;
    }

    public RelationshipAttribute getOrderAttribute(){
        for (RelationshipAttribute relationshipAttribute : getRelationshipAttributes()) {
            if(relationshipAttribute.getRelationAttributeDefinition().getName().equalsIgnoreCase("orden")){
                return relationshipAttribute;
            }
        }
        return null;
    }

    public Integer getOrder(){

        RelationshipAttribute attribute = getOrderAttribute();

        if(attribute != null){
            BasicTypeValue basicTypeValue = (BasicTypeValue) attribute.getTarget();
            return Integer.parseInt(basicTypeValue.getValue().toString());
        }
        else{
            return 0;
        }
    }
}
