package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.model.snomedct.ConceptSCT;
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

    /**
     * Este método es responsable de buscar aquellos conceptos que posean al menos una descripción cuyo término coincide
     * con el patrón dado como parámetro.
     *
     * @param patron El patrón de búsqueda.
     *
     * @return La lista de conceptos que satisfacen el criterio de búsqueda.
     */
    public List<ConceptSCT> findConceptsBy(String patron);
}
