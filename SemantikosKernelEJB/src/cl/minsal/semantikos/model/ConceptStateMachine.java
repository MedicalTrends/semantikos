package cl.minsal.semantikos.model;

/**
 * Created by andres on 7/12/16.
 */
public class ConceptStateMachine extends StateMachine {

    /** The singleton object for this class */
    private static ConceptStateMachine singletonInstance;

    /**
     * The method for obtaining the only instance of the class.
     *
     * @return The singleton object.
     */
    public static ConceptStateMachine getInstance() {

        if(singletonInstance == null)
            singletonInstance = new ConceptStateMachine();

        return ConceptStateMachine.singletonInstance;
    }

    private ConceptStateMachine() {
        this.singletonInstance = new ConceptStateMachine();
    }
}
