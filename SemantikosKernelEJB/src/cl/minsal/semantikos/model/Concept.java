package cl.minsal.semantikos.model;


import java.util.ArrayList;

/**
 * Created by stk-des01 on 01-06-16.
 */
public class Concept {
    private ArrayList<Description> descriptions;


    public ArrayList<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public Concept(int id, ArrayList<Description> descriptions) {

        this.descriptions = descriptions;
    }




}
