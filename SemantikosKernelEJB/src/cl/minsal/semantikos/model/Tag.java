package cl.minsal.semantikos.model;

import java.util.ArrayList;
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

    /** Las etiquetas hijos */
    private List<Tag> children;

    private Tag parentTag;


    public Tag(long id, String name, String colorBackground, String colorLetter, List<Tag> children, Tag parentTag) {
        this.id = id;
        this.name = name;
        this.colorBackground = colorBackground;
        this.colorLetter = colorLetter;
        this.children = children;
        this.parentTag = parentTag;

        this.children = new ArrayList<>();
    }


    public List<Tag> getChildrenTag() {
        return children;
    }

    public void setChildren(List<Tag> children) {
        this.children = children;
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

    public void addChild(Tag tag) {
        this.children.add(tag);
    }

    public int deepnessLevel() {

        int deepness = 1;
        int maxDeep = 0;
        Tag champion = null;

        for (Tag child : children) {
            int childDeep = child.deepnessLevel();

            if (childDeep > maxDeep) {
                champion = child;
                maxDeep = childDeep;
            }
        }

        if (champion == null){
            return 1;
        }

        return deepness + champion.deepnessLevel();
    }
}
