package cl.minsal.semantikos.kernel.DAO.interfaces;

import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;

import javax.ejb.Local;
import java.util.List;


/**
 * Created by des01c7 on 01-07-16.
 */

@Local
public interface DescriptionDAO {

    public List<DescriptionType> getAllTypes();

    public List<Description> getDescriptionByConceptID(long id);
}
