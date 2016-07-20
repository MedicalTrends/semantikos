package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.ConceptStateMachine;
import cl.minsal.semantikos.model.Description;

import javax.ejb.Local;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stk-des01 on 01-06-16.
 */
@Local
public interface StateMachineManagerInterface {

    public ConceptStateMachine getConceptStateMachine();

}