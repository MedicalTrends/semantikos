package cl.minsal.semantikos.kernel.DAO.implementation;

import cl.minsal.semantikos.kernel.DAO.interfaces.DescriptionDAO;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.kernel.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by des01c7 on 01-07-16.
 */
public class DescriptionDAOImpl implements DescriptionDAO {

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

}
