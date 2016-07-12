package cl.minsal.semantikos.kernel.domain;

import java.util.List;

/**
 * Created by root on 08-07-16.
 */
public class BasicType {
    private long idBasicType;
    private String name;
    private String description;

    private List<TargetDefinition> targetDefinitions;

    public long getIdBasicType() {
        return idBasicType;
    }

    public void setIdBasicType(long idBasicType) {
        this.idBasicType = idBasicType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TargetDefinition> getTargetDefinitions() {
        return targetDefinitions;
    }

    public void setTargetDefinitions(List<TargetDefinition> targetDefinitions) {
        this.targetDefinitions = targetDefinitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicType that = (BasicType) o;

        if (idBasicType != that.idBasicType) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idBasicType ^ (idBasicType >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
