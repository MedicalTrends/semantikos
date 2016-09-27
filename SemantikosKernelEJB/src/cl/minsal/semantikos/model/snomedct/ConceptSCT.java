package cl.minsal.semantikos.model.snomedct;

import cl.minsal.semantikos.model.PersistentEntity;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static cl.minsal.semantikos.model.snomedct.DescriptionSCTType.FSN;
import static cl.minsal.semantikos.model.snomedct.DescriptionSCTType.SYNONYM;

/**
 * Esta clase representa un concepto Snomed-CT.
 *
 * @author Andres Farias
 * @version 1.0
 * @created 17-ago-2016 12:52:05
 */
public class ConceptSCT extends PersistentEntity implements Target {

    /** Identificador único (oficial) de Snomed CT para este concepto. */
    private long idSnomedCT;

    /** Descripciones del Concepto */
    private List<DescriptionSCT> descriptions;

    private List<RelationshipSCT> relationships;

    /**
     * Definition: Specifies the inclusive date at which the component version's state became the then current valid
     * state of the component
     */
    private Timestamp effectiveTime;

    /**
     * <p></p>Si el concepto Snomed CT está vigente
     *
     * <p>Specifies whether the concept 's state was active or inactive from the nominal release date specified by the
     * effectiveTime</p>
     */
    private boolean isActive;

    /** <p>Identifies the concept version's module. Set to a descendant of |Module| within the metadata hierarchy.</p> */
    private long moduleId;

    /**
     * <p>Specifies if the concept version is primitive or fully defined. Set to a child of | Definition status | in
     * the
     * metadata hierarchy.</p>
     */
    private long definitionStatusId;

    public ConceptSCT() {
    }

    public ConceptSCT(long idSnomedCT, Timestamp effectiveTime, boolean isActive, long moduleId, long definitionStatusId) {
        this.idSnomedCT = idSnomedCT;
        this.effectiveTime = effectiveTime;
        this.isActive = isActive;
        this.moduleId = moduleId;
        this.definitionStatusId = definitionStatusId;
    }

    public void setIdSnomedCT(long idSnomedCT) {
        this.idSnomedCT = idSnomedCT;
    }

    public Timestamp getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Timestamp effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public long getDefinitionStatusId() {
        return definitionStatusId;
    }

    public void setDefinitionStatusId(long definitionStatusId) {
        this.definitionStatusId = definitionStatusId;
    }

    public long getIdSnomedCT() {
        return idSnomedCT;
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.SnomedCT;
    }

    @Override
    public String toString() {
        String toString = "Concepto " + this.idSnomedCT;

        /* Si el concepto tiene FSN se retorna esa descripción */
        if (this.getDescriptionFSN() != null) {
            return toString + " - " + this.getDescriptionFSN();
        }

        /* Si no tiene FSN se intenta con la preferida */
        else if (this.getDescriptionFavouriteSynonymous() != null) {
            return toString + " - " + this.getDescriptionFavouriteSynonymous();
        }

        return toString + " - Sin descripción FSN o Preferida";
    }

    private DescriptionSCT getDescriptionFavouriteSynonymous() {

        for (DescriptionSCT synonym : this.getDescriptionSynonymous()) {
            if (synonym.isFavourite()){
                return synonym;
            }
        }

        return null;
    }

    private List<DescriptionSCT> getDescriptionSynonymous() {
        List<DescriptionSCT> synonyms = new ArrayList<>();
        for (DescriptionSCT description : descriptions) {
            if (description.getType().equals(SYNONYM)) {
                synonyms.add(description);
            }
        }

        return synonyms;
    }

    private String getDescriptionFSN() {

        for (DescriptionSCT description : descriptions) {
            if (description.getType().equals(FSN)) {
                return description.getTerm();
            }
        }

        return null;
    }
}