package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetDefinition;

import javax.ejb.Local;

/**
 * @author Andrés Farías
 */
@Local
public interface TargetDAO {

    /**
     * Este método es responsable de recuperar un Target a partir de su ID.
     *
     * @param idTarget Identificador único del Target.
     *
     * @return Un objeto fresco (ConceptoSMTK, fila de una Tabla Auxiliar, Concepto CST) que es el valor concreto.
     */
    public Target getTargetByID(long idTarget);

    /**
     * Este método es responsable de persistir un Target asociado a una relación
     *
     * @param target el target
     * @param targetDefinition la definición del target
     *
     * @return el id de la nueva entidad persistida
     */
    public long persist(Target target, TargetDefinition targetDefinition);

    /**
     * Este método es responsable de actualizar un Target asociado a una relación
     *
     * @param target el target
     * @param targetDefinition la definición del target
     *
     * @return el id de la entidad actualizada
     */
    public long update(Target target, TargetDefinition targetDefinition);
}
