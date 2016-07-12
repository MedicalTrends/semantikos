package cl.minsal.semantikos.kernel.semantikos.kernel;

import smtk.classes.Description;

import javax.ejb.Local;
import java.util.ArrayList;

/**
 * Created by stk-des01 on 01-06-16.
 */
@Local
public interface ConceptManagerInterface {

    public ArrayList<Description> findDescriptionForPattern(String pattern);

    public String addConcept(String idCategory, boolean isValid);

    public int getIDConceptBy(int idDescription);

}
