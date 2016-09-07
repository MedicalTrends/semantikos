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


    public Tag(long id, String name, String colorBackground, String colorLetter, Tag parentTag) {
        this.id = id;
        this.name = name;
        this.colorBackground = colorBackground;
        this.colorLetter = colorLetter;
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

    /**
     * Este método es responsable de retornar el nivel de profundidad relativa a este Tag.
     *
     * @return El nivel de profundidad de este nodo (aunque tenga padres).
     */
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

        if (champion == null) {
            return 1;
        }

        return deepness + champion.deepnessLevel();
    }

    /**
     * Este método es responsable de determinar este tag contienen a <code>tag</code> en su familia. Se dice que un tag
     * contiene a otro en su familia si:
     * <ul>
     * <li>Es el mismo tag</li>
     * <li>Es el padre</li>
     * <li>Es uno de los hijos</li>
     * </ul>
     *
     * @param tag El tag cuya familia se desea ver si contiene a este tag.
     *
     * @return <code>true</code> si este tag está en la familia del <code>tag</code> y <code>false</code> sino.
     */
    public boolean containsInItsFamily(Tag tag) {

        /* Si es el mismo tag, lo contiene */
        if (this.equals(tag)) {
            return true;
        }

        /* Si es el padre, también lo contiene */
        else if (parentTag != null && parentTag.equals(tag)) {
            return true;
        }

        /* Finalmente, quizás "tag" es uno de los hijos */
        for (Tag child : children) {
            if (child.containsInItsFamily(tag)){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id != tag.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
