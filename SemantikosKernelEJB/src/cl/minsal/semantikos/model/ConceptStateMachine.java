package cl.minsal.semantikos.model;

/**
 * @author Andrés Farías
 */
public class ConceptStateMachine extends StateMachine {

    /** The singleton object for this class */
    private static ConceptStateMachine singletonInstance = new ConceptStateMachine();

    private ConceptStateMachine() {

    }

    /**
     * The method for obtaining the only instance of the class.
     *
     * @return The singleton object.
     */
    public static ConceptStateMachine getInstance() {
        return ConceptStateMachine.singletonInstance;
    }
}
