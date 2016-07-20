package cl.minsal.semantikos.kernel.daos;


import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import cl.minsal.semantikos.model.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 13-07-16.
 */

@Stateless
public class StateMachineDAOImpl implements StateMachineDAO {


    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;


    @Override
    public ConceptStateMachine initConceptStateMachine() {

        ConnectionBD connect = new ConnectionBD();

        ObjectMapper mapper = new ObjectMapper();

        ConceptStateMachine conceptStateMachine= ConceptStateMachine.getInstance();

        try {

            CallableStatement call = connect.getConnection().prepareCall("{call semantikos.get_concept_state_machine()}");

            call.execute();

            ResultSet rs = call.getResultSet();

            while (rs.next()) {
                String resultJSON = rs.getString(1);

                conceptStateMachine = mapper.readValue(StringUtils.underScoreToCamelCaseJSON(resultJSON), ConceptStateMachine.class);

                //System.out.println("conceptStateMachine.getTransitions().get(0).getTargetState()="+conceptStateMachine.getTransitions().get(0).getTargetState());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connect.closeConnection();

        return conceptStateMachine;
    }
}
