package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;
import cl.minsal.semantikos.model.IUser;
import cl.minsal.semantikos.model.businessrules.BusinessRulesContainer;
import cl.minsal.semantikos.model.businessrules.ConceptCreationBusinessRuleContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * @author Andrés Farías
 */
@Stateless
public class ConceptManagerImpl implements ConceptManagerInterface {

    /** El logger de la clase */
    private static final Logger logger = LoggerFactory.getLogger(ConceptManagerImpl.class);

    @EJB
    private ConceptDAO conceptDAO;

    @EJB
    private DescriptionManagerInterface descriptionManager;

    @EJB
    private StateMachineManagerInterface stateMachineManager;

    @EJB
    private AuditManagerInterface auditManager;



    @Override
    public ConceptSMTK getConceptByCONCEPT_ID(String conceptId) {

        /* Se recupera el concepto base (sus atributos) sin sus relaciones ni descripciones */
        ConceptSMTK concept = this.conceptDAO.getConceptByCONCEPT_ID(conceptId);

        /* Se completa el objeto con sus relaciones (relaciones y descripciones por el momento) faltantes. */
        this.refresh(concept);

        return concept;
    }

    @Override
    public ConceptSMTK getConceptByID(long id) {

        // TODO: Repetir lo que se hace en getConceptByCONCEPT_ID
        return this.conceptDAO.getConceptByID(id);
    }

    /**
     * Este método es responsable de sincronizar el concepto respecto a la base de datos,
     */
    private void refresh(ConceptSMTK concept) {

        /* Se refrescan las descripciones primero */
        List<Description> descriptions = this.descriptionManager.getDescriptionsOf(concept);
        concept.setDescriptions(descriptions);

        // TODO: Continuar.jajaja
    }

    @Override
    public List<ConceptSMTK> findConceptBy(String patternOrConceptID, Long[] categories, int pageNumber, int pageSize) {

        long MODELADO_VIGENTE= 3;
        long MODELADO_NO_VIGENTE= 4;

        Long[] states = {MODELADO_VIGENTE, MODELADO_NO_VIGENTE};

        categories = (categories==null)?new Long[0]:categories;

        patternOrConceptID = standardizationPattern(patternOrConceptID);
        String[] arrayPattern = patternToArray(patternOrConceptID);

        //Búsqueda por categoría y patron o concept ID
        if ((categories.length!=0 && patternOrConceptID != null)) {
            if (patternOrConceptID.length() >= 3) {
                if (arrayPattern.length >= 2) {
                    return conceptDAO.getConceptBy(arrayPattern, categories, states, pageSize, pageNumber);
                } else {
                    return conceptDAO.getConceptBy(arrayPattern[0], categories, pageNumber, pageSize, states);
                }
            }
        }

        //Búsqueda por patron o concept ID
        if ((categories.length==0 && patternOrConceptID != null)) {
            if (patternOrConceptID.length() >= 3) {
                if (arrayPattern.length >= 2) {
                    return conceptDAO.getConceptBy(arrayPattern, states, pageSize, pageNumber);
                } else {
                    return conceptDAO.getConceptBy(arrayPattern[0], categories, pageNumber, pageSize, states);
                }
            }

        }
        //Búsqueda por categoría

        if (categories.length > 0) {
            return conceptDAO.getConceptBy(categories, states, pageSize, pageNumber);
        }


        //Búsqueda por largo (PageSize y PageNumber)
        return conceptDAO.getConceptsBy(states, pageSize, pageNumber);
    }

    @Override
    public List<ConceptSMTK> findConceptBy(String pattern) {

        List<ConceptSMTK> conceptSMTKList = findConceptBy(pattern,new Long[0],0, countConceptBy(pattern,new Long[0]));
        if(conceptSMTKList.size()!=0){
            return conceptSMTKList;
        }else{
            pattern= truncatePattern(pattern);
            return findConceptBy(pattern,new Long[0],0, countConceptBy(pattern,new Long[0]));
        }

    }

    @Override
    public int countConceptBy(String pattern, Long[] categories) {

        long MODELADO_VIGENTE= 3;
        long MODELADO_NO_VIGENTE= 4;
        Long[] states = {MODELADO_VIGENTE, MODELADO_NO_VIGENTE};

        pattern = standardizationPattern(pattern);
        String[] arrayPattern = patternToArray(pattern);

        categories = (categories==null)?new Long[0]:categories;

        //Cuenta por categoría y patron o concept ID
        if ((categories.length!=0 && pattern != null)) {
            if (pattern.length() >= 3) {
                if (arrayPattern.length >= 2) {
                    return conceptDAO.countConceptBy(arrayPattern, categories, states);
                } else {
                    return conceptDAO.countConceptBy(arrayPattern[0], categories, states);
                }
            }
        }

        //Cuenta por patron o concept ID

        if (pattern != null) {
            if (pattern.length() >= 3) {
                if (arrayPattern.length >= 2) {
                    return conceptDAO.countConceptBy(arrayPattern, new Long[0], states);
                } else {
                    return conceptDAO.countConceptBy(arrayPattern[0], categories, states);
                }
            }
        }

        //Cuenta por categoría
        if (categories.length > 0) {
            return conceptDAO.countConceptBy((String[])null, categories, states);
        }
        return conceptDAO.countConceptBy((String[])null, categories, states);

    }

    @Override
    public void persist(@NotNull ConceptSMTK conceptSMTK, IUser user) {
        logger.debug("El concepto " + conceptSMTK + " será persistido.");

        /* Pre-condición técnica: el concepto no debe estar persistido */
        validatesIsNotPersistent(conceptSMTK);

        /* Pre-condiciones: Reglas de negocio para la persistencia */
        BusinessRulesContainer brm = new ConceptCreationBusinessRuleContainer();
        brm.apply(conceptSMTK, user);

        /* En este momento se está listo para persistir el concepto */
        try {
            conceptDAO.persist(conceptSMTK, user);
        } catch (EJBException ejbException) {
            String errorMsg = "No se pudo persistir el concepto " + conceptSMTK.toString();
            logger.error(errorMsg, ejbException);
        }

        /* Se deja registro en la auditoría */
        auditManager.recordNewConcept(conceptSMTK, user);
        logger.debug("El concepto " + conceptSMTK + " fue persistido.");
    }

    @Override
    public void update(@NotNull ConceptSMTK conceptSMTK, IUser user) {

        // TODO: Agregar validaciones pre-update
        // TODO: Aplicar reglas de negocio (crear clase)
        // TODO: Invocar el DAO para hacer el update, quizás por cada uno...

        /* Se deja registro en la auditoría */
        auditManager.recordUpdateConcept(conceptSMTK, user);
        logger.debug("El concepto " + conceptSMTK + " fue actualizado.");
    }

    @Override
    public void update(@NotNull ConceptSMTK oldConceptSMTK, @NotNull ConceptSMTK newConceptSMTK, IUser user) {

        logger.debug("El concepto " + newConceptSMTK + " será actualizado.");

        /* Pre-condición técnica: el concepto debe estar persistido */
        validatesIsPersistent(newConceptSMTK);

        /* Pre-condiciones: Reglas de negocio para la persistencia */
        BusinessRulesContainer brm = new ConceptCreationBusinessRuleContainer();
        brm.apply(newConceptSMTK, user);

        /* En este momento se está listo para persistir el concepto */
        try {
            conceptDAO.update(oldConceptSMTK, newConceptSMTK, user);
        } catch (EJBException ejbException) {
            String errorMsg = "No se pudo actualizar el concepto " + newConceptSMTK.toString();
            logger.error(errorMsg, ejbException);
        }

        /* Se deja registro en la auditoría */
        auditManager.recordNewConcept(newConceptSMTK, user);
    }

    /**
     * Este método es responsable de validar que el concepto no se encuentre persistido.
     *
     * @param conceptSMTK El concepto sobre el cual se realiza la validación de persistencia.
     *
     * @throws javax.ejb.EJBException Se arroja si el concepto tiene un ID de persistencia.
     */
    private void validatesIsNotPersistent(ConceptSMTK conceptSMTK) throws EJBException {
        long id = conceptSMTK.getId();
        if (id != conceptDAO.NON_PERSISTED_ID) {
            throw new EJBException("El concepto ya se encuentra persistido. ID=" + id);
        }
    }

    /**
     * Este método es responsable de validar que el concepto se encuentre persistido.
     *
     * @param conceptSMTK El concepto sobre el cual se realiza la validación de persistencia.
     *
     * @throws javax.ejb.EJBException Se arroja si el concepto no tiene un ID de persistencia.
     */
    private void validatesIsPersistent(ConceptSMTK conceptSMTK) throws EJBException {
        long id = conceptSMTK.getId();
        if (id == conceptDAO.NON_PERSISTED_ID) {
            throw new EJBException("El concepto no se encuentra persistido. ID=" + id);
        }
    }

    @Override
    public ConceptSMTK merge(ConceptSMTK conceptSMTK) {

        // TODO: Por Implementar
        return null;
    }

    @Override
    public String generateConceptId() {
        return UUID.randomUUID().toString();
    }


    // TODO: Terminar esto.
    @Override
    public ArrayList<Description> findDescriptionForPattern(String pattern) {


        /*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call find_description(?,?)}");

            call.setString(1, "");
            call.setString(2, pattern);
            call.execute();

            ResultSet rs = call.getResultSet();

            int idD;
            String description;

            while (rs.next()) {
                idD = Integer.parseInt(rs.getString("id_concepto"));
                description = rs.getString("term");
                descriptions.add(new Description(idD, description));
            }
            conne.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        */

        return null;
    }

    // TODO: Terminar esto.
    @Override
    public String addConcept(String idCategory, boolean isValid) {
        String idConcepto = null;

/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call crea_concepto(?,?)}");

            call.setInt(1, Integer.parseInt(idCategory));
            call.setBoolean(2, isValid);

            call.execute();


            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                idConcepto = rs.getString(1);

            }
            conne.close();
        } catch (SQLException | ClassNotFoundException e) {

            System.out.println(e.toString());
        }
*/

        return idConcepto;
    }


    /**
     * Método encargado de convertir un string en una lista de string.
     * @param pattern patrón de texto
     * @return retorna una lista de String
     */

    private String[] patternToArray(String pattern) {
        if(pattern!=null){
            StringTokenizer st;
            String token;
            st = new StringTokenizer(pattern, " ");
            ArrayList<String> listPattern = new ArrayList<>();
            int count = 0;

            while (st.hasMoreTokens()) {
                token = st.nextToken();
                if (token.length() >= 3) {
                    listPattern.add(token.trim());
                }
                if (count == 0 && listPattern.size() == 0) {
                    listPattern.add(token.trim());
                }
                count++;
            }
            return listPattern.toArray(new String[listPattern.size()]);
        }
        return new String[0];
    }

    /**
     *  Método de normalización del patrón de búsqueda, lleva las palabras a minúsculas,
     *  le quita los signos de puntuación y ortográficos
     * @param pattern patrón de texto a normalizar
     * @return patrón normalizado
     */
    //TODO: Falta quitar los StopWords (no se encuentran definidos)
    private String standardizationPattern(String pattern) {

        if(pattern!=null){
            pattern = Normalizer.normalize(pattern, Normalizer.Form.NFD);
            pattern = pattern.toLowerCase();
            pattern = pattern.replaceAll("[^\\p{ASCII}]", "");
            pattern = pattern.replaceAll("\\p{Punct}+", "");
        }
        return pattern;
    }


    /**
     * Método encargado de truncar a un largo de 3 las palabras del String ingresado
     * @param pattern arreglo de palabras
     * @return arreglo de String con las palabras truncadas
     */
    private String truncatePattern(String pattern){
        pattern= standardizationPattern(pattern);
        String [] arrayToPattern= patternToArray(pattern);

        for (int i = 0; i < arrayToPattern.length ; i++) {
            pattern=arrayToPattern[i].substring(0,3)+" ";
        }
        return pattern;
    }
}
