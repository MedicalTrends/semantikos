package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Falta inicializar, con el estado base para este diagrama
 */
public class StateMachine {

    private State initialState;
    private List<Transition> transitions;

    public State getInitialState() {
        return initialState;
    }

    /**
     * TODO: Escribir la doc.
     * @param aSourceState
     * @return
     */
    public List<Transition> getTransitions(State aSourceState){

        List<Transition> results = new ArrayList<Transition>();
        for (Transition transition : transitions) {
            if (transition.getSourceState().equals(aSourceState))
                results.add(transition);
        }

        return results;
    }

    /**
     * Este metodo es responsable de realizar la transición entre un estado y otro.
     *
     * @param sourceState El estado desde el cual se realiza la transición
     * @param targetState El etado al cual se quiere llegar.
     *
     *                    @return <code>true</code> si la transición se realiza y <code>false</code> si no
     * @throws IllegalStateException Lanzado si la maquina se encuentra en un estado que no le pertenece.
     */
    public boolean transit(State sourceState, State targetState) throws IllegalStateException {

        /*
        for (Transition transition : sourceState.getTransitions()) {

            if(transition.getTargetState().equals(targetState))
                return true;
            else
                return false;

        }
        */
        return false;

    }
}
