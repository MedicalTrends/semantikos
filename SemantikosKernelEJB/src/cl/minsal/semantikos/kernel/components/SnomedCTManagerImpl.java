package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.SnomedCTDAO;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.TagSMTK;
import cl.minsal.semantikos.model.snomedct.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Andrés Farías on 9/26/16.
 */
@Stateless
public class SnomedCTManagerImpl implements SnomedCTManager {

    @EJB
    private SnomedCTDAO snomedctDAO;

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

    @Override
    public List<ConceptSCT> findConceptsBy(String pattern) {
        return snomedctDAO.findConceptsBy(pattern);
    }

    @Override
    public ConceptSCT getConceptByID(long conceptID) {
        return snomedctDAO.getConceptByID(conceptID);
    }
}
