package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import javax.ejb.Local;
import java.text.ParseException;

/**
 * Esta clase es responsable de recuperar los TargetTypeDefinition desde la BD.
 */
@Local
public interface TargetTypeDAO {

    /**
     * Este método es responsable de recuperar un BasicTypeDefinition desde la BD.
     *
     * @param id El ID del basic type definition.
     *
     * @return El objecto fresco creado desde la BD.
     */
    public BasicTypeDefinition findByID(long id) throws ParseException;
}
