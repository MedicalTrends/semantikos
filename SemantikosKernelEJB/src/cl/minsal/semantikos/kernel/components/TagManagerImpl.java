package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;

import java.util.List;

/**
 * @author Andrés Farías on 8/26/16.
 */
public class TagManagerImpl implements TagManager {

    @Override
    public List<Tag> getAllTags() {
        return null;
    }

    @Override
    public List<Tag> findTagByNamePattern(String pattern) {
        return null;
    }

    @Override
    public void removeTag(Tag tag) {

    }

    @Override
    public List<ConceptSMTK> findConceptsByTag(Tag tag) {
        return null;
    }

    @Override
    public void assignTag(ConceptSMTK conceptSMTK, Tag tag) {

    }

    @Override
    public void removeTag(ConceptSMTK conceptSMTK, Tag tag) {

    }

    @Override
    public void update(Tag tag) {

    }

    @Override
    public void link(Tag parent, Tag child) {

    }

    @Override
    public void unlink(Tag parent, Tag child) {

    }
}
