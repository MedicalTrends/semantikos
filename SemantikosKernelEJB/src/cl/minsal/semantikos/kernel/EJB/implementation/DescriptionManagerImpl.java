package cl.minsal.semantikos.kernel.EJB.implementation;

import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.kernel.DAO.implementation.DescriptionDAOImpl;
import cl.minsal.semantikos.kernel.EJB.interfaces.DescriptionManager;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by des01c7 on 29-06-16.
 */

@Stateless
public class DescriptionManagerImpl implements DescriptionManager {

    @Override
    public List<DescriptionType> getAllTypes() {
        DescriptionDAOImpl descriptionDAO= new DescriptionDAOImpl();
        System.out.println("descriptionDAO.getAllTypes().size()="+descriptionDAO.getAllTypes().size());
        return descriptionDAO.getAllTypes();
    }

}
