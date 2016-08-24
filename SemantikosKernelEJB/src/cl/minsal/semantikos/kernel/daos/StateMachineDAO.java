package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptStateMachine;

import javax.ejb.Local;

/**
 * @author Andrés Farías on 13-07-16.
 */
@Local
public interface StateMachineDAO {

    /** El ID nulo en la base de datos para indicar no persistencia */
    public static final long NULL_ID = -1;

    /**
     * Este método crea una máquina de estados para los conceptos.
     *
     * @return La máquina de estados para los conceptos.
     */
    public ConceptStateMachine initConceptStateMachine();
}
