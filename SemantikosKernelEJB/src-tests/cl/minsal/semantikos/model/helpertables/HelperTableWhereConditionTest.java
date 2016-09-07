package cl.minsal.semantikos.model.helpertables;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.sql.Timestamp;

import static cl.minsal.semantikos.model.helpertables.ConditionalOperator.GREATER_THAN;
import static cl.minsal.semantikos.model.helpertables.HelperTable.SYSTEM_COLUMN_VALIDITY_UNTIL;
import static org.junit.Assert.assertTrue;

public class HelperTableWhereConditionTest {

    @Test
    public void testToString() throws Exception {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        HelperTableWhereCondition validityUntilCondition = new HelperTableWhereCondition(SYSTEM_COLUMN_VALIDITY_UNTIL, GREATER_THAN, now);

        String representation = validityUntilCondition.toString();
        assertTrue(representation.startsWith(SYSTEM_COLUMN_VALIDITY_UNTIL.getColumnName() + " > "));
    }
}