package cl.minsal.semantikos.model;

import java.util.List;

/**
 * Created by andres on 7/12/16.
 */
public class State {

    private String name;
    private StateMachine stateMachine;

    //private List<Transition> transitions;

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

    @Override
    public boolean equals(Object other) {
        return (other instanceof State) && ( String.valueOf(name) != null )
                ? String.valueOf(name).equals(String.valueOf(((State) other).name))
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(name) != null)
                ? (this.getClass().hashCode() + String.valueOf(name).hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        //return String.format("ExampleEntity[%d, %s]", idDescriptionType, glosa);
        return getName();
    }

}
