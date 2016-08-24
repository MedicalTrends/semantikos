package cl.minsal.semantikos.model.relationships;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.kernel.daos.ConceptSCTDAO;
import cl.minsal.semantikos.kernel.daos.HelperTableDAO;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import java.io.IOException;
import java.sql.Timestamp;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * @author Andrés Farías
 */
@Singleton
public class TargetFactory {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(TargetFactory.class);

    @EJB
    private HelperTableDAO helperTableDAO;

    @EJB
    private ConceptSCTDAO conceptSCTDAO;

    @EJB
    private ConceptDAO conceptDAO;

    /**
     * Este método es responsable de reconstruir un Target a partir de una expresión JSON, yendo a buscar los otros
     * objetos necesarios.
     *
     * @param jsonResult Una expresión JSON de la forma {"id":1,"float_value":null,"date_value":null,"string_value":"strig","boolean_value":null,"int_value":null,"id_auxiliary":null,"id_extern":null,"id_concept_sct":null,"id_concept_stk":null,"id_target_type":null}
     *
     * @return Una instancia fresca y completa
     */
    public Target createTargetFromJSON(String jsonResult) {
        TargetDTO targetDTO;
        ObjectMapper mapper = new ObjectMapper();
        try {
            targetDTO = mapper.readValue(underScoreToCamelCaseJSON(jsonResult), TargetDTO.class);
        } catch (IOException e) {
            String errorMsg = "Error al parsear un JSON hacia un targetDTO";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        Target target;
        long idHelperTableRecord = targetDTO.getIdAuxiliary();
        long idConceptSct = targetDTO.getIdConceptSct();
        long idConceptStk = targetDTO.getIdConceptStk();

        /* Se evalúa caso a caso. Helper Tables: */
        if (idHelperTableRecord > 0) {
            target = helperTableDAO.getHelperTableRecordFromId(idHelperTableRecord);
        } else if (idConceptSct > 0) {
            target = conceptSCTDAO.getConceptCSTByID(idConceptSct);
        } else if (idConceptStk > 0) {
            target = conceptDAO.getConceptByID(idConceptStk);
        }
        /* Ahora los tipos básicos */
        else {
            Float floatValue = targetDTO.getFloatValue();
            Integer intValue = targetDTO.getIntValue();
            Boolean boolValue = targetDTO.isBooleanValue();
            String stringValue = targetDTO.getStringValue();
            Timestamp dateValue = targetDTO.getDateValue();

            if (floatValue != null) {
                target = new BasicTypeValue<Float>(floatValue);
            } else if (intValue != null) {
                target = new BasicTypeValue<Integer>(intValue);
            } else if (boolValue != null) {
                target = new BasicTypeValue<Boolean>(boolValue);
            } else if (stringValue != null) {
                target = new BasicTypeValue<String>(stringValue);
            } else if (dateValue != null) {
                target = new BasicTypeValue<Timestamp>(dateValue);
            } else {
                String message = "Existe un caso no contemplado";
                logger.error(message);
                throw new EJBException(message);
            }
        }

        return target;
    }

}

class TargetDTO {
    private Long id;
    private Float floatValue;
    private Timestamp dateValue;
    private String stringValue;
    private boolean booleanValue;
    private Integer intValue;
    private long idAuxiliary;
    private long idExtern;
    private long idConceptSct;
    private long idConceptStk;
    private long idTargetType;

    public TargetDTO() {
    }

    public long getIdConceptStk() {
        return idConceptStk;
    }

    public void setIdConceptStk(long idConceptStk) {
        this.idConceptStk = idConceptStk;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public long getIdAuxiliary() {
        return idAuxiliary;
    }

    public void setIdAuxiliary(long idAuxiliary) {
        this.idAuxiliary = idAuxiliary;
    }

    public long getIdExtern() {
        return idExtern;
    }

    public void setIdExtern(long idExtern) {
        this.idExtern = idExtern;
    }

    public long getIdConceptSct() {
        return idConceptSct;
    }

    public void setIdConceptSct(long idConceptSct) {
        this.idConceptSct = idConceptSct;
    }

    public long getIdTargetType() {
        return idTargetType;
    }

    public void setIdTargetType(long idTargetType) {
        this.idTargetType = idTargetType;
    }

    @Override
    public String toString() {
        return "TargetDTO{" +
                "id=" + id +
                ", floatValue=" + floatValue +
                ", dateValue=" + dateValue +
                ", stringValue='" + stringValue + '\'' +
                ", booleanValue=" + booleanValue +
                ", intValue=" + intValue +
                ", idAuxiliary=" + idAuxiliary +
                ", idExtern=" + idExtern +
                ", idConceptSct=" + idConceptSct +
                ", idConceptStk=" + idConceptStk +
                ", idTargetType=" + idTargetType +
                '}';
    }
}
