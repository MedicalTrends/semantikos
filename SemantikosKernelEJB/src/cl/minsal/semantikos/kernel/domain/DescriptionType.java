package cl.minsal.semantikos.kernel.domain;

/**
 * Created by root on 08-07-16.
 */
public class DescriptionType {
    private long idDescriptionType;
    private String glosa;

    public long getIdDescriptionType() {
        return idDescriptionType;
    }

    public void setIdDescriptionType(long idTipoDescripcion) {
        this.idDescriptionType = idTipoDescripcion;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionType that = (DescriptionType) o;

        if (idDescriptionType != that.idDescriptionType) return false;
        if (glosa != null ? !glosa.equals(that.glosa) : that.glosa != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idDescriptionType ^ (idDescriptionType >>> 32));
        result = 31 * result + (glosa != null ? glosa.hashCode() : 0);
        return result;
    }
}
