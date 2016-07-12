package cl.minsal.semantikos.kernel.EJB.interfaces;

import cl.minsal.semantikos.kernel.domain.DescriptionType;
import cl.minsal.semantikos.kernel.domain.DescriptionType;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by des01c7 on 29-06-16.
 */

@Local
public interface DescriptionManager {


    public List<DescriptionType> getAllTypes();

}
