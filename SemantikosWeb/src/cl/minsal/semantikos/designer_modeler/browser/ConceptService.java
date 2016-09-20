package cl.minsal.semantikos.designer_modeler.browser;

/**
 * Created by root on 16-09-16.
 */

import cl.minsal.semantikos.kernel.components.CategoryManager;
import cl.minsal.semantikos.kernel.components.ConceptManager;
import cl.minsal.semantikos.model.*;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ManagedBean(name = "conceptService")
@ApplicationScoped

public class ConceptService {

    @EJB
    private CategoryManager categoryManager;

    @EJB
    private ConceptManager conceptManager;


    private Category selectedCategory;

    @PostConstruct
    protected void initialize() throws ParseException {

        selectedCategory = categoryManager.getCategoryById(419891008);
    }

    /*
    public List<ConceptSMTK> findConceptsByCategory(){

        List<ConceptSMTK> concepts = conceptManager.findConceptBy(selectedCategory, first, pageSize);

        this.setRowCount(conceptManager.countConceptBy(pattern, selectedCategories));


        return conceptSMTKs;

        return conceptManager.findConceptsByCategory(selectedCategory, );
    }
    */


}
