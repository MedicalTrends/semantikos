package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.audit.AuditableEntity;

import static cl.minsal.semantikos.kernel.daos.ConceptDAO.NON_PERSISTED_ID;

/**
 * @author Andrés Farías on 08-07-16.
 */
public class Description implements AuditableEntity {

    /** ID en la base de datos */
    private long id;

    /** El término que representa esta descripción */
    private String term;

    /** El DESCRIPTION_ID (valor de negocio) de la descripción */
    private String descriptionID;

    private DescriptionType idDescriptionType;

    private boolean isCaseSensitive;

    private final boolean isAutoGenerated;

    private boolean autogeneratedName;

    private boolean isActive;

    private boolean isPublished;

    /** El tipo de descriptor */
    private DescriptionType descriptionType;

    // El estado del descriptor
    private IState state;

    /**
     *
     * @param id
     * @param descriptionID
     * @param descriptionType
     * @param term
     * @param isCaseSensitive
     * @param isAutoGenerated
     * @param isActive
     * @param isPublished
     */
    public Description(long id, String descriptionID, DescriptionType descriptionType, String term, boolean isCaseSensitive, boolean isAutoGenerated, boolean isActive, boolean isPublished) {

        this.id = id;
        this.descriptionID = descriptionID;
        this.term = term;
        this.descriptionType = descriptionType;

        this.isCaseSensitive = isCaseSensitive;
        this.isAutoGenerated = isAutoGenerated;
        this.isActive = isActive;
        this.isPublished = isPublished;
    }

    /**
     * Un constructor minimalista para la Descripción.
     * @param term El término de la descripción.
     * @param descriptionType El tipo de la descripción.
     */
    public Description(String term, DescriptionType descriptionType) {
        this(-1, "NULL", descriptionType, term, false, false, false, false);
    }

    /**
     * Este método determina si esta descrición está persistida o no
     * @return Un <code>java.lang.boolean</code>
     */
    public boolean isPersisted(){
        return (this.id != NON_PERSISTED_ID);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescriptionId() {
        return descriptionID;
    }

    public void setDescriptionId(String idDescription) {
        this.descriptionID = idDescription;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.isCaseSensitive = caseSensitive;
    }

    public boolean isAutogeneratedName() {
        return autogeneratedName;
    }

    public void setAutogeneratedName(boolean autogeneratedName) {
        this.autogeneratedName = autogeneratedName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public DescriptionType getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(DescriptionType descriptionType) {
        this.descriptionType = descriptionType;
    }

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }

}
