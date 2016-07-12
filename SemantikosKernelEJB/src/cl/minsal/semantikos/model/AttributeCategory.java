package cl.minsal.semantikos.model;

/**
 * Created by stk-des01 on 27-05-16.
 */
public class AttributeCategory {


    private String id;
    private String name;
    private String type;
    private int multiplicity;
    private String description;
    private boolean required;

    public AttributeCategory(String id, String name, String type, int multiplicity, String description, boolean required, int idCategoryDes, String order) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.multiplicity = multiplicity;
        this.description = description;
        this.required = required;
        this.idCategoryDes = idCategoryDes;
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdCategoryDes() {
        return idCategoryDes;
    }

    public void setIdCategoryDes(int idCategoryDes) {
        this.idCategoryDes = idCategoryDes;
    }

    public String isOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private int idCategoryDes;
    private String order;


    public AttributeCategory(String id, String name, int multiplicity, String description, boolean required) {
        this.id = id;
        this.name = name;
        this.multiplicity = multiplicity;
        this.description = description;
        this.required = required;
    }

    @Override
    public String toString() {
        return "AttributeCategory { " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", multiplicity=" + multiplicity +
                ", description='" + description + '\'' +
                ", required=" + required +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(int multiplicity) {
        this.multiplicity = multiplicity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
