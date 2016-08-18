package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.snomedct.ConceptSCT;

import javax.ejb.Local;

/**
 * @author Andrés Farías
 */
@Local
public interface ConceptSCTDAO {

    /**
     * Este método es responsable de recuperar un concepto CST a partir de un id <code>idConceptCST</code>.
     *
     * @param idConceptCST El identificador único de la base de datos.
     *
     * @return Una instancia fresca creada a partir de la entidad en la base de datos.
     */
    ConceptSCT getConceptCSTByID(long idConceptCST);
}
