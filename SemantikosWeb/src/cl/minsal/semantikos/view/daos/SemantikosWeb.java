package cl.minsal.semantikos.view.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJBException;
import javax.ejb.Local;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * @author Andrés Farías on 10/5/16.
 */
@Local
public interface SemantikosWeb {

    /**
     * Este método es responsable de recuperar la información del objeto composite asociado a un
     * RelationshipDefinition.
     *
     * @param relationshipDefinition El RelationshipDefinition que requiere ser completado.
     *
     * @return El Identificador del Composite asociado a dicho RelationshipDefinition.
     */
    public long getCompositeOf(RelationshipDefinition relationshipDefinition);
}
