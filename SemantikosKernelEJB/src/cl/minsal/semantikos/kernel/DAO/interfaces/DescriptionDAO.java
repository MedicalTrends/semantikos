package cl.minsal.semantikos.kernel.DAO.interfaces;

import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;

import java.util.List;


/**
 * Created by des01c7 on 01-07-16.
 */
public interface DescriptionDAO {

    public List<DescriptionType> getAllTypes();

    public List<Description> getDescriptionBy(int id);
}
