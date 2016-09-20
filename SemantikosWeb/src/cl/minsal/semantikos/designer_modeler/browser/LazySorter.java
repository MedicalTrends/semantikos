package cl.minsal.semantikos.designer_modeler.browser;

/**
 * Created by root on 16-09-16.
 */

import cl.minsal.semantikos.model.Car;
import cl.minsal.semantikos.model.ConceptSMTK;
import org.primefaces.model.SortOrder;

import java.util.Comparator;

public class LazySorter implements Comparator<ConceptSMTK> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(ConceptSMTK concept1, ConceptSMTK concept2) {
        try {
            Object value1 = ConceptSMTK.class.getField(this.sortField).get(concept1);
            Object value2 = ConceptSMTK.class.getField(this.sortField).get(concept2);

            int value = ((Comparable)value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
