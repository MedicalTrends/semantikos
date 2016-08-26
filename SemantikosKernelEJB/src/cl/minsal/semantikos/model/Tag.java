package cl.minsal.semantikos.model;

import java.sql.Array;
import java.util.List;

/**
 * @author Gustavo Punucura
 */
public class Tag {

    /** Identificador único del Tag en la BDD */
    private long id;

    private String name;
    private String colorBackground;
    private String colorLetter;
    private List<Tag> childTag;
    private Tag parentTag;


    public Tag(String name, String colorBackground, String colorLetter, List<Tag> childTag, Tag parentTag) {
        this.name = name;
        this.colorBackground = colorBackground;
        this.colorLetter = colorLetter;
        this.childTag = childTag;
        this.parentTag = parentTag;
    }

    public List<Tag> getChildTag() {
        return childTag;
    }

    public void setChildTag(List<Tag> childTag) {
        this.childTag = childTag;
    }

    public Tag getParentTag() {
        return parentTag;
    }

    public void setParentTag(Tag parentTag) {
        this.parentTag = parentTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorLetter() {
        return colorLetter;
    }

    public void setColorLetter(String colorLetter) {
        this.colorLetter = colorLetter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
