package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrés Farías on 10/24/16.
 */
public class NoValidDescription {

    /** La descripción que no es válida */
    private Description noValidDescription;

    /** La observación que indica por qué la descripción es propuesta como no válida */
    private String observation;

    /** Conceptos sugeridos como alternativa a la descripción */
    private List<ConceptSMTK> suggestedConcepts;

    public NoValidDescription(Description noValidDescription, String observation) {
        this.noValidDescription = noValidDescription;
        this.observation = observation;
        this.suggestedConcepts = new ArrayList<>();
    }

    public NoValidDescription(Description noValidDescription, String observation, List<ConceptSMTK> suggestedConcepts) {
        this(noValidDescription, observation);
        this.suggestedConcepts.addAll(suggestedConcepts);
    }

    public Description getNoValidDescription() {
        return noValidDescription;
    }

    public String getObservation() {
        return observation;
    }

    public List<ConceptSMTK> getSuggestedConcepts() {
        return suggestedConcepts;
    }

    public void addSuggestedConcept(ConceptSMTK conceptSMTK) {
        this.suggestedConcepts.add(conceptSMTK);
    }

    public void addSuggestedConcepts(List<ConceptSMTK> conceptSMTKs) {
        this.suggestedConcepts.addAll(conceptSMTKs);
    }
}
