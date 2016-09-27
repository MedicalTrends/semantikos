package cl.minsal.semantikos.model.snomedct;

/**
 * @author Andrés Farías on 9/26/16.
 */
public class DescriptionSCT {

    private DescriptionSCTType type;
    private String term;
    private boolean favourite;

    public DescriptionSCT(DescriptionSCTType type, String term, boolean favourite) {
        this.type = type;
        this.term = term;
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
}

enum DescriptionSCTType {
    FSN, SYNONYM;
}