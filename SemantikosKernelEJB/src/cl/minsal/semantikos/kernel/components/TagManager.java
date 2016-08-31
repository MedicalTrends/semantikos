package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías on 8/26/16.
 */
@Local
public interface TagManager {

    public List<Tag> getAllTags();

    /**
     * Este método es responsable de recuperar un Tag por su ID.
     *
     * @param id El ID del tag buscado.
     *
     * @return El Tag con ID <code>id</code>
     */
    public Tag findTagByID(long id);

    public List<Tag> findTagByNamePattern(String pattern);

    public void removeTag(Tag tag);

    public List<ConceptSMTK> findConceptsByTag(Tag tag);

    public void assignTag(ConceptSMTK conceptSMTK, Tag tag);

    public void unassignTag(ConceptSMTK conceptSMTK, Tag tag);

    public void persist(Tag tag);

    public void update(Tag tag);

    public void link(Tag parent, Tag child);

    public void unlink(Tag parent, Tag child);
}
