package cl.minsal.semantikos.model;

import cl.minsal.semantikos.kernel.daos.TagDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * @author Andrés Farías on 8/26/16.
 */
@Singleton
public class TagFactory {

    /** El logger de esta clase */
    private static final Logger logger = LoggerFactory.getLogger(TagFactory.class);

    @EJB
    private TagDAO tagDAO;

    /**
     * Este método es responsable de crear un objeto Tag a partir de una expresión JSON de la forma:
     * <code>[{"id":1,"name":"tag etiqueta ","letter_color":null,"background_color":null,"id_parent_tag":null},
     * {"id":2,"name":"tag update","letter_color":null,"background_color":null,"id_parent_tag":null}]</code>
     *
     * @param jsonExpression La expresión JSon a partir de la cual se crea el Tag.
     *
     * @return El objeto Tag.
     */
    public List<Tag> createTagsFromJSON(String jsonExpression) {

        /* Si JSON es nulo, se retorna una lista vacía */
        if (jsonExpression == null) {
            return Collections.emptyList();
        }

        /* Se parsea la expresión JSON */
        ObjectMapper mapper = new ObjectMapper();
        TagDTO[] tagsDTO;
        try {
            tagsDTO = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), TagDTO[].class);
        } catch (IOException e) {
            String errorMsg = "Error when parsing a JSON a Relationships";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        /* Se retorna como una lista */
        return createTagFromDTOs(tagsDTO);
    }

    /**
     * Este método es responsable de transformar el arreglo de DTO's a objetos del modelo.
     *
     * @param tagsDTO El arreglo de DTO.
     *
     * @return Una lista de objetos de negocio Tag.
     */
    private List<Tag> createTagFromDTOs(TagDTO[] tagsDTO) {
        List<Tag> tags = new ArrayList<>();
        for (TagDTO tagDTO : tagsDTO) {
            tags.add(createTagFromDTO(tagDTO));
        }

        return tags;
    }

    /**
     * Este método es responsable de crear un objeto de negocio <code>Tag</code> a partir de un DTO.
     *
     * @param tagDTO El DTO a partir del cual se creará el objeto de negocio.
     *
     * @return El objeto de negocio Tag.
     */
    private Tag createTagFromDTO(TagDTO tagDTO) {

        Tag parentTag = tagDAO.findTagByID(tagDTO.getId_parent_tag());
        List<Tag> children = tagDAO.getChildrenOf(tagDTO.getId());

        return new Tag(tagDTO.getId(), tagDTO.getName(), tagDTO.getBackground_color(), tagDTO.getLetter_color(), children, parentTag);
    }

}

class TagDTO {

    private final long id;
    private final String name;
    private final String letter_color;
    private final String background_color;
    private final long id_parent_tag;

    public TagDTO(long id, String name, String letter_color, String background_color, long id_parent_tag) {
        this.id = id;
        this.name = name;
        this.letter_color = letter_color;
        this.background_color = background_color;
        this.id_parent_tag = id_parent_tag;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLetter_color() {
        return letter_color;
    }

    public String getBackground_color() {
        return background_color;
    }

    public long getId_parent_tag() {
        return id_parent_tag;
    }
}
