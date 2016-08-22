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

    @Override
    public ConceptSMTK getConceptByCONCEPT_ID(String conceptID) {

        /* Se recupera el concepto base (sus atributos) sin sus relaciones ni descripciones */
        ConceptSMTK concept = this.conceptDAO.getConceptByCONCEPT_ID(conceptID);

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
    public List<ConceptSMTK> findConceptByPatternCategoryPageNumber(String pattern, Long[] categories, int pageNumber, int pageSize) {

        // FIXME: Cambiar estados en duro a variables?
        Long[] states = {(long) 3, (long) 4};
        if (categories != null) {
            if (categories.length == 0) categories = null;
        }

        /* El patron se separa en varios palabras: pattern.split(pattern) */
        return conceptDAO.getConceptByPatternCategory(pattern.split(pattern), categories, states, pageSize, pageNumber);
    }

    @Override
    public List<ConceptSMTK> findConceptByConceptIDOrDescriptionCategoryPageNumber(String patter, Long[] categories, int pageNumber, int pageSize) {


        Long[] states = {(long) 3, (long) 4};


        if ((categories != null && patter != null)) {
            if (patter.length() > 0) {
                List<String> listPattern;
                patter = standardizationPattern(patter);
                listPattern = patternToList(patter);
                String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);
                if (listPattern.size() >= 2) {
                    return conceptDAO.getConceptByPatternCategory(arrPattern, categories, states, pageSize, pageNumber);
                } else {
                    return conceptDAO.getConceptByPatternOrConceptIDAndCategory(arrPattern[0], categories, pageNumber, pageSize, states);
                }
            }
        }
        if (categories != null) {
            if (categories.length > 0) {
                return conceptDAO.getConceptByCategory(categories, states, pageSize, pageNumber);
            }

        }

        if (patter != null) {
            if (patter.length() > 0) {
                categories = new Long[0];
                List<String> listPattern;
                patter = standardizationPattern(patter);
                listPattern = patternToList(patter);
                String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);
                if (listPattern.size() >= 2) {
                    return conceptDAO.getConceptByPatternCategory(arrPattern, categories, states, pageSize, pageNumber);
                } else {
                    return conceptDAO.getConceptByPatternOrConceptIDAndCategory(arrPattern[0], categories, pageNumber, pageSize, states);
                }
            }

        }

        return conceptDAO.getAllConcepts(states, pageSize, pageNumber);
    }


    @Override
    public int getAllConceptCount(String pattern, Long[] categories) {

        Long[] states = {(long) 3, (long) 4};


        if (categories != null && pattern != null) {
            if (pattern.length() > 0) {
                List<String> listPattern;
                pattern = standardizationPattern(pattern);
                listPattern = patternToList(pattern);
                String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);
                if (listPattern.size() >= 2) {
                    return conceptDAO.getAllConceptCount(arrPattern, categories, states);
                } else {
                    return conceptDAO.getCountFindConceptID(arrPattern[0], categories, states);
                }
            }
        }

        if (categories != null) {
            if (categories.length > 0) {
                return conceptDAO.getAllConceptCount(null, categories, states);
            }

        }
        categories = new Long[0];
        if (pattern != null) {
            if (pattern.length() > 0) {

                List<String> listPattern;
                pattern = standardizationPattern(pattern);
                listPattern = patternToList(pattern);
                String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);
                if (listPattern.size() >= 2) {
                    return conceptDAO.getAllConceptCount(arrPattern, null, states);
                } else {
                    return conceptDAO.getCountFindConceptID(arrPattern[0], categories, states);
                }
            }
        }
        return conceptDAO.getAllConceptCount(null, categories, states);

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
        if (id <= 0) {
            throw new EJBException("El concepto ya se encuentra persistido. ID=" + id);
        }
    }

    @Override
    public ConceptSMTK merge(ConceptSMTK conceptSMTK) {

        // TODO: Por Implementar
        return null;
    }


    private List<String> patternToList(String pattern) {
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
        return listPattern;
    }

    private String standardizationPattern(String pattern) {

        pattern = Normalizer.normalize(pattern, Normalizer.Form.NFD);
        pattern = pattern.toLowerCase();
        pattern = pattern.replaceAll("[^\\p{ASCII}]", "");
        pattern = pattern.replaceAll("\\p{Punct}+", "");

        return pattern;

    }
}
