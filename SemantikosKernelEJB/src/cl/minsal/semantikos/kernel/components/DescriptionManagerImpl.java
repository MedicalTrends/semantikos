package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.kernel.daos.DescriptionDAO;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.businessrules.DescriptionEditionBR;
import cl.minsal.semantikos.model.businessrules.DescriptionMovementBR;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static cl.minsal.semantikos.model.DescriptionType.PREFERIDA;

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
    public void updateDescription(@NotNull ConceptSMTK conceptSMTK, @NotNull Description initDescription, @NotNull Description finalDescription, @NotNull User user) {

        /* Se aplican las reglas de negocio */
        new DescriptionEditionBR().applyRules(initDescription, finalDescription);

        /* Y se actualizan */
        descriptionDAO.invalidate(initDescription);
        descriptionDAO.persist(finalDescription, conceptSMTK, user);

        /* Registrar en el Historial si es preferida (Historial BR) */
        if (initDescription.getDescriptionType().equals(PREFERIDA)) {
            auditManager.recordFavouriteDescriptionUpdate(conceptSMTK, initDescription, user);
        }
    }


    /**
     * Este método es responsable de aplicar las actualizaciones. Para actualizar una descripción se revisan las
     * marcadas para actualizar. La descripción <em>original</em> tiene un campo que indica que debe ser actualizado
     * <code>isToBeUpdated</code>. Para cada una debe existir otra descripción con el mismo DESCRIPTION_ID que tiene
     * los
     * cambios, pero que no es persistente.
     *
     * <p>
     * Este método itera sobre las descripciones marcadas para ser actualizadas, busca su par, y si existe:
     * <ul>
     * <li>Aplica reglas de negocio para validar que esté en orden</li>
     * <li>y deja inválida la original, y persiste la nueva.</li>
     * </ul>
     * </p>
     *
     * @param conceptSMTK El concepto cuyas descripciones se quieren actualizar.
     */
    private void updateDescriptions(ConceptSMTK conceptSMTK, User user) {

        // TODO: Probar esto algun dia:
        List<Description> descriptions = conceptSMTK.getDescriptions();
        for (Description description : descriptions) {

            Description original = description;
            /* Se buscan las descripciones a actualizar */
            if (description.isToBeUpdated()) {

                /* Una vez encontrada se busca a su hermana que tiene los nuevos valores */
                Description changed = conceptSMTK.getDescriptionByDescriptionID(original.getDescriptionId(), true);


            }
        }
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
