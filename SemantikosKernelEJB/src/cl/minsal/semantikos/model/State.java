package cl.minsal.semantikos.model;

/**
 * Created by andres on 7/12/16.
 */
public class State {

    private String name;
    private StateMachine stateMachine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}