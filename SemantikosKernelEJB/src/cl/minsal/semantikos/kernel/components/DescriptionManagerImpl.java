package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.kernel.daos.DescriptionDAO;
import cl.minsal.semantikos.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: REFACTOR THIS!!!
 */
@Stateless
public class DescriptionManagerImpl implements DescriptionManagerInterface {

    String user = "postgres";
    String password = "1q2w3e";

    @EJB
    DescriptionDAO descriptionDAO;

    @Override
    public void addDescriptionToConcept(String idConcept, String description, String type) {


/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call add_descripcion_concepto(?,?,?)}");

            call.setInt(1, Integer.parseInt(idConcept));
            call.setString(2, description);
            call.setInt(3,Integer.parseInt(type));

            call.execute();
            conne.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/

    }



    @Override
    public String getIdDescription(String tipoDescription) {

/*
        String idDescription=null;
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call get_description_type_by_name(?)}");
            call.setString(1, tipoDescription);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                idDescription = rs.getString("iddescriptiontype");
            }
            conne.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        return idDescription;
*/
        return null;

    }

    @Override
    public List<DescriptionType> getAllTypes() {

        return DescriptionTypeFactory.getInstance().getDescriptionTypes();

    }

    @Override
    public List<Description> findDescriptionsByConcept(int idConcept) {

        /*
        DAODescriptionImpl DAOdescription= new DAODescriptionImpl();

        return DAOdescription.getDescriptionBy(idConcept);

        */


        return null;
    }


    @Override
    public DescriptionType getTypeFSN() {
        return DescriptionTypeFactory.getInstance().getFSNDescriptionType();
    }

    @Override
    public DescriptionType getTypeFavorite() {
        return DescriptionTypeFactory.getInstance().getFavoriteDescriptionType();
    }

    @Override
    public List<Description> getDescriptionsOf(ConceptSMTK concept) {
        return descriptionDAO.getDescriptionsByConceptID(concept.getId());
    }
}
