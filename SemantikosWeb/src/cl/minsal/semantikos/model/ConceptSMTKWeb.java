package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 8/5/16.
 */
public class ConceptSMTKWeb extends ConceptSMTK {

    public ConceptSMTKWeb(long id, long conceptID, Category category, boolean isToBeReviewed, boolean isToBeConsultated, State state, boolean isFullyDefined, boolean isPublished, List<Description> descriptions) {
        super(id, conceptID, category, isToBeReviewed, isToBeConsultated, state, isFullyDefined, isPublished, descriptions);
    }

    public ConceptSMTKWeb() {
    }

    /**
     *
     * @param category
     * @param favouriteDescription
     * @param initialState
     */
    public ConceptSMTKWeb(Category category, Description favouriteDescription, State initialState) {
        this(category, favouriteDescription, new Description("", DescriptionTypeFactory.getInstance().getFSNDescriptionType()));

        this.setState(initialState);
    }

    public ConceptSMTKWeb(Category category, Description fsn, Description preferido) {
        super(category, fsn, preferido);
    }

    public ConceptSMTKWeb(Category category, Description fsn, Description preferido, State state) {
        super(category, fsn, preferido, state);
    }

    /**
     * Este método restorna todas ls descripciones que no son FSN o Preferidas.
     *
     * @return Una lista con todas las descripciones no FSN o Preferidas.
     */
    public List<Description> getDescriptionsButFSNandFavorite() {

        List<Description> otherDescriptions = new ArrayList<Description>();
        DescriptionType fsnType = DescriptionTypeFactory.getInstance().getFSNDescriptionType();
        DescriptionType favoriteType = DescriptionTypeFactory.getInstance().getFavoriteDescriptionType();

        for (Description description : getDescriptions()) {
            if (!description.getDescriptionType().equals(fsnType)
                    && description.getDescriptionType().equals(favoriteType)) {
                otherDescriptions.add(description);
            }
        }

        return otherDescriptions;
    }
}
