package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.snomedct.RelationshipSCT;
import cl.minsal.semantikos.model.snomedct.SnapshotProcessingResult;
import cl.minsal.semantikos.model.snomedct.SnomedCTSnapshot;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Andrés Farías on 9/26/16.
 */
public class SnomedCTManagerImpl implements SnomedCTManager {

    @Override
    public SnapshotProcessingResult processSnapshot(SnomedCTSnapshot snomedCTSnapshot) {
        return new SnapshotProcessingResult();
    }

    @Override
    public List<RelationshipSCT> getRelationshipsFrom(long idConceptSCT) {
        return emptyList();
    }
}
