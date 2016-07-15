package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import cl.minsal.semantikos.model.State;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by des01c7 on 01-07-16.
 */
@Stateless
public class DescriptionDAOImpl implements DescriptionDAO {


    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    EntityManager em;


    @Override
    public List<DescriptionType> getAllTypes() {

        ConnectionBD connect = new ConnectionBD();

        ObjectMapper mapper = new ObjectMapper();

        DescriptionType[] descriptionTypes= new DescriptionType[0];

        try {

            CallableStatement call = connect.getConnection().prepareCall("{call semantikos.get_all_description_types()}");

            call.execute();

            ResultSet rs = call.getResultSet();


            while (rs.next()) {
                String resultJSON = rs.getString(1);

                descriptionTypes = mapper.readValue(StringUtils.lowerCaseToCamelCaseJSON(resultJSON) , DescriptionType[].class);
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

        return Arrays.asList(descriptionTypes);
    }

    @Override
    public List<Description> getDescriptionBy(int id) {
        return null;
    }

    @Override
    public List<Description> getDescriptionByConceptID(long id) {


        Query q = em.createNativeQuery("select * from semantikos.get_descriptions_by_idconcept(?)");
        q.setParameter(1,id);
        List<Object[]> resultList = q.getResultList();

        List<Description> respuesta = new ArrayList<Description>();

        for (Object[] result:resultList) {



            DescriptionType descriptionType= new DescriptionType();
            descriptionType.setIdDescriptionType(((BigInteger)result[1]).longValue());
            descriptionType.setGlosa(((String)result[2]));

            Description description = new Description(((String)result[3]),descriptionType);
            description.setIdConcept(((BigInteger)result[0]).longValue());
            description.setCaseSensitive((boolean)result[4]);
            description.setCaseSensitive((boolean)result[5]);
            description.setActive((boolean)result[6]);
            description.setPublished((boolean)result[7]);

            respuesta.add(description);
        }

        return respuesta;

        /*
        * List<Description> descriptions= new ArrayList<>();
        conectionBD connect = new conectionBD();

        try {

            CallableStatement call = connect.getConnection().prepareCall("{call semantikos.get_descriptions_by_idconcept(?)}");
            call.setInt(1,id);
            call.execute();

            ResultSet rs = call.getResultSet();

            int idDescription;
            String descriptionType;
            String description;
            boolean caseSensitive;
            boolean nameAutogenerated;
            boolean isActive;
            boolean isPublished;

            while (rs.next()) {

                idDescription= Integer.parseInt(rs.getString("id_description"));
                descriptionType= rs.getString("description_type");
                description= rs.getString("description");
                caseSensitive= rs.getString("case_sensitive").equalsIgnoreCase("true")? true: false;
                nameAutogenerated= rs.getString("autogenerated").equalsIgnoreCase("true")? true: false;
                isActive= rs.getString("is_active").equalsIgnoreCase("true")? true: false;
                isPublished= rs.getString("is_published").equalsIgnoreCase("true")? true: false;

                descriptions.add(new Description(idDescription,descriptionType,description,caseSensitive,nameAutogenerated,isActive,isPublished));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        connect.closeConnection();
        return descriptions;
        * */


    }

    @Override
    public List<State> getAllStates() {

        ConnectionBD connect = new ConnectionBD();

        ObjectMapper mapper = new ObjectMapper();

        State[] states= new State[0];

        try {

            CallableStatement call = connect.getConnection().prepareCall("{call semantikos.get_all_states()}");

            call.execute();

            ResultSet rs = call.getResultSet();


            while (rs.next()) {
                String resultJSON = rs.getString(1);

                states = mapper.readValue(StringUtils.lowerCaseToCamelCaseJSON(resultJSON), State[].class);
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

        return Arrays.asList(states);
    }

}
