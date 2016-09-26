package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.snomedct.RelationshipSCT;
import cl.minsal.semantikos.model.snomedct.SnapshotProcessingResult;
import cl.minsal.semantikos.model.snomedct.SnomedCTSnapshot;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Andrés Farías on 9/26/16.
 */
@Local
public interface SnomedCTManager {

    /**
     * Este método es responsable de procesar un snapshot de Snomed CT.
     *
     * @param snomedCTSnapshot El Snapshot que será procesado.
     *
     * @return El resultado del proceso.
     */
    public SnapshotProcessingResult processSnapshot(SnomedCTSnapshot snomedCTSnapshot);

    /**
     * Este método es responsable de recuperar las relaciones de un concepto SCT.
     *
     * @param idConceptSCT El Identificador único del concepto SCT.
     *
     * @return Una lista de relaciones Snomed-CT donde el concepto Snomed-CT está en el origen de las relaciones.
     */
    public List<RelationshipSCT> getRelationshipsFrom(long idConceptSCT);

}
