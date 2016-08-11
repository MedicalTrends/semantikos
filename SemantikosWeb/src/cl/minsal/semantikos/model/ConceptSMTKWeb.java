package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Diego Soto
 */
public class ConceptSMTKWeb extends ConceptSMTK {

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

    public ConceptSMTKWeb(Category category, Description fsn, Description favorite) {
        super(category, fsn, favorite);
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
