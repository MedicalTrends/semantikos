package cl.minsal.semantikos.kernel.components;


import cl.minsal.semantikos.kernel.daos.DescriptionDAO;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.businessrules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static cl.minsal.semantikos.model.DescriptionType.PREFERIDA;
import static java.lang.System.currentTimeMillis;

/**
 * @author Andrés Farías
 */
@Stateless
public class DescriptionManagerImpl implements DescriptionManager {

    private static final Logger logger = LoggerFactory.getLogger(DescriptionManagerImpl.class);

    @EJB
    DescriptionDAO descriptionDAO;

    @EJB
    CategoryManager categoryManager;

    @EJB
    private AuditManagerInterface auditManager;

    /* El conjunto de reglas de negocio para validar creación de descripciones */
    private DescriptionCreationBR descriptionCreationBR = new DescriptionCreationBR();

    @Override
    public void createDescription(Description description, User user) {

        /* Reglas de negocio previas */
        ConceptSMTK conceptSMTK = description.getConceptSMTK();
        new DescriptionCreationBR().applyRules(conceptSMTK, description.getTerm(), description.getDescriptionType(), user, categoryManager);

        if (!description.isPersistent()){
            descriptionDAO.persist(description, user);
        }

        /* Si el concepto al cual se agrega la descripción está modelado, se registra en el historial */
        if (conceptSMTK.isModeled()){
            auditManager.recordDescriptionCreation(description, user);
        }

    }

    @Override
    public Description bindDescriptionToConcept(ConceptSMTK concept, String term, DescriptionType descriptionType, User user) {

        /* Se aplican las reglas de negocio para crear la Descripción*/
        descriptionCreationBR.applyRules(concept, term, descriptionType, user, categoryManager);

        /* Se crea la descripción */
        Description description = new Description(concept, term, descriptionType);

        /* Se aplican las reglas de negocio para crear la Descripción y se persiste y asocia al concepto */
        new DescriptionBindingBR().applyRules(concept, description, user);
        descriptionDAO.persist(description, user);

        /* Se retorna la descripción persistida */
        return description;
    }

    @Override
    public Description bindDescriptionToConcept(ConceptSMTK concept, Description description, User user) {

        /* Si la descripción no se encontraba persistida, se persiste primero */
        if (!description.isPersistent()){
            descriptionCreationBR.applyRules(concept, description.getTerm(), description.getDescriptionType(), user, categoryManager);
            descriptionDAO.persist(description, user);
        }

        /* Se aplican las reglas de negocio para crear la Descripción y se persiste y asocia al concepto */
        new DescriptionBindingBR().applyRules(concept, description, user);

        /* Se hace la relación a nivel lógico del modelo */
        if(!concept.getDescriptions().contains(description)){
            concept.addDescription(description);
        }

        /* Y se persiste la nueva relación a nivel de la descripción */
        descriptionDAO.bind(description, concept, user);

        /* Se retorna la descripción persistida */
        return description;
    }

    @Override
    public Description unbindDescriptionToConcept(ConceptSMTK concept, Description description, User user) {

        /* Si la descripción no se encontraba persistida, se persiste primero */
        if (!description.isPersistent()){
            return description;
        }

        /* Se validan las reglas de negocio para eliminar una descripción */
        new DescriptionUnbindingBR().applyRules(concept, description, user);

        /* Se establece la fecha de vigencia y se actualiza la descripción */
        description.setActive(false);
        description.setValidityUntil(new Timestamp(currentTimeMillis()));
        descriptionDAO.update(description);

        /* Se retorna la descripción actualizada */
        return description;
    }

    @Override
    public void updateDescription(@NotNull ConceptSMTK conceptSMTK, @NotNull Description initDescription, @NotNull Description finalDescription, @NotNull User user) {

        logger.info("Se actualizan descripciones. \nOriginal: " + initDescription + "\nFinal: " + finalDescription);

        /* Se aplican las reglas de negocio */
        new DescriptionEditionBR().applyRules(initDescription, finalDescription);

        /* Y se actualizan */
        descriptionDAO.invalidate(initDescription);

        descriptionDAO.persist(finalDescription, user);
        descriptionDAO.bind(finalDescription, conceptSMTK, user);

        /* Registrar en el Historial si es preferida (Historial BR) */
        if (conceptSMTK.isModeled() && initDescription.getDescriptionType().equals(PREFERIDA)) {
            auditManager.recordFavouriteDescriptionUpdate(conceptSMTK, initDescription, user);
        }
    }


    @Override
    public void deleteDescription(ConceptSMTK conceptSMTK, Description description, User user) {

        /* Eliminar una descripción consiste en dejarla inválida */
        descriptionDAO.invalidate(description);

        /* Se registra en el Historial si el concepto está modelado */
        if (conceptSMTK.isModeled()) {
            auditManager.recordDescriptionDeletion(conceptSMTK, description, user);
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
    public void moveDescriptionToConcept(ConceptSMTK targetConcept, Description description, User user) {

        /* Se aplican las reglas de negocio para el traslado */
        DescriptionTranslationBR descriptionTranslationBR = new DescriptionTranslationBR();
        descriptionTranslationBR.validatePreConditions(description, targetConcept);

        /* Se realiza la actualización a nivel del modelo lógico */
        ConceptSMTK sourceConcept = description.getConceptSMTK();
        List<Description> sourceConceptDescriptions = sourceConcept.getDescriptions();

        /* Se elimina la descripción del objeto base */
        if(sourceConceptDescriptions.contains(description)){
            sourceConceptDescriptions.remove(description);
        }

        /* Se agrega al concepto destino */
        if(!targetConcept.getDescriptions().contains(description)){
            targetConcept.addDescription(description);
        }

        /* Se actualiza el concepto dueño de la descripción en la descripción */
        description.setConceptSMTK(targetConcept);

        /* Se aplican las reglas de negocio asociadas al movimiento de un concepto */
        descriptionTranslationBR.apply(targetConcept, description);

        /* Luego se persiste el cambio */
        descriptionDAO.bind(description, targetConcept, user);

        /* Se registra en el Audit el traslado */
        auditManager.recordDescriptionMovement(sourceConcept, targetConcept, description, user);
    }

    @Override
    public String getIdDescription(String tipoDescription) {

/*      TODO: Reparar esto.
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
        return descriptionDAO.getDescriptionsByConcept(concept);
    }

    @Override
    public String generateDescriptionId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public List<Description> searchDescriptionsByTerm(String term, List<Category> categories) {
        return descriptionDAO.searchDescriptionsByTerm(term, categories);
    }
}
