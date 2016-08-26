package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Gustavo Punucura
 *         TODO: Implementar esta clase.
 */
@Stateless
public class TagDAOImpl implements TagDAO {

    @Override
    public void persist(Tag tag) {

    }

    @Override
    public void update(Tag tag) {

    }

    @Override
    public void remove(Tag tag) {

    }

    @Override
    public List<Tag> findTagsBy(String namePattern) {
        return null;
    }

    @Override
    public void linkTagToTag(Tag tagPattern, Tag tagChild) {

    }

    @Override
    public List<Tag> getAllTags() {
        return null;
    }

    @Override
    public void assignTag(ConceptSMTK conceptSMTK, Tag tag) {

    }

    @Override
    public void unassignTag(ConceptSMTK conceptSMTK, Tag tag) {

    }

    @Override
    public Tag findTagByID(long id) {
        return null;
    }


}
