package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;


/**
 * Created by diego on 26/06/2016.
 */

@ManagedBean(name = "conceptBrowserBean")
@ViewScoped
public class ConceptBrowserBean implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ConceptBrowserBean.class);

    @EJB
    ConceptManagerInterface conceptManager;


    @EJB
    CategoryManager categoryManager;

    @EJB
    AuditManagerInterface auditManager;

    public User user;

    private Category category;


    //Inicializacion del Bean

    @PostConstruct
    protected void initialize() throws ParseException {

        // TODO: Manejar el usuario desde la sesión

        user = new User();

        user.setIdUser(1);
        user.setUsername("amauro");
        user.setPassword("amauro");
        Profile designerProfile = new Profile(1, "designerProfile", "designerProfile");
        user.getProfiles().add(designerProfile);

        //category = categoryManager.getCategoryById(105590001);
        //category = categoryManager.getCategoryById(105590001);
        category = categoryManager.getCategoryById(71388002);
        //category = categoryManager.getCategoryById(419891008);


        // TODO: Inicializar lista de estados de descripción con todos los estados posibles
        //descriptionStates = stateMachineManager.getConceptStateMachine().
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

