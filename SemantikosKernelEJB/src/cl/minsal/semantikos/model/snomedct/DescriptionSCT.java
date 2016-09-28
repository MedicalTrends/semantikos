package cl.minsal.semantikos.model.snomedct;

import cl.minsal.semantikos.model.PersistentEntity;

import java.sql.Timestamp;

/**
 * @author Andrés Farías on 9/26/16.
 */
public class DescriptionSCT extends PersistentEntity {

    private DescriptionSCTType type;

    private String term;

    private final Timestamp effectiveTime;
    private final boolean active;
    private final long moduleID;
    private final long conceptID;
    private final String languageCode;
    private final long caseSignificanceID;
    private boolean favourite;

    public DescriptionSCT(long id, DescriptionSCTType type, String term, Timestamp effectiveTime, boolean active, long moduleID, long conceptID, String languageCode, long caseSignificanceID, boolean favourite) {
        super(id);

        this.type = type;
        this.term = term;
        this.effectiveTime = effectiveTime;
        this.active = active;
        this.moduleID = moduleID;
        this.conceptID = conceptID;
        this.languageCode = languageCode;
        this.caseSignificanceID = caseSignificanceID;
        this.favourite = favourite;
    }

    public DescriptionSCTType getType() {
        return type;
    }

    public void setType(DescriptionSCTType type) {
        this.type = type;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public enum DescriptionSCTType {
        FSN, SYNONYM;
    }
}

