package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.kernel.daos.TagDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Andrés Farías on 8/26/16.
 */
@Stateless
public class TagManagerImpl implements TagManager {

    /** El logger de la clase */
    private static final Logger logger = LoggerFactory.getLogger(TagManagerImpl.class);

    @EJB
    private TagDAO tagDAO;

    @EJB
    private ConceptDAO conceptDAO;

    @Override
    public List<Tag> getAllTags() {
        logger.debug("Obteniendo todos los Tags del sistema.");
        return tagDAO.getAllTags();
    }

    @Override
    public Tag findTagByID(long id) {
        logger.debug("Buscando tag con ID=" + id);

        Tag tagByID = tagDAO.findTagByID(id);
        logger.debug("Tag encontrado: " + tagByID);

        return tagByID;
    }

    @Override
    public List<Tag> findTagByNamePattern(String pattern) {
        logger.debug("Buscando tags por patrón: " + pattern);

        List<Tag> tagsBy = tagDAO.findTagsBy(pattern);
        logger.debug(tagsBy.size() + " tags encontrados por patrón: " + pattern);

        return tagsBy;
    }

    @Override
    public void removeTag(Tag tag) {
        logger.debug("Eliminando Tag: " + tag);
        tagDAO.remove(tag);
        logger.debug("Tag eliminado: " + tag);
    }

    @Override
    public List<ConceptSMTK> findConceptsByTag(Tag tag) {
        logger.debug("Buscando conceptos por Tag: " + tag);

        List<ConceptSMTK> conceptsByTag = conceptDAO.findConceptsByTag(tag);
        logger.debug(conceptsByTag.size() + " conceptos asociados al tag " + tag + " encontrados.");

        return conceptsByTag;
    }

    @Override
    public void assignTag(ConceptSMTK conceptSMTK, Tag tag) {
        logger.debug("Asociando el tag " + tag + " al concepto " + conceptSMTK);

        tagDAO.assignTag(conceptSMTK, tag);
        logger.debug("Se ha asociando el tag " + tag + " al concepto " + conceptSMTK);
    }

    @Override
    public void unassignTag(ConceptSMTK conceptSMTK, Tag tag) {
        logger.debug("Desasociando el tag " + tag + " al concepto " + conceptSMTK);

        tagDAO.unassignTag(conceptSMTK, tag);
        logger.debug("Se ha asociando el tag " + tag + " al concepto " + conceptSMTK);
    }

    @Override
    public void update(Tag tag) {
        logger.debug("Actualizando tag " + tag);

        tagDAO.update(tag);
        logger.debug("Tag actualizado:" + tag);
    }

    @Override
    public void link(Tag parent, Tag child) {
        logger.debug("Asociación de Tags:" + parent + " --> " + child);

        tagDAO.linkTagToTag(parent, child);
        logger.debug("Se asociaron tags:" + parent + " --> " + child);
    }

    @Override
    public void unlink(Tag parent, Tag child) {
        logger.debug("Desasociación de Tags:" + parent + " --> " + child);

        tagDAO.linkTagToTag(parent, child);
        logger.debug("Se asociaron Tags:" + parent + " --> " + child);
    }
}
