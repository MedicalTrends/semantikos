package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.model.DescriptionTypeFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;

/**
 * Created by des01c7 on 01-07-16.
 */
@Stateless
@Startup
public class DescriptionDAOImpl implements DescriptionDAO {

    /** El logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(DescriptionDAOImpl.class);

    @PersistenceContext(unitName = "SEMANTIKOS_PU")
    private EntityManager em;

    @PostConstruct
    private void init() {
        this.refreshDescriptionTypes();
    }

    @Override
    public List<DescriptionType> getDescriptionTypes() {

        ConnectionBD connect = new ConnectionBD();
        ObjectMapper mapper = new ObjectMapper();
        DescriptionType[] descriptionTypes = new DescriptionType[0];
        String sql = "{call semantikos.get_all_description_types()}";
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql);) {

            call.execute();
            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                String resultJSON = rs.getString(1);
                descriptionTypes = mapper.readValue(underScoreToCamelCaseJSON(resultJSON), DescriptionType[].class);
            }
        } catch (SQLException e) {
            String errorMsg = "Error al recuperar descripciones de la BDD.";
            logger.error(errorMsg, e);
            throw new EJBException(e);
        } catch (IOException e) {
            String errorMsg = "Error al parsear los objetos a JSON.";
            logger.error(errorMsg, e);
            throw new EJBException(e);
        }

        return Arrays.asList(descriptionTypes);
    }

    // TODO: Implementar este método
    @Override
    public List<Description> getDescriptionBy(int id) {
        return null;
    }

    @Override
    public List<Description> getDescriptionsByConceptID(long id) {

        ConnectionBD connect = new ConnectionBD();
        ObjectMapper mapper = new ObjectMapper();

        List<Description> descriptions = new ArrayList<>();
        String sql = "{call semantikos.get_descriptions_by_idconcept(?)}";
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql);) {

            call.setLong(1, id);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                String resultJSON = rs.getString(1);
                Description description = mapper.readValue(underScoreToCamelCaseJSON(resultJSON), Description.class);
                descriptions.add(description);
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar descripciones de la BDD.";
            logger.error(errorMsg, e);
            throw new EJBException(e);
        } catch (IOException e) {
            String errorMsg = "Error al parsear los objetos a JSON.";
            logger.error(errorMsg, e);
            throw new EJBException(e);
        }

        return descriptions;
    }

    @Override
    public DescriptionTypeFactory refreshDescriptionTypes() {

        ConnectionBD connect = new ConnectionBD();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, DescriptionType> descriptionTypes = new HashMap<>();
        String sql = "{call semantikos.get_description_types()}";
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sql);) {

            call.execute();
            ResultSet rs = call.getResultSet();

            /* Se recuperan los description types */
            while (rs.next()) {
                String resultJSON = rs.getString(1);
                DescriptionTypeDTO descriptionType = mapper.readValue(underScoreToCamelCaseJSON(resultJSON), DescriptionTypeDTO.class);
                descriptionTypes.put(descriptionType.getName().toUpperCase(), descriptionType.getDescriptionType());
            }

            DescriptionTypeFactory.getInstance().setDescriptionTypes(descriptionTypes);
        } catch (SQLException e) {
            String errorMsg = "Error al intentar recuperar Description Types de la BDD.";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        } catch (IOException e) {
            String errorMsg = "Error al intentar parsear Description Types en JSON.";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        }

        // TODO: Hacer el caso de prueba de JSON.
        return DescriptionTypeFactory.getInstance();
    }
}

class DescriptionTypeDTO {

    private long id;
    private String name;
    private String description;

    public DescriptionTypeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DescriptionType getDescriptionType(){
        return new DescriptionType(this.id, this.name, this.description);
    }
}
