package cl.minsal.semantikos.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una máquina de estado.
 *
 * @author Andrés Farías
 */
public class StateMachine {

    /** El estado inicial de la máquina de estados */
    private IState initialState = NullState.getInstance();

    /** Las transiciones definidas para esta máquina de estado */
    private List<Transition> transitions;

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    /**
     * Este método es responsable de retornar el estado inicial de esta máquina de destados.
     *
     * @return El estado (<code>IState</code>) inicial de esta máquina de estados.
     */
    public IState getInitialState() {
        return initialState;
    }

    /**
     * Este método retorna las transiciones que parten desde un estado dado.
     *
     * @param aSourceState El estado origen de las transiciones solicitadas.
     *
     * @return Una <code>List</code> de transiciones que parten desde el estado <code>aSourceState</code>.
     */
    public List<Transition> getTransitionsFrom(@NotNull State aSourceState) {

        List<Transition> results = new ArrayList<Transition>();
        for (Transition transition : transitions) {
            /* Se filtran aquellas transiciones que parten desde el estado <code>aSourceState</code> */
            if (transition.getSourceState().equals(aSourceState))
                results.add(transition);
        }

        return results;
    }

    /**
     * Este método es responsable de listar los estados a los cuales es posible llegar, en un paso, desde el estado
     * <code>aSourceState</code>.
     *
     * @param aSourceState El estado inicial desde el cual salen transiciones que llegan a los estados solicitados.
     *
     * @return Una lista de los estados a los que se llega en una transición desde el estado <code>aSourceState</code>.
     */
    public List<IState> getTargetStates(State aSourceState) {

        List<IState> results = new ArrayList<IState>();
        for (Transition transition : transitions) {
            if (transition.getSourceState().equals(aSourceState))
                results.add(transition.getTargetState());
        }

        return results;
    }

    /**
     * Este metodo es responsable de realizar la transición entre un estado y otro.
     *
     * @param sourceState El estado desde el cual se realiza la transición
     * @param targetState El etado al cual se quiere llegar.
     *
     * @return <code>true</code> si la transición se realiza y <code>false</code> si no
     *
     * @throws IllegalStateException Lanzado si la maquina se encuentra en un estado que no le pertenece.
     */
    public boolean transit(State sourceState, State targetState) throws IllegalStateException {

        for (Transition transition : getTransitionsFrom(sourceState)) {
            if (transition.getTargetState().equals(targetState))
                return true;
        }

        return false;
    }
}
