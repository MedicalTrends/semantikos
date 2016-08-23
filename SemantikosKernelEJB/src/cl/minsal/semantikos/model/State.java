package cl.minsal.semantikos.model;

import java.util.List;

/**
 * @author: Diego Soto
 */
public class State implements IState {

    /** Identificador único de la base de datos */
    private long id;

    private String name;

    private StateMachine stateMachine;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
