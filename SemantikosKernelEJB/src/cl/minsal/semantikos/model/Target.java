package cl.minsal.semantikos.model;

import java.sql.Timestamp;

/**
 * Created by root on 08-07-16.
 */
public class Target {
    private long idTarget;
    private Float floatValue;
    private Timestamp dateValue;
    private String stringValue;
    private boolean booleanValue;
    private Long intValue;
    private Long idAuxiliary;
    private Long idExtern;
    private Long idConceptoSct;
    private Long idConceptoStk;
    private Long idTargetType;

    public long getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(long idTarget) {
        this.idTarget = idTarget;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    public Timestamp getDateValue() {
        return dateValue;
    }

    public void setDateValue(Timestamp dateValue) {
        this.dateValue = dateValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Long getIntValue() {
        return intValue;
    }

    public void setIntValue(Long intValue) {
        this.intValue = intValue;
    }

    public Long getIdAuxiliary() {
        return idAuxiliary;
    }

    public void setIdAuxiliary(Long idAuxiliary) {
        this.idAuxiliary = idAuxiliary;
    }

    public Long getIdExtern() {
        return idExtern;
    }

    public void setIdExtern(Long idExtern) {
        this.idExtern = idExtern;
    }

    public Long getIdConceptoSct() {
        return idConceptoSct;
    }

    public void setIdConceptoSct(Long idConceptoSct) {
        this.idConceptoSct = idConceptoSct;
    }

    public Long getIdConceptoStk() {
        return idConceptoStk;
    }

    public void setIdConceptoStk(Long idConceptoStk) {
        this.idConceptoStk = idConceptoStk;
    }

    public Long getIdTargetType() {
        return idTargetType;
    }

    public void setIdTargetType(Long idTargetType) {
        this.idTargetType = idTargetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Target that = (Target) o;

        if (idTarget != that.idTarget) return false;
        if (booleanValue != that.booleanValue) return false;
        if (floatValue != null ? !floatValue.equals(that.floatValue) : that.floatValue != null) return false;
        if (dateValue != null ? !dateValue.equals(that.dateValue) : that.dateValue != null) return false;
        if (stringValue != null ? !stringValue.equals(that.stringValue) : that.stringValue != null) return false;
        if (intValue != null ? !intValue.equals(that.intValue) : that.intValue != null) return false;
        if (idAuxiliary != null ? !idAuxiliary.equals(that.idAuxiliary) : that.idAuxiliary != null) return false;
        if (idExtern != null ? !idExtern.equals(that.idExtern) : that.idExtern != null) return false;
        if (idConceptoSct != null ? !idConceptoSct.equals(that.idConceptoSct) : that.idConceptoSct != null)
            return false;
        if (idConceptoStk != null ? !idConceptoStk.equals(that.idConceptoStk) : that.idConceptoStk != null)
            return false;
        if (idTargetType != null ? !idTargetType.equals(that.idTargetType) : that.idTargetType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTarget ^ (idTarget >>> 32));
        result = 31 * result + (floatValue != null ? floatValue.hashCode() : 0);
        result = 31 * result + (dateValue != null ? dateValue.hashCode() : 0);
        result = 31 * result + (stringValue != null ? stringValue.hashCode() : 0);
        result = 31 * result + (booleanValue ? 1 : 0);
        result = 31 * result + (intValue != null ? intValue.hashCode() : 0);
        result = 31 * result + (idAuxiliary != null ? idAuxiliary.hashCode() : 0);
        result = 31 * result + (idExtern != null ? idExtern.hashCode() : 0);
        result = 31 * result + (idConceptoSct != null ? idConceptoSct.hashCode() : 0);
        result = 31 * result + (idConceptoStk != null ? idConceptoStk.hashCode() : 0);
        result = 31 * result + (idTargetType != null ? idTargetType.hashCode() : 0);
        return result;
    }
}
