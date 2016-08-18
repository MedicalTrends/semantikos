package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.basictypes.BasicTypeValue;

import javax.ejb.Local;

/**
 * @author Andrés Farías.
 */
@Local
public interface BasicTypeDAO {

    public BasicTypeValue getBasicTypeValueByID(long idBasicValue);

}
