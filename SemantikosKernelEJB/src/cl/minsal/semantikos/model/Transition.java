package cl.minsal.semantikos.model;

/**
 * Created by root on 08-07-16.
 */
public class Transition {

    private long id;

    private State sourceState;
    private State targetState;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public State getSourceState() {
        return sourceState;
    }

    public void setSourceState(State sourceState) {
        this.sourceState = sourceState;
    }

    public State getTargetState() {
        return targetState;
    }

    public void setTargetState(State targetState) {
        this.targetState = targetState;
    }
}