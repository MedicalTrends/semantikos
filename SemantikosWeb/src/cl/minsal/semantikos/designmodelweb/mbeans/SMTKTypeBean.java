package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;

import javax.ejb.EJB;
import java.awt.*;
import java.util.List;

/**
 * Created by des01c7 on 26-07-16.
 */
public class SMTKTypeBean {

    private Category category;
    private List<ConceptSMTK> conceptSearch;
    private List<ConceptSMTK> conceptValue;

    @EJB
    private ConceptManagerInterface conceptManager;


    public List<ConceptSMTK> findConcept(){


         return null;
    }



}
