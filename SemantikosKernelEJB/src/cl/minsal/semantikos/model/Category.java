package cl.minsal.semantikos.model;

import java.util.ArrayList;

/**
 * Created by stk-des01 on 02-06-16.
 */
public class Category {

    private int id;
    private String name;
    private boolean isValid;
    private ArrayList<AttributeCategory> attributeCategories;

    public Category(int id, String name, boolean isValid, ArrayList<AttributeCategory> attributeCategories) {
        this.id = id;
        this.name = name;
        this.isValid = isValid;
        this.attributeCategories = attributeCategories;
    }

    public Category(int id, String name, boolean isValid) {
        this.id = id;
        this.name = name;
        this.isValid = isValid;
    }
    public Category() {

    }

    public ArrayList<AttributeCategory> getAttributeCategories() {
        return attributeCategories;
    }

    public void setAttributeCategories(ArrayList<AttributeCategory> attributeCategories) {
        this.attributeCategories = attributeCategories;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
