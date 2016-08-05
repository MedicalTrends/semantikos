package cl.minsal.semantikos.model;

/**
 * Created by root on 08-07-16.
 */
public class DescriptionType {
    private long id;
    private String gloss;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof DescriptionType) && ( String.valueOf(id) != null )
                ? String.valueOf(id).equals(String.valueOf(((DescriptionType) other).id))
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(id) != null)
                ? (this.getClass().hashCode() + String.valueOf(id).hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        return getGloss();
    }

}
