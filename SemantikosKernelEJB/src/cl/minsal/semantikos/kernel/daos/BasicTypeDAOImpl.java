package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.basictypes.BasicTypeValue;

import javax.ejb.Stateless;

/**
 * @author Andrés Farías
 */
@Stateless
public class BasicTypeDAOImpl implements BasicTypeDAO {

    @Override
    public BasicTypeValue getBasicTypeValueByID(long idBasicValue) {

        //TODO: UH?
        return null;
    }
}
