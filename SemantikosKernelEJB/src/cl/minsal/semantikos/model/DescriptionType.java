package cl.minsal.semantikos.model;

/**
 * Created by root on 08-07-16.
 */
public class DescriptionType {
    private long idDescriptionType;
    private String gloss;

    public long getIdDescriptionType() {
        return idDescriptionType;
    }

    public void setIdDescriptionType(long idTipoDescripcion) {
        this.idDescriptionType = idTipoDescripcion;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof DescriptionType) && ( String.valueOf(idDescriptionType) != null )
                ? String.valueOf(idDescriptionType).equals(String.valueOf(((DescriptionType) other).idDescriptionType))
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(idDescriptionType) != null)
                ? (this.getClass().hashCode() + String.valueOf(idDescriptionType).hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        //return String.format("ExampleEntity[%d, %s]", idDescriptionType, glosa);
        return getGloss();
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionType that = (DescriptionType) o;

        if (idDescriptionType != that.idDescriptionType) return false;
        if (gloss != null ? !gloss.equals(that.gloss) : that.gloss != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idDescriptionType ^ (idDescriptionType >>> 32));
        result = 31 * result + (gloss != null ? gloss.hashCode() : 0);
        return result;
    }
    */
}
