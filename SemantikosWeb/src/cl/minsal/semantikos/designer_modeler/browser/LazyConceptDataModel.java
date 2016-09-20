package cl.minsal.semantikos.designer_modeler.browser;

/**
 * Created by root on 16-09-16.
 */

import cl.minsal.semantikos.model.Car;
import cl.minsal.semantikos.model.ConceptSMTK;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.*;

/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class LazyConceptDataModel extends LazyDataModel<ConceptSMTK> {

    private List<ConceptSMTK> datasource;

    public LazyConceptDataModel(List<ConceptSMTK> datasource) {
        this.datasource = datasource;
    }

    @Override
    public ConceptSMTK getRowData(String rowKey) {
        for(ConceptSMTK conceptSMTK : datasource) {
            if(conceptSMTK.getConceptID().equals(rowKey))
                return conceptSMTK;
        }

        return null;
    }

    @Override
    public Object getRowKey(ConceptSMTK conceptSMTK) {
        return conceptSMTK.getConceptID();
    }

    @Override
    public List<ConceptSMTK> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

        List<ConceptSMTK> data = new ArrayList<ConceptSMTK>();

        //filter
        for(ConceptSMTK conceptSMTK : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(conceptSMTK.getClass().getField(filterProperty).get(conceptSMTK));

                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        }
                        else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }

            if(match) {
                data.add(conceptSMTK);
            }
        }

        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
