package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;

import java.util.List;

/**
 * Created by root on 08-07-16.
 */
public class TargetType {
    private long idTargetType;
    private String nombre;
    private String descripcion;

    private List<TargetDefinition> targetDefinitions;

    private List<BasicTypeDefinition> basicTypeDefinitions;

    public long getIdTargetType() {
        return idTargetType;
    }

    public void setIdTargetType(long idTargetType) {
        this.idTargetType = idTargetType;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<TargetDefinition> getTargetDefinitions() {
        return targetDefinitions;
    }

    public void setTargetDefinitions(List<TargetDefinition> targetDefinitions) {
        this.targetDefinitions = targetDefinitions;
    }

    public List<BasicTypeDefinition> getBasicTypeDefinitions() {
        return basicTypeDefinitions;
    }

    public void setBasicTypeDefinitions(List<BasicTypeDefinition> basicTypeDefinitions) {
        this.basicTypeDefinitions = basicTypeDefinitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetType that = (TargetType) o;

        if (idTargetType != that.idTargetType) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTargetType ^ (idTargetType >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }
}
