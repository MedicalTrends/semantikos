package cl.minsal.semantikos.kernel.domain;

import java.sql.Timestamp;

/**
 * Created by root on 08-07-16.
 */
public class Etiquetas {
    private long idEtiqueta;
    private String nombreEtiqueta;
    private Integer idPadre;
    private boolean estado;
    private Timestamp fechaRegistro;
    private Integer usuarioRegsitro;

    public long getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(long idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }

    public void setNombreEtiqueta(String nombreEtiqueta) {
        this.nombreEtiqueta = nombreEtiqueta;
    }

    public Integer getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Integer idPadre) {
        this.idPadre = idPadre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getUsuarioRegsitro() {
        return usuarioRegsitro;
    }

    public void setUsuarioRegsitro(Integer usuarioRegsitro) {
        this.usuarioRegsitro = usuarioRegsitro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Etiquetas that = (Etiquetas) o;

        if (idEtiqueta != that.idEtiqueta) return false;
        if (estado != that.estado) return false;
        if (nombreEtiqueta != null ? !nombreEtiqueta.equals(that.nombreEtiqueta) : that.nombreEtiqueta != null)
            return false;
        if (idPadre != null ? !idPadre.equals(that.idPadre) : that.idPadre != null) return false;
        if (fechaRegistro != null ? !fechaRegistro.equals(that.fechaRegistro) : that.fechaRegistro != null)
            return false;
        if (usuarioRegsitro != null ? !usuarioRegsitro.equals(that.usuarioRegsitro) : that.usuarioRegsitro != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idEtiqueta ^ (idEtiqueta >>> 32));
        result = 31 * result + (nombreEtiqueta != null ? nombreEtiqueta.hashCode() : 0);
        result = 31 * result + (idPadre != null ? idPadre.hashCode() : 0);
        result = 31 * result + (estado ? 1 : 0);
        result = 31 * result + (fechaRegistro != null ? fechaRegistro.hashCode() : 0);
        result = 31 * result + (usuarioRegsitro != null ? usuarioRegsitro.hashCode() : 0);
        return result;
    }
}
