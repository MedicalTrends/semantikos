package cl.minsal.semantikos.kernel.daos;

import java.sql.Timestamp;

/**
 * Created by des01c7 on 16-08-16.
 */
public interface TargetDAO {
    public long createTarget(float floatValue, Timestamp dateValue, String stringValue, boolean booleanValue, int intValue, long idAuxiliary, long idExtern, long idConceptSCT, long idConceptSMTK, long targetType );
}
