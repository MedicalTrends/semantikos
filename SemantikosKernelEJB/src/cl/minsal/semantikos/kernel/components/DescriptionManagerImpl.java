package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.kernel.daos.DescriptionDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.DescriptionType;
import cl.minsal.semantikos.model.DescriptionTypeFactory;
import cl.minsal.semantikos.model.businessrules.DescriptionMovementBR;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.UUID;

import static cl.minsal.semantikos.model.DescriptionType.ABREVIADA;
import static cl.minsal.semantikos.model.DescriptionType.GENERAL;

/**
 * @author Andrés Farías
 */
@Stateless
public class DescriptionManagerImpl implements DescriptionManagerInterface {

    @EJB
    DescriptionDAO descriptionDAO;

    @EJB
    private AuditManagerInterface auditManager;

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
    public void moveDescriptionToConcept(ConceptSMTK sourceConcept, ConceptSMTK targetConcept, Description description) {

        /* Se aplican las reglas de negocio para el traslado */
        new DescriptionMovementBR().apply(sourceConcept, targetConcept, description);

        /* Se registra en el Audit el traslado */
        auditManager.recordDescriptionMovement(sourceConcept, targetConcept, description);

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

    @Override
    public String generateDescriptionId() {
        return UUID.randomUUID().toString();
    }
}
