package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.kernel.daos.StateMachineDAO;
import cl.minsal.semantikos.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by stk-des01 on 01-06-16.
 */
@Stateful
public class StateMachineManagerImpl implements StateMachineManagerInterface {

    @EJB
    StateMachineDAO stateMachineDAO;

    public ConceptStateMachine conceptStateMachine = ConceptStateMachine.getInstance();


    public StateMachineManagerImpl() {
        if(conceptStateMachine == null)
            System.out.println("ES NULL");
        stateMachineDAO.initConceptStateMachine(conceptStateMachine);
    }

    public ConceptStateMachine getConceptStateMachine() {
        return conceptStateMachine;
    }

    public void setConceptStateMachine(ConceptStateMachine conceptStateMachine) {
        this.conceptStateMachine = conceptStateMachine;
    }

}
