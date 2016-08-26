package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Gustavo Punucura
 */
@Local
public interface TagDAO {


    /**
     * Este método es el encargado de persistir el tag en la base de datos
     */
    public void persist(Tag tag);

    /**
     * Este método es el encargado de actualizar la información de un tag ya existente en la base de datos
     */
    public void update(Tag tag);

    /**
     * Este método es el encargado remover un tag este tag si contiene hijos, también deben ser eliminados
     */
    public void remove(Tag tag);


    /**
     * Este método es el encargado de buscar etiquetas por nombre
     *
     * @param namePattern El patrón de búsqueda sobre el nombre.
     *
     * @return Una lista de TAGS que contienen
     */
    public List<Tag> findTagsBy(String namePattern);

    /**
     * Este metodo se encarga de anidar un etiqueta padre con su hijo en la base de datos
     */
    public void linkTagToTag(Tag tagPattern, Tag tagChild);

    /**
     * Este método es responsable de recuperar todos tags del sistema.
     *
     * @return Una lista <code>List</code> de Tags.
     */
    List<Tag> getAllTags();

    /**
     * Este método es responsable de asociar un Tag a un Concepto.
     *
     * @param conceptSMTK El concepto al cual se desea asociar el Tag.
     * @param tag         El tag que se asocia al <code>conceptSMTK</code>.
     */
    public void assignTag(ConceptSMTK conceptSMTK, Tag tag);

    /**
     * Este método es responsable de desasociar un Tag a un Concepto.
     *
     * @param conceptSMTK El concepto al cual se desea desasociar el Tag.
     * @param tag         El tag que se desasocia del <code>conceptSMTK</code>.
     */
    public void unassignTag(ConceptSMTK conceptSMTK, Tag tag);

    /**
     * Este método es responsable de recuperar un Tag por su ID.
     *
     * @param id El ID del tag buscado.
     *
     * @return El Tag con ID <code>id</code>
     */
    public Tag findTagByID(long id);
}
