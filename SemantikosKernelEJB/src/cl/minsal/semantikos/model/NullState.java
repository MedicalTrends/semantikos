package cl.minsal.semantikos.model;

import cl.minsal.semantikos.kernel.daos.StateMachineDAO;

/**
 * @author Andrés Farías on 8/23/16.
 */
public class NullState implements IState {

    private static IState instance = new NullState();

    private NullState() {
    }

    public static IState getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "Estado NULO";
    }

    @Override
    public long getId() {
        return StateMachineDAO.NULL_ID;
    }
}
