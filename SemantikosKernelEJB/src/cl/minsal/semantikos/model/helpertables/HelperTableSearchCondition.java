package cl.minsal.semantikos.model.helpertables;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by BluePrints Developer on 07-09-2016.
 */
public class HelperTableSearchCondition extends HelperTableWhereCondition{

    private List<HelperTableColumn> searchableColumns;
    public HelperTableSearchCondition(List<HelperTableColumn> searchableColumns, String query) {
        super(searchableColumns.get(0),ConditionalOperator.GREATER_THAN, "'%"+query+"%'" );
        this.searchableColumns = searchableColumns;
    }

    @Override
    public String toString() {

        String query = "";

        for (HelperTableColumn column: searchableColumns) {
            if(query.length()>0)
                query =query + " OR ";

                String subquery = column.getColumnName()  + " " + conditionalOperator.toString() + " " + value;

                query += "("+subquery+")";
        }


        return query;
    }
}
