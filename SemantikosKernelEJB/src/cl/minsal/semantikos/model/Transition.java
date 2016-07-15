package cl.minsal.semantikos.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by root on 08-07-16.
 */
public class Transition {
    private long idTransition;
    private Long idStateDestination;
    private Long idStateOrigin;

    public long getIdTransition() {
        return idTransition;
    }

    public void setIdTransition(long idTransition) {
        this.idTransition = idTransition;
    }

    public Long getIdStateDestination() {
        return idStateDestination;
    }

    public void setIdStateDestination(Long idStateDestination) {
        this.idStateDestination = idStateDestination;
    }

    public Long getIdStateOrigin() {
        return idStateOrigin;
    }

    public void setIdStateOrigin(Long idStateOrigin) {
        this.idStateOrigin = idStateOrigin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transition that = (Transition) o;

        if (idTransition != that.idTransition) return false;
        if (idStateDestination != null ? !idStateDestination.equals(that.idStateDestination) : that.idStateDestination != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTransition ^ (idTransition >>> 32));
        result = 31 * result + (idStateDestination != null ? idStateDestination.hashCode() : 0);
        return result;
    }
}
