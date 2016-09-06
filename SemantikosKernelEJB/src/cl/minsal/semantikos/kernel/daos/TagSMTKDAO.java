package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.TagSMTK;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías on 9/5/16.
 */
@Local
public interface TagSMTKDAO {

    /**
     * Este método es responsable de recuperar todos los Tag Semántikos desde la BDD.
     *
     * @return La lista de Tag Semantikos.
     */
    public List<TagSMTK> getAllTagSMTKs();

    /**
     * Este método es responsable de recuperar un Tag SMTK por su id.
     *
     * @param idTag Identificador único del Tag Semántikos.
     *
     * @return El Tag Semántikos a retornar.
     */
    TagSMTK findTagSMTKByID(long idTag);
}
