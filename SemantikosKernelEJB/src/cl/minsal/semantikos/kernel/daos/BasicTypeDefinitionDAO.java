package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import javax.ejb.Local;

/**
 * @author Andrés Farías & Gustavo Punucura.
 */
@Local
public interface BasicTypeDefinitionDAO {

    /**
     * Este método es responsable de recuperar un BasicTypeDefinition desde la base de datos.
     * @param id El identificador único del basic type definition.
     * @return Un objeto representando el <code>BasicTypeDefinition</code>.
     */
    public BasicTypeDefinition getBasicTypeDefinitionById(long id);
}
