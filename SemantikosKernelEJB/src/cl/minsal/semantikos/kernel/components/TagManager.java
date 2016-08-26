package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;

import java.util.List;

/**
 * @author Andrés Farías on 8/26/16.
 */
public interface TagManager {

    public List<Tag> getAllTags();

    public List<Tag> findTagByNamePattern(String pattern);

    public void removeTag(Tag tag);

    public List<ConceptSMTK> findConceptsByTag(Tag tag);

    public void assignTag(ConceptSMTK conceptSMTK, Tag tag);

    public void removeTag(ConceptSMTK conceptSMTK, Tag tag);

    public void update(Tag tag);

    public void link(Tag parent, Tag child);

    public void unlink(Tag parent, Tag child);
}
