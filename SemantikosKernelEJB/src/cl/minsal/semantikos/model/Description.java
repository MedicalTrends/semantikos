package cl.minsal.semantikos.model;

/**
 * Created by stk-des01 on 01-06-16.
 */
public class Description {
    private int id;
    private String termino;


    public Description(int id, String termino) {
        this.id = id;
        this.termino = termino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }
}
