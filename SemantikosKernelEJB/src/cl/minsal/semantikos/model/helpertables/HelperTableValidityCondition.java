package cl.minsal.semantikos.model.helpertables;

import cl.minsal.semantikos.model.helpertables.ConditionalOperator;
import cl.minsal.semantikos.model.helpertables.HelperTableColumn;
import cl.minsal.semantikos.model.helpertables.HelperTableWhereCondition;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BluePrints Developer on 07-09-2016.
 */
public class HelperTableValidityCondition extends HelperTableWhereCondition{


    public HelperTableValidityCondition(HelperTableColumn helperTableColumn, Date date) {
        super(helperTableColumn,ConditionalOperator.GREATER_THAN,  "to_date('"+ new SimpleDateFormat("dd-MM-yyyy").format( date) +"', 'DD-MM-YYYY' )" );
    }

    @Override
    public String toString() {
        return "( " + helperTableColumn.getName() + " is  NULL  or " + super.toString() + ")";
    }
}
