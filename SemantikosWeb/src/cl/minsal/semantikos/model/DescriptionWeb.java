package cl.minsal.semantikos.model;


public class DescriptionWeb extends Description {

    public boolean hasBeenModified;

    /** Este atributo corresponde al valor de la descripción en la vista */
    public String representation;

    public DescriptionWeb(){
        super(null,"", DescriptionTypeFactory.TYPELESS_DESCRIPTION_TYPE);
        this.representation = "";
    }

    public DescriptionWeb(Description d) {
        super(d.getConceptSMTK(), d.getDescriptionId(), d.getDescriptionType(), d.getTerm(), d.isCaseSensitive(), d.isAutogeneratedName(), d.isPublished(), d.getValidityUntil(), d.getCreationDate(), d.getCreatorUser());
        this.hasBeenModified = false;
        this.representation = "";
    }

    public DescriptionWeb(ConceptSMTKWeb concept, Description d) {
        super(d.getConceptSMTK(), d.getDescriptionId(), d.getDescriptionType(), d.getTerm(), d.isCaseSensitive(), d.isAutogeneratedName(), d.isPublished(), d.getValidityUntil(), d.getCreationDate(), d.getCreatorUser());
        this.hasBeenModified = false;
        this.representation = "";
        super.setConceptSMTK(concept);
    }

    public DescriptionWeb(long id, Description d) {
        this(d);
        this.setId(id);
    }

    public DescriptionWeb(ConceptSMTKWeb concept, long id, Description d) {
        this(d);
        this.setId(id);
        super.setConceptSMTK(concept);
    }

    public DescriptionWeb(ConceptSMTKWeb concept, String term, DescriptionType typeFSN) {
        super(concept, term, typeFSN);
    }

    public boolean hasBeenModified() {
        return hasBeenModified;
    }

    public void setModified(boolean hasBeenModified) {
        this.hasBeenModified = hasBeenModified;
    }

    public void restore(DescriptionWeb descriptionWeb) {

        super.setAutogeneratedName(descriptionWeb.isAutogeneratedName());
        super.setTerm(descriptionWeb.getTerm());
        super.setDescriptionType(descriptionWeb.getDescriptionType());
        super.setCaseSensitive(descriptionWeb.isCaseSensitive());
        this.setRepresentation(descriptionWeb.getRepresentation());
    }

    @Override
    public void setTerm(String term) {
        super.setTerm(term.trim());
    }

    @Override
    public void setCaseSensitive(boolean caseSensitive) {
        super.setCaseSensitive(caseSensitive);
    }

    @Override
    public void setAutogeneratedName(boolean autogeneratedName) {
        super.setAutogeneratedName(autogeneratedName);
    }

    @Override
    public void setDescriptionType(DescriptionType descriptionType) {
        if(descriptionType == null)
            return;

        for (Description description : this.getConceptSMTK().getValidDescriptions()) {
            if(description.getDescriptionType().equals(descriptionType) && descriptionType.getName().equalsIgnoreCase("abreviada"))
                return;
        }

        super.setDescriptionType(descriptionType);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this)return true;


        Description description = (Description) o;

        DescriptionWeb descriptionWeb = new DescriptionWeb(description.getId(), description);

        return this.getTerm().equals(descriptionWeb.getTerm()) && this.isCaseSensitive()==descriptionWeb.isCaseSensitive() && this.isAutogeneratedName()==descriptionWeb.isAutogeneratedName() && this.getDescriptionType().equals(descriptionWeb.getDescriptionType());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getTerm().hashCode();
        result = 31 * result + new Boolean(isCaseSensitive()).hashCode();
        result = 31 * result + new Boolean(isAutogeneratedName()).hashCode();
        result = 31 * result + getDescriptionType().hashCode();
        return result;
    }

    public boolean isFsnType(){
        return (this.getDescriptionType()==DescriptionTypeFactory.getInstance().getFSNDescriptionType());
    }

    public boolean isFavoriteType(){
        return (this.getDescriptionType()==DescriptionTypeFactory.getInstance().getFavoriteDescriptionType());
    }

    public String getRepresentation() {
        return super.toString();
    }

    public void setRepresentation(String representation) {
        super.setTerm(representation.trim().replaceAll("\\(.+?\\)","").trim());
        //super.setTerm(representation.trim());
        this.representation = representation;
    }
}