package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.snomedct.*;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Andrés Farías on 9/26/16.
 */
public class SnomedCTManagerImpl implements SnomedCTManager {

    @Override
    public SnapshotProcessingResult processSnapshot(SnomedCTSnapshot snomedCTSnapshot) {

        List<ConceptSCT> conceptSCTs = SnomedCTSnapshotFactory.getInstance().createConceptsSCTFromPath(snomedCTSnapshot.getConceptSnapshotPath());

        List<DescriptionSCT> descriptionSCTs = SnomedCTSnapshotFactory.getInstance().createDescriptionsSCTFromPath(snomedCTSnapshot.getDescriptionSnapshotPath());

        for (DescriptionSCT descriptionSCT : descriptionSCTs) {
            
        }

        return new SnapshotProcessingResult();
    }

    @Override
    public List<RelationshipSCT> getRelationshipsFrom(long idConceptSCT) {
        return emptyList();
    }
}
