package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.model.DescriptionTypeFactory;

import javax.ejb.Local;
import java.util.List;


/**
 * Created by des01c7 on 01-07-16.
 */
@Local
public interface DescriptionDAO {

    public List<DescriptionType> getDescriptionTypes();

    public List<Description> getDescriptionBy(int id);

    /**
     * Este método es responsable de recuperar todas las descripciones de un concepto a partir de su ID.
     *
     * @param id El ID del concepto cuyas descripciones se desea recuperar.
     *
     * @return La lista de las descripciones del concepto cuyo ID fue dado.
     */
    public List<Description> getDescriptionsByConceptID(long id);

    /**
     * Este método es responsable de retornar un Factory.
     * @return
     */
    public DescriptionTypeFactory refreshDescriptionTypes();
}
