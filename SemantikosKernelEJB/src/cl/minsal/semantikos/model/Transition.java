package cl.minsal.semantikos.model;

/**
 * Created by root on 08-07-16.
 */
public class Transition {

    private long id;

    private IState sourceState;
    private IState targetState;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public IState getSourceState() {
        return sourceState;
    }

    public void setSourceState(IState sourceState) {
        this.sourceState = sourceState;
    }

    public IState getTargetState() {
        return targetState;
    }

    public void setTargetState(IState targetState) {
        this.targetState = targetState;
    }
}