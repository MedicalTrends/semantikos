package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.kernel.util.ConnectionBD;
import cl.minsal.semantikos.model.ChangeType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static cl.minsal.semantikos.kernel.util.StringUtils.underScoreToCamelCaseJSON;
import static java.util.Collections.emptyList;

/**
 * @author Andrés Farías on 8/23/16.
 */
@Stateless
public class AuditDAOImpl implements AuditDAO {


    private static final Logger logger = LoggerFactory.getLogger(AuditDAOImpl.class);


    @Override
    public List<ChangeType> getAllChangeTypes() {


        ConnectionBD connect = new ConnectionBD();
        String sqlQuery = "{call semantikos.get_all_changes_types()}";
        ObjectMapper mapper = new ObjectMapper();

        ChangeType[] changeTypes = new ChangeType[0];
        try (Connection connection = connect.getConnection();
             CallableStatement call = connection.prepareCall(sqlQuery)) {

            call.execute();

            ResultSet rs = call.getResultSet();
            if (rs.next()) {
                String jsonExpression = rs.getString(1);
                changeTypes = mapper.readValue(underScoreToCamelCaseJSON(jsonExpression), ChangeType[].class);
            }
            rs.close();


        } catch (SQLException e) {
            String errorMsg = "Erro al invocar get_all_changes_types()";
            logger.error(errorMsg, e);
            throw new EJBException(errorMsg, e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ChangeType> changeTypeList = new ArrayList<>();
        for (ChangeType changeType : changeTypes) {
            changeTypeList.add(new ChangeType(changeType.getId(),changeType.getName()));
        }


        return changeTypeList;
    }
}
